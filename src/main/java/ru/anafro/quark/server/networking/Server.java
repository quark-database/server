package ru.anafro.quark.server.networking;

import ru.anafro.quark.server.language.lexer.InstructionLexer;
import ru.anafro.quark.server.language.parser.InstructionParser;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.networking.middlewares.Middleware;
import ru.anafro.quark.server.networking.middlewares.QueryMiddleware;
import ru.anafro.quark.server.networking.middlewares.TokenMiddleware;
import ru.anafro.quark.server.plugins.events.ServerStoppedEvent;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.networking.Networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * TcpServer is an abstract class for receiving TCP packets,
 * but not handling - implement 'handle()' method to add
 * handling functionality.
 */
public final class Server implements Runnable {
    private final InstructionLexer lexer = new InstructionLexer();
    private final InstructionParser parser = new InstructionParser();
    private final ArrayList<Middleware> middlewares = Lists.empty();
    private final Logger logger = new Logger(this.getClass());
    private volatile boolean isRunning = false;
    private ServerSocket socket;

    public Server() {
        middlewares.add(new TokenMiddleware());
        middlewares.add(new QueryMiddleware());
    }

    /**
     * This function is called when a request is received from the client.
     * <p>
     * The function is called with a single argument, a Request object. The Request object contains all
     * the information about the request.
     *
     * @param request The request object that was sent to the server.
     * @return A Response object.
     */
    public Response respond(Request request) {
        try {
            return Response.make(request.getQuery().execute());
        } catch (QuarkException exception) {
            return Response.syntaxError(exception);
        } catch (Exception exception) {
            return Response.serverError(exception);
        }
    }

    /**
     * It starts a server on a given port, waits for a client to connect, collects a message from the
     * client, makes a request, runs middlewares, and sends a response
     */
    @Override
    public void run() {
        this.socket = Networking.createServerSocket(Quark.configuration().getPort());

        while (this.isRunning()) {
            var client = new Client(Networking.acceptClientSocket(socket));
            var request = client.receiveRequest();
            var middlewareResponse = passThroughMiddlewares(request);

            if (middlewareResponse.isPassed()) {
                client.send(respond(request));
            } else {
                client.send(middlewareResponse);
            }
        }
    }

    public int getPort() {
        return socket.getLocalPort();
    }

    private MiddlewareResponse passThroughMiddlewares(Request request) {
        for (var middleware : middlewares) {
            var response = middleware.handleRequest(request);

            if (response.isDenied()) {
                return response;
            }
        }

        return MiddlewareResponse.pass();
    }

    /**
     * The stop() function sets the stopped variable to false.
     */
    public void stop() {
        try {
            socket.close();
            this.isRunning = false;

            Quark.fireEvent(new ServerStoppedEvent());
        } catch (IOException exception) {
            logger.error("Stopping the server caused an exception.");
            logger.error(exception);
        }
    }

    /**
     * This function returns a boolean value that indicates whether the server is running or not
     *
     * @return The boolean value of the stopped variable.
     */
    public boolean isRunning() {
        return isRunning;
    }

    public InstructionLexer getLexer() {
        return lexer;
    }

    public InstructionParser getParser() {
        return parser;
    }

    public Logger getLogger() {
        return logger;
    }
}
