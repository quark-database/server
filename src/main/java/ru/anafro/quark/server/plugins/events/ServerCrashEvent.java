package ru.anafro.quark.server.plugins.events;

public class ServerCrashEvent extends Event {
    private final String errorMessage;

    public ServerCrashEvent(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
