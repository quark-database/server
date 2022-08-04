package ru.anafro.quark.server.networking;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ServerClient {
    private final OutputStream outputStream;

    public ServerClient(Socket socket) throws IOException {
        this.outputStream = socket.getOutputStream();
    }

    public void sendNamedString(String name, String string) throws IOException {
        JSONObject response = new JSONObject();
        response.put(name, string);

        sendMessage(response);
    }

    public void sendMessage(JSONObject json) throws IOException {
        System.out.println("sendMessage()");

        DataOutputStream clientDataOutputStream = new DataOutputStream(outputStream);
        clientDataOutputStream.write(new Message(json.toString()).buildByteMessage());
    }

    public void sendError(String errorMessage) throws IOException {
        JSONObject errorResponse = new JSONObject();

        errorResponse.put("error", true);
        errorResponse.put("message", errorMessage);

        sendMessage(errorResponse);
    }

    public void sendError(Throwable becauseOf) throws IOException {
        JSONObject errorResponse = new JSONObject();

        errorResponse.put("error", true);
        errorResponse.put("exception", becauseOf.getClass().getSimpleName());
        errorResponse.put("message", becauseOf.getMessage());

        sendMessage(errorResponse);
    }

    public void sendMessage(String message) throws IOException {
        sendNamedString("message", message);
    }
}
