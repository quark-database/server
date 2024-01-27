package ru.anafro.quark.server.database.language.entities.constructors.modifiers;

public class NotNegativeModifierConstructor extends ColumnModifierConstructor {
    public NotNegativeModifierConstructor() {
        super("require not negative");
    }
}
