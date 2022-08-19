package ru.anafro.quark.server.networking;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionResult;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.plugins.Plugin;
import ru.anafro.quark.server.plugins.events.ServerStarted;
import ru.anafro.quark.server.security.Token;

public class Server extends TcpServer {
    private final ServerConfiguration configuration = new ServerConfigurationLoader().load("Server Configuration.yaml");
    private final InstructionLexer lexer = new InstructionLexer();
    private final InstructionParser parser = new InstructionParser();
    private final Logger logger = new Logger(this.getClass());


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

        super.start(configuration.getPort());
        Quark.plugins().getLoadedPlugins().forEach(Plugin::onEnable);
    }

    @Override
    public Response onRequest(Request request) {
        parser.parse(lexer.lex(request.getString("query")));
        Instruction instruction = parser.getInstruction();
        Token token = new Token(request.getString("token"));

        if(token.hasPermission(instruction.getPermission())) {
            InstructionResult result = instruction.execute(parser.getArguments());

            return Response.create()
                    .add("status", result.executionStatus().name())
                    .add("message", result.message())
                    .add("time", result.milliseconds())
                    .add("table", result.tableView().toJson());
        } else {
            return Response.error("No Permission");
        }
    }

    @Override
    public void onStartingCompleted() {
        Quark.plugins().fireEvent(new ServerStarted());
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
