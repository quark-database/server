package ru.anafro.quark.server.databases.data.modifiers;

public class AlphaDashUnderscoreModifier extends AbstractRegexColumnModifier {
    public AlphaDashUnderscoreModifier() {
        super("alpha dash underscore", "^[a-zA-Z-_]*$");
    }
}
