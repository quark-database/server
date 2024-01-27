package ru.anafro.quark.server.database.data.modifiers;

public class AlphaDashNumericColumnModifier extends AbstractRegexColumnModifier {
    public AlphaDashNumericColumnModifier() {
        super("alpha dash numeric", "^[a-zA-Z0-9-]*$");
    }
}
