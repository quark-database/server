package ru.anafro.quark.server.database.language.entities.constructors.modifiers;

public class RequireUniqueModifierConstructor extends ColumnModifierConstructor {
    public RequireUniqueModifierConstructor() {
        super("unique");
    }
}
