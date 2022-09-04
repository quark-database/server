package ru.anafro.quark.server.databases.ql.entities.constructors.modifiers;

public class NotNegativeModifierConstructor extends ColumnModifierConstructor {
    public NotNegativeModifierConstructor() {
        super("require not negative");
    }
}
