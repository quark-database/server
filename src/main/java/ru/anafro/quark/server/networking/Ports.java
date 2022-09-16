package ru.anafro.quark.server.networking;

import ru.anafro.quark.server.networking.exceptions.ImpossiblePortNumberException;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

/**
 * Ports is a utility class for networking ports.
 * It can check whether some port it available for usage,
 * or just valid.
 */
public final class Ports {
    public static final int FIRST = 0, LAST = 65535;

    // A private constructor. It is used to prevent instantiation of the class.
    private Ports() {}

    /**
     * > The function returns true if the port number is between 0 and 65535, inclusive
     * 
     * @param port the port number to be checked
     * @return A boolean value.
     */
    public static boolean isValid(int port) {
        return port >= FIRST && port <= LAST;
    }

    /**
     * Returns true if the given port is not valid
     * 
     * @param port The port number to check.
     * @return The return value is a boolean.
     */
    public static boolean isInvalid(int port) {
        return !Ports.isValid(port);
    }

    /**
     * It tries to open a server socket and a datagram socket on the given port. If it succeeds, it
     * closes the sockets and returns true. If it fails, it returns false
     * 
     * @param port The port number to check.
     * @return A boolean value.
     */
    public static boolean isAvailable(int port) {
        if(isInvalid(port)) {
            throw new ImpossiblePortNumberException(port);
        }

        ServerSocket serverSocket = null;
        DatagramSocket datagramSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);

            datagramSocket = new DatagramSocket(port);
            datagramSocket.setReuseAddress(true);

            return true;
        } catch(IOException exception) {
            // Exception is ignored;
        } finally {
            if(datagramSocket != null) {
                datagramSocket.close();
            }

            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch(IOException exception) {
                    // This exception will never be thrown;
                }
            }
        }

        return false;
    }

    /**
     * > If the port is available, return false. Otherwise, return true
     * 
     * @param port The port to check
     * @return A boolean value.
     */
    public static boolean isUnavailable(int port) {
        return !Ports.isAvailable(port);
    }
}
