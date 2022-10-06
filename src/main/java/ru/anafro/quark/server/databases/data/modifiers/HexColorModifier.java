package ru.anafro.quark.server.databases.data.modifiers;

public class HexColorModifier extends AbstractRegexColumnModifier {
    public HexColorModifier() {
        super("hex color", "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    }
}
