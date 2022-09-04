package ru.anafro.quark.server.databases.ql.entities.constructors.modifiers;

public class UniqueModifierConstructor extends ColumnModifierConstructor {
    public UniqueModifierConstructor() {
        super("require unique");
    }
}
