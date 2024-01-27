package ru.anafro.quark.server.database.data.modifiers;

public class AlphaDashUnderscoreColumnModifier extends AbstractRegexColumnModifier {
    public AlphaDashUnderscoreColumnModifier() {
        super("alpha dash underscore", "^[a-zA-Z-_]*$");
    }
}
