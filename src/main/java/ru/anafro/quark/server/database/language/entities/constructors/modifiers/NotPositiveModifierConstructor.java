package ru.anafro.quark.server.database.language.entities.constructors.modifiers;

public class NotPositiveModifierConstructor extends ColumnModifierConstructor {
    public NotPositiveModifierConstructor() {
        super("require not positive");
    }
}
