package ru.anafro.quark.server.database.data.modifiers;

public class AlphaDotColumnModifier extends AbstractRegexColumnModifier {
    public AlphaDotColumnModifier() {
        super("alpha dot", "^[a-zA-Z.]*$");
    }
}
