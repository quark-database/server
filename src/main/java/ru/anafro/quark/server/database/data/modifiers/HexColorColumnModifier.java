package ru.anafro.quark.server.database.data.modifiers;

public class HexColorColumnModifier extends AbstractRegexColumnModifier {
    public HexColorColumnModifier() {
        super("hex color", "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    }
}
