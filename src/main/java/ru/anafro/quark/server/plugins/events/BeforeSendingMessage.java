package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.networking.Message;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.networking.ServerClient;

public class BeforeSendingMessage extends Event {
    private final Server server;
    private final ServerClient client;
    private final Message message;

    public BeforeSendingMessage(Server server, ServerClient client, Message message) {
        this.server = server;
        this.client = client;
        this.message = message;
    }

    public Server getServer() {
        return server;
    }

    public ServerClient getClient() {
        return client;
    }

    public Message getMessage() {
        return message;
    }
}
