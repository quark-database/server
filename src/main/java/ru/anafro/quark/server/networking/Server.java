package ru.anafro.quark.server.networking;

import ru.anafro.quark.server.api.Quark;
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
        Quark.logger().error("Request query: %s".formatted(request.getString("query")));

        parser.parse(lexer.lex(request.getString("query")));
        var instruction = parser.getInstruction();
        var arguments = parser.getArguments();
        var token = new Token(request.getString("token"));

//        System.out.println("Before: " + arguments.asList().stream().map(instructionArgument -> instructionArgument.name() + ": " + instructionArgument.value().toInstructionForm()).collect(Collectors.joining(", ")));

        if(token.hasPermission(instruction.getPermission())) {
            InstructionResult result = instruction.execute(arguments);

            return Response.create()
                    .add("status", result.queryExecutionStatus().name())
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
