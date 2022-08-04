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

        System.out.println(
        """

             ______     __  __     ______     ______     __  __        ______     ______     ______     __   __   ______     ______   \s
            /\\  __ \\   /\\ \\/\\ \\   /\\  __ \\   /\\  == \\   /\\ \\/ /       /\\  ___\\   /\\  ___\\   /\\  == \\   /\\ \\ / /  /\\  ___\\   /\\  == \\  \s
            \\ \\ \\/\\_\\  \\ \\ \\_\\ \\  \\ \\  __ \\  \\ \\  __<   \\ \\  _"-.     \\ \\___  \\  \\ \\  __\\   \\ \\  __<   \\ \\ \\'/   \\ \\  __\\   \\ \\  __<  \s
             \\ \\___\\_\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\     \\/\\_____\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\__|    \\ \\_____\\  \\ \\_\\ \\_\\\s
              \\/___/_/   \\/_____/   \\/_/\\/_/   \\/_/ /_/   \\/_/\\/_/      \\/_____/   \\/_____/   \\/_/ /_/   \\/_/      \\/_____/   \\/_/ /_/\s
                                               Quark Server v1.0.0.0 build 539                                                                                      \s
        """
        );

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while(isStarted()) {
                Thread.onSpinWait();

                logger.log(LogLevel.INFO, "Waiting for a client...");

                Socket clientSocket = serverSocket.accept();

                logger.log(LogLevel.INFO, "Connection from: " + clientSocket.getInetAddress().getHostAddress());

                ServerClient client = new ServerClient(clientSocket);

                logger.log(LogLevel.INFO, "Starting collecting message...");

                Message clientRequestMessage = Message.collect(clientSocket.getInputStream());

                logger.log(LogLevel.INFO, "Collected client request message: " + clientRequestMessage.getContents());

                try {
                    Request clientRequest = new Request(new JSONObject(clientRequestMessage.getContents()), clientSocket.getInetAddress());

                    logger.log(LogLevel.INFO, "Made a request...");

                    boolean middlewaresPassed = true;
                    for(Middleware middleware : middlewares) {
                        logger.log(LogLevel.INFO, "Running middleware...");

                        MiddlewareResponse middlewareResponse = middleware.filter(clientRequest);

                        logger.log(LogLevel.INFO, "Completed! Passed? " + middlewareResponse.isPassed() + ", reason: " + middlewareResponse.getReason());

                        if(middlewareResponse.isDenied()) {
                            client.sendError(middlewareResponse.getReason());
                            middlewaresPassed = false;

                            logger.log(LogLevel.INFO, "Sent an error message");

                            break;
                        }
                    }

                    if(middlewaresPassed) {
                        Response serverResponse = onRequest(clientRequest);
                        client.sendMessage(serverResponse.data());

                        logger.log(LogLevel.INFO, "All the middlewares are passed successfully! Sent a message");
                    }
                } catch(JSONException | MessageHeaderIsTooShortException exception) {
                    client.sendError(exception);
                    logger.log(LogLevel.INFO, "Woopsie, exception! Sent an error message");
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

    public void reload() {
        // TODO
    }
}
