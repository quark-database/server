package ru.anafro.quark.server.language.constructors.modifiers;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.required;

public class DefaultModifierConstructor extends ColumnModifierConstructor {

    public DefaultModifierConstructor() {
        super("default", required("default value", "?"));
    }
}
