package ru.anafro.quark.server.utils.networking;

import ru.anafro.quark.server.networking.Ports;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public final class Networking {
    private static final int FIND_AND_ALLOCATE_PORT = 0;

    private Networking() {
    }

    public static ServerSocket createServerSocket(int port) {
        try {
            return new ServerSocket(Ports.isUsable(port) ? port : FIND_AND_ALLOCATE_PORT);
        } catch (IOException exception) {
            throw new NetworkingException(exception.getMessage());
        }
    }

    public static Socket acceptClientSocket(ServerSocket serverSocket) {
        try {
            return serverSocket.accept();
        } catch (IOException exception) {
            throw new NetworkingException(exception.getMessage());
        }
    }
}
