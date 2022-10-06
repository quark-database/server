package ru.anafro.quark.server.databases.data.modifiers;

public class AlphaDashDotUnderscoreModifier extends AbstractRegexColumnModifier {
    public AlphaDashDotUnderscoreModifier() {
        super("alpha dash dot", "^[A-Za-z._]$");
    }
}
