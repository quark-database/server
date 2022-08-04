package ru.anafro.quark.server.security;

import ru.anafro.quark.server.databases.Instruction;

public record Token(String token) {
    public boolean hasPermission(String permission) {
        Instruction instruction = Instruction.fromString("count in 'quark.token_permissions' { if = @condition('token = \"%s\" & permission = \"%s\"') }".formatted(token, permission));

        // TODO

        return false; // TODO
    }
}
