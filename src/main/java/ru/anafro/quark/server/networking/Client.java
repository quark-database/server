package ru.anafro.quark.server.networking;

import org.json.JSONObject;
import ru.anafro.quark.server.language.ResponseStatus;
import ru.anafro.quark.server.logging.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final Logger logger = new Logger(getClass());

    public Client(Socket socket) {
        this.socket = socket;
    }

    public Request receiveRequest() {
        try (var stream = socket.getInputStream()) {
            return Request.createFromJson(NetworkMessage.collectString(stream));
        } catch (IOException exception) {
            var ip = this.getIp();
            var port = this.getPort();
            logger.info(STR."Receiving a request from client at \{ip}:\{port} failed: \{exception.getMessage()}");

            return null;
        }
    }

    public void send(Response response) {
        sendMessage(response.json());
    }

    public void sendMessage(JSONObject json) {
        var message = new NetworkMessage(json.toString());

        try (var stream = new DataOutputStream(socket.getOutputStream())) {
            stream.write(message.toByteArray());
        } catch (IOException exception) {
            var ip = this.getIp();
            var port = this.getPort();

            logger.info(STR."Sending a message to client at \{ip}:\{port} failed: \{exception.getMessage()}");
        }
    }

    public void sendError(String message, ResponseStatus status) {
        var response = new JSONObject();

        response.put("status", status.name());
        response.put("message", message);

        sendMessage(response);
    }

    private String getIp() {
        return socket.getInetAddress().getHostAddress();
    }

    private int getPort() {
        return socket.getPort();
    }

    public void send(MiddlewareResponse middlewareResponse) {
        sendError(middlewareResponse.getReason(), ResponseStatus.MIDDLEWARE_ERROR);
    }
}
