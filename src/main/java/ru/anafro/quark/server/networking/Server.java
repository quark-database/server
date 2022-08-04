package ru.anafro.quark.server.networking;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionResult;
import ru.anafro.quark.server.logging.LogLevel;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.security.Token;

public class Server extends TcpServer {
    private final ServerConfiguration configuration = ServerConfigurationLoader.load("Server Configuration.yaml");
    private final Logger logger = new Logger(this.getClass());
    private int requests = 0;


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

    public void startAsync() {
        logger.log(LogLevel.INFO, "Server is being started at port " + configuration.getPort() + ". To use it, copy this access token:" + configuration.getToken());
        startAsync(configuration.getPort());
    }

    public void start() {
        start(configuration.getPort());
    }

    @Override
    public Response onRequest(Request request) {
        Instruction instruction = Instruction.fromString(request.getString("query"));
        Token token = new Token(request.getString("token"));

        if(token.hasPermission(instruction.getPermission())) {
            InstructionResult result = instruction.execute(this);

            return Response.create()
                    .add("status", result.executionStatus().name())
                    .add("message", result.message())
                    .add("time", result.milliseconds())
                    .add("table", result.tableView().toJson());
        } else {
            return Response.error("No Permission");
        }
    }

    public ServerConfiguration getConfiguration() {
        return configuration;
    }

    public Logger getLogger() {
        return logger;
    }

    public int getRequests() {
        return requests;
    }
}
