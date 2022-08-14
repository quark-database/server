package ru.anafro.quark.server.security;

public record Token(String token) {
    public boolean hasPermission(String permission) {
        // TODO

        return false; // TODO
    }
}
