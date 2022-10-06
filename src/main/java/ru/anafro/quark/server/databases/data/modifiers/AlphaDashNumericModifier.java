package ru.anafro.quark.server.databases.data.modifiers;

public class AlphaDashNumericModifier extends AbstractRegexColumnModifier {
    public AlphaDashNumericModifier() {
        super("alpha dash numeric", "^[a-zA-Z0-9-]*$");
    }
}
