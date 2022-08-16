package ru.anafro.quark.server.networking;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.commands.*;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionResult;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.fun.Greeter;
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
        Greeter.greet();
        logger.info("Server is being started at port " + configuration.getPort() + "...");
        try {
            super.start(configuration.getPort());
            Quark.plugins().getLoadedPlugins().forEach(Plugin::onEnable);
        } catch(QuarkException exception) {
            logger.error("Server has crashed, here is why: " + exception.getMessage());
            logger.error("Reading a stacktrace may help you to figure out the reason better:");

            exception.printStackTrace();

            logger.error("Because of this error, we have to stop the Quark Server.");
            logger.error("\tIf you think that it's a bug, please, report it on the project's GitHub: github.com/anafro/quark");
            logger.error("\tIf you can't solve this problem by your own, please, feel free to write me on email: contact@anafro.ru (but please, add 'Quark Help' words to the message's theme, thanks)");

            System.exit(-1);
        }
    }

    @Override
    public Response onRequest(Request request) {
        parser.parse(lexer.lex(request.getString("query")));
        Instruction instruction = parser.getInstruction();
        Token token = new Token(request.getString("token"));

        if(token.hasPermission(instruction.getPermission())) {
            InstructionResult result = instruction.execute(parser.getArguments(), this);

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
        var loop = Quark.commands();

        loop.registerCommand(new ExitCommand());
        loop.registerCommand(new HelpCommand());
        loop.registerCommand(new ChangeLogLevelCommand());
        loop.registerCommand(new OpenDebugCommand());
        loop.registerCommand(new EnableDebugCommand());
        loop.registerCommand(new DisableDebugCommand());
//        loop.registerCommand(new ReloadCommand());        TODO: Not working yet

        loop.startReadingCommands();

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
