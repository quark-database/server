package ru.anafro.quark.server.database.data.modifiers;

public class AlphaDotUnderscoreColumnModifier extends AbstractRegexColumnModifier {
    public AlphaDotUnderscoreColumnModifier() {
        super("alpha dot underscore", "^[a-zA-Z_]*$");
    }
}
