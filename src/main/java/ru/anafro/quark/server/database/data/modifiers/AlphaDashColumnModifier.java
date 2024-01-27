package ru.anafro.quark.server.database.data.modifiers;

public class AlphaDashColumnModifier extends AbstractRegexColumnModifier {
    public AlphaDashColumnModifier() {
        super("alpha dash", "^[A-Za-z_]*$");
    }
}
