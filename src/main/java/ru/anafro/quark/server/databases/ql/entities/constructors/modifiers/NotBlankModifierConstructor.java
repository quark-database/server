package ru.anafro.quark.server.databases.ql.entities.constructors.modifiers;

public class NotBlankModifierConstructor extends ColumnModifierConstructor {
    public NotBlankModifierConstructor() {
        super("not blank");
    }
}