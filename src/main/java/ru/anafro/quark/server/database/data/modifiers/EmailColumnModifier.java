package ru.anafro.quark.server.database.data.modifiers;

public class EmailColumnModifier extends AbstractRegexColumnModifier {
    public EmailColumnModifier() {
        super("email", "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
}
