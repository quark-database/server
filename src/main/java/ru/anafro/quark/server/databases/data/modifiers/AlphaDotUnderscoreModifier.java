package ru.anafro.quark.server.databases.data.modifiers;

public class AlphaDotUnderscoreModifier extends AbstractRegexColumnModifier {
    public AlphaDotUnderscoreModifier() {
        super("alpha dot underscore", "^[a-zA-Z_]*$");
    }
}
