package ru.anafro.quark.server.databases.data.modifiers;

public class EmailModifier extends AbstractRegexColumnModifier {
    public EmailModifier() {
        super("email", "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
}
