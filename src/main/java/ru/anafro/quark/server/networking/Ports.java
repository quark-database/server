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

    private Ports() {}

    public static boolean isValid(int port) {
        return port >= FIRST && port <= LAST;
    }

    public static boolean isInvalid(int port) {
        return !Ports.isValid(port);
    }

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

    public static boolean isUnavailable(int port) {
        return !Ports.isAvailable(port);
    }
}
