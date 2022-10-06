package ru.anafro.quark.server.networking;

import org.json.JSONException;
import org.json.JSONObject;
import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.QueryExecutionStatus;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.multithreading.AsyncService;
import ru.anafro.quark.server.multithreading.Threads;
import ru.anafro.quark.server.networking.exceptions.MessageHeaderIsTooShortException;
import ru.anafro.quark.server.networking.exceptions.ServerCannotBeRunTwiceException;
import ru.anafro.quark.server.networking.exceptions.ServerCrashedException;
import ru.anafro.quark.server.utils.containers.Lists;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * TcpServer is an abstract class for receiving TCP packets,
 * but not handling - implement 'handle()' method to add
 * handling functionality.
 */
public abstract class TcpServer implements AsyncService {
    private static final float DELAY_BETWEEN_ATTEMPTS_TO_RUN_SERVER_IN_SECONDS = 7;
    private volatile boolean stopped = false;
    private final ArrayList<Middleware> middlewares = Lists.empty();
    protected ServerSocket serverSocket;
    protected final Logger logger = new Logger(this.getClass());

    /**
     * This function adds a middleware to the list of middlewares
     * 
     * @param middleware The middleware to be registered.
     */
    public void registerMiddleware(Middleware middleware) {
        middlewares.add(middleware);
    }

    /**
     * This function is called when a request is received from the client.
     *
     * The function is called with a single argument, a Request object. The Request object contains all
     * the information about the request.
     * 
     * @param request The request object that was sent to the server.
     * @return A Response object.
     */
    public abstract Response onRequest(Request request);

    /**
     * It starts a server on a given port, waits for a client to connect, collects a message from the
     * client, makes a request, runs middlewares, and sends a response
     * 
     * @param port The port number to start the server on.
     */
    public void start(int port) {
        if(isStopped()) {
            throw new ServerCannotBeRunTwiceException();
        }

        if(Ports.isInvalid(port)) {
            Quark.crash("Port %d is invalid. We can't use it for Quark Server. Change it to another one".formatted(port));
        }

        while(Ports.isUnavailable(port)) {
            logger.error("Port %d is unavailable. Waiting %.1f seconds before trying to start server again...".formatted(port, DELAY_BETWEEN_ATTEMPTS_TO_RUN_SERVER_IN_SECONDS));
            Threads.freezeFor(DELAY_BETWEEN_ATTEMPTS_TO_RUN_SERVER_IN_SECONDS);
        }

        logger.info("Server is started!");

        try {
            serverSocket = new ServerSocket(port);

            onStartingCompleted();

            while(isStarted()) {
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
                            client.sendError(middlewareResponse.getReason(), QueryExecutionStatus.MIDDLEWARE_ERROR);
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
        } catch(SocketException exception) {
            logger.info("Server has stopped.");
        } catch(IOException exception) {
            throw new ServerCrashedException(exception);
        }
    }

    /**
     * The stop() function sets the stopped variable to false.
     */
    public void stop() {
        try {
            logger.debug("Server is stopping...");
            serverSocket.close();
        } catch (IOException ignored) {
            // Ignored.
        }
    }

    /**
     * This function returns a boolean value that indicates whether the server is stopped or not
     * 
     * @return The boolean value of the stopped variable.
     */
    public boolean isStopped() {
        return stopped;
    }

    /**
     * This function returns a boolean value that indicates whether the server is started or not
     * 
     * @return The boolean value of the stopped variable.
     */
    public boolean isStarted() {
        return !isStopped();
    }

    /**
     * This function is called when the starting of the server is completed.
     */
    public void onStartingCompleted() {
        // Override it in super class.
    }

    /**
     * It reloads the server.
     */
    @Deprecated(since = "Quark 1.1")
    public void reload() {
        try {
            String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            File currentJar = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());

            if(!currentJar.getName().endsWith(".jar")) {
                return;
            }

            ArrayList<String> command = new ArrayList<>();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());

            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
            System.exit(0);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
