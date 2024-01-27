package ru.anafro.quark.server.database.data.modifiers;

public class AlphaDashDotUnderscoreColumnModifier extends AbstractRegexColumnModifier {
    public AlphaDashDotUnderscoreColumnModifier() {
        super("alpha dash dot", "^[A-Za-z._]$");
    }
}
