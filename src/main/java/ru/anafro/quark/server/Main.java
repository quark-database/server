package ru.anafro.quark.server;

import ru.anafro.quark.server.networking.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
