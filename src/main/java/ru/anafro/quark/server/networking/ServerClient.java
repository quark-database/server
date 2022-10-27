package ru.anafro.quark.server.networking;

import org.json.JSONObject;
import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.QueryExecutionStatus;
import ru.anafro.quark.server.plugins.events.BeforeSendingMessage;
import ru.anafro.quark.server.plugins.events.SendingMessageFinished;
import ru.anafro.quark.server.utils.exceptions.Exceptions;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ServerClient {
    private final OutputStream outputStream;

    public ServerClient(Socket socket) throws IOException {
        this.outputStream = socket.getOutputStream();
    }

    public void sendMessage(JSONObject json) throws IOException {
        var clientDataOutputStream = new DataOutputStream(outputStream);
        var message = new Message(json.toString());

        Quark.fire(new BeforeSendingMessage(Quark.server(), this, message));

        clientDataOutputStream.write(message.buildByteMessage());

        Quark.fire(new SendingMessageFinished(Quark.server(), this, message));
    }

    public void sendError(String errorMessage, QueryExecutionStatus status) throws IOException {
        JSONObject errorResponse = new JSONObject();

        errorResponse.put("status", status.name());
        errorResponse.put("message", errorMessage);

        sendMessage(errorResponse);
    }

    public void sendError(Throwable becauseOf) throws IOException {
        JSONObject errorResponse = new JSONObject();

        errorResponse.put("status", QueryExecutionStatus.SERVER_ERROR.name());
        errorResponse.put("exception", becauseOf.getClass().getSimpleName() + "\n\n" + Exceptions.getTraceAsString(becauseOf));
        errorResponse.put("message", becauseOf.getMessage());

        sendMessage(errorResponse);
    }
}
