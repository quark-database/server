package ru.anafro.quark.server.database.language.entities.constructors.modifiers;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter.required;

public class DefaultModifierConstructor extends ColumnModifierConstructor {

    public DefaultModifierConstructor() {
        super("default", required("default value", "?"));
    }
}
