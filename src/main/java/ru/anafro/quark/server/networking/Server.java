package ru.anafro.quark.server.networking;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.InstructionResult;
import ru.anafro.quark.server.databases.ql.QueryExecutionStatus;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;
import ru.anafro.quark.server.databases.views.TableView;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.plugins.events.*;
import ru.anafro.quark.server.security.Token;

public class Server extends TcpServer {
    private final ServerConfiguration configuration = new ServerConfigurationLoader().load("Server Configuration.json");
    private final InstructionLexer lexer = new InstructionLexer();
    private final InstructionParser parser = new InstructionParser();


    public Server() {
        this.registerMiddleware(request -> {
            if(request.missing("token")) {
                return MiddlewareResponse.deny("Token Missed");
            }

            if(request.missing("query")) {
                return MiddlewareResponse.deny("Query Missing");
            }

            return MiddlewareResponse.pass();
        });
    }

    public void start() {
        logger.info("Server is being started at port " + configuration.getPort() + "...");

        Quark.plugins().forEach(plugin -> {
            try {
                plugin.onEnable();
                Quark.fire(new PluginLoaded(plugin));
            } catch(Throwable throwable) {
                Quark.fire(new PluginCrashed(plugin, throwable));
            }
        });

        Quark.fire(new BeforeServerStarted(this));
        super.start(configuration.getPort());
        Quark.fire(new ServerStarted(this));
    }

    @Override
    public Response onRequest(Request request) {
        try {
            var query = request.getString("query");

            Quark.fire(new RequestReceived(this, request));
            Quark.fire(new BeforeInstructionLexing(query, lexer));

            var tokens = lexer.lex(query);

            Quark.fire(new BeforeInstructionParsing(query, tokens, parser));
            parser.parse(tokens);

            var instruction = parser.getInstruction();
            var arguments = parser.getArguments();
            var token = new Token(request.getString("token"));


            if(token.hasPermission(instruction.getPermission())) {
                    Quark.fire(new BeforeInstructionExecuted(instruction, arguments));

                    InstructionResult result = instruction.execute(arguments);

                    Quark.fire(new InstructionFinished(instruction, arguments, result));

                    return Response.create()
                            .add("status", result.queryExecutionStatus().name())
                            .add("message", result.message())
                            .add("time", result.milliseconds())
                            .add("table", result.tableView().toJson());

            } else {
                return Response.error("No Permission");
            }
        } catch(QuarkException exception) {
            return Response.create()
                    .add("status", QueryExecutionStatus.SYNTAX_ERROR.name())
                    .add("message", exception.getMessage())
                    .add("time", 0)
                    .add("table", TableView.empty().toJson());
        } catch(Exception exception) {
            return Response.create()
                    .add("status", QueryExecutionStatus.SERVER_ERROR.name())
                    .add("message", exception.getMessage())
                    .add("time", 0)
                    .add("table", TableView.empty().toJson());
        }
    }

    @Override
    public void onStartingCompleted() {
        Quark.fire(new ServerStarted(this));
    }

    public ServerConfiguration getConfiguration() {
        return configuration;
    }

    public Logger getLogger() {
        return logger;
    }

    public InstructionLexer getInstructionLexer() {
        return lexer;
    }

    public InstructionParser getInstructionParser() {
        return parser;
    }

    @Override
    public void run() {
        start();
    }
}
