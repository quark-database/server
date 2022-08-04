package ru.anafro.quark.server.networking;

public class MiddlewareResponse {
    private final boolean passed;
    private final String reason;

    public MiddlewareResponse(boolean passed, String reason) {
        this.passed = passed;
        this.reason = reason;
    }

    public MiddlewareResponse(boolean passed) {
        this(passed, "Ok");
    }

    public static MiddlewareResponse pass() {
        return new MiddlewareResponse(true);
    }

    public static MiddlewareResponse deny(String reason) {
        return new MiddlewareResponse(false, reason);
    }

    public boolean isPassed() {
        return passed;
    }

    public boolean isDenied() {
        return !isPassed();
    }

    public String getReason() {
        return reason;
    }
}
