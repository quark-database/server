package ru.anafro.quark.server.networking;

import org.json.JSONException;
import org.json.JSONObject;
import ru.anafro.quark.server.logging.LogLevel;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.networking.exceptions.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * TcpServer is an abstract class for receiving TCP packets,
 * but not handling - implement 'handle()' method to add
 * handling functionality.
 */
public abstract class TcpServer {
    private volatile boolean stopped = false;
    private final ArrayList<Middleware> middlewares = new ArrayList<>();
    private final Logger logger = new Logger(this.getClass());

    public void registerMiddleware(Middleware middleware) {
        middlewares.add(middleware);
    }

    public abstract Response onRequest(Request request);

    public void startAsync(int port) {
        CompletableFuture.supplyAsync(() -> {
            start(port);
            return null;
        });
    }

    public void start(int port) {
        if(isStopped()) {
            throw new ServerCannotBeRunTwiceException();
        }

        if(Ports.isInvalid(port)) {
            throw new ImpossiblePortNumberException(port);
        }

        if(Ports.isUnavailable(port)) {
            throw new PortIsUnavailableException(port);
        }

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            onStartingCompleted();

            while(isStarted()) {
                Thread.onSpinWait();

                logger.debug("Waiting for a client...");

                Socket clientSocket = serverSocket.accept();

                logger.debug("Connection from: " + clientSocket.getInetAddress().getHostAddress());

                ServerClient client = new ServerClient(clientSocket);

                logger.debug("Starting collecting message...");

                Message clientRequestMessage = Message.collect(clientSocket.getInputStream());

                logger.debug("Collected client request message: " + clientRequestMessage.getContents());

                try {
                    Request clientRequest = new Request(new JSONObject(clientRequestMessage.getContents()), clientSocket.getInetAddress());

                    logger.debug("Made a request...");

                    boolean middlewaresPassed = true;
                    for(Middleware middleware : middlewares) {
                        logger.debug("Running middleware...");

                        MiddlewareResponse middlewareResponse = middleware.filter(clientRequest);

                        logger.debug("Completed! Passed? " + middlewareResponse.isPassed() + ", reason: " + middlewareResponse.getReason());

                        if(middlewareResponse.isDenied()) {
                            client.sendError(middlewareResponse.getReason());
                            middlewaresPassed = false;

                            logger.debug("Sent an error message");

                            break;
                        }
                    }

                    if(middlewaresPassed) {
                        Response serverResponse = onRequest(clientRequest);
                        client.sendMessage(serverResponse.data());

                        logger.debug("All the middlewares are passed successfully! Sent a message");
                    }
                } catch(JSONException | MessageHeaderIsTooShortException exception) {
                    client.sendError(exception);
                    logger.debug("Woopsie, exception! Sent an error message");
                }
            }
        } catch(IOException exception) {
            throw new ServerCrashedException(exception);
        } finally {
            this.stop();
        }
    }

    public void stop() {
        logger.log(LogLevel.DEBUG, "Server is stopping...");
        stopped = false; // TODO: Change so it will stop with no additional request acceptance.
    }

    public boolean isStopped() {
        return stopped;
    }

    public boolean isStarted() {
        return !isStopped();
    }

    public void onStartingCompleted() {
        // Override it in super class.
    }

    public void reload() {
        // TODO
    }
}
