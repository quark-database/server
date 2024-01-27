package ru.anafro.quark.server.utils.networking;

import ru.anafro.quark.server.networking.Ports;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public final class Networking {
    private static final int FIND_AND_ALLOCATE_PORT = 0;

    private Networking() {}

    public static ServerSocket createServerSocket(int port) {
        try (var socket = new ServerSocket(Ports.isUsable(port) ? port : FIND_AND_ALLOCATE_PORT)) {
            return socket;
        } catch (IOException exception) {
            throw new NetworkingException(exception.getMessage());
        }
    }

    public static Socket acceptClientSocket(ServerSocket serverSocket) {
        try (var socket = serverSocket.accept()) {
            return socket;
        } catch (IOException exception) {
            throw new NetworkingException(exception.getMessage());
        }
    }
}
