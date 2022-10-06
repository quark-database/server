package ru.anafro.quark.server.databases.data.modifiers;

public class AlphaDashModifier extends AbstractRegexColumnModifier {
    public AlphaDashModifier() {
        super("alpha dash", "^[A-Za-z_]*$");
    }
}
