package ru.anafro.quark.server.networking;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

public class Request {
    private final JSONObject data;
    private final InetAddress clientIP;

    public Request(JSONObject data, InetAddress clientIP) {
        this.data = data;
        this.clientIP = clientIP;
    }

    public String getString(String key) {
        return data.getString(key);
    }

    public int getInt(String key) {
        return data.getInt(key);
    }

    public float getFloat(String key) {
        return data.getFloat(key);
    }

    public double getDouble(String key) {
        return data.getDouble(key);
    }

    public long getLong(String key) {
        return data.getLong(key);
    }

    public boolean getBoolean(String key) {
        return data.getBoolean(key);
    }

    public boolean has(String key) {
        return data.has(key);
    }

    public boolean missing(String key) {
        return !has(key);
    }

    public InetAddress getClientIP() {
        return clientIP;
    }

    public boolean isInner() {
        if (clientIP.isAnyLocalAddress() || clientIP.isLoopbackAddress())
            return true;

        try {
            return NetworkInterface.getByInetAddress(clientIP) != null;
        } catch(SocketException exception) {
            return false;
        }
    }

    public JSONObject getData() {
        return data;
    }
}
