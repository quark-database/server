package ru.anafro.quark.server.databases.data.modifiers;

public class AlphaDotModifier extends AbstractRegexColumnModifier {
    public AlphaDotModifier() {
        super("alpha dot", "^[a-zA-Z.]*$");
    }
}
