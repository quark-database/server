package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.networking.Message;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.networking.ServerClient;

public class SendingMessageFinished extends Event {
    private final Server server;
    private final ServerClient client;
    private final Message message;

    public SendingMessageFinished(Server server, ServerClient client, Message message) {
        this.server = server;
        this.client = client;
        this.message = message;
    }

    public Server getServer() {
        return server;
    }

    public Message getMessage() {
        return message;
    }

    public ServerClient getClient() {
        return client;
    }
}
