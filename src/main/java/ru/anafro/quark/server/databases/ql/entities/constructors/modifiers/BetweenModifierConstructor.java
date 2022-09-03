package ru.anafro.quark.server.databases.ql.entities.constructors.modifiers;

import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter;

public class BetweenModifierConstructor extends ColumnModifierConstructor {
    public BetweenModifierConstructor() {
        super("between",
                InstructionEntityConstructorParameter.required("min", "float"),
                InstructionEntityConstructorParameter.required("max", "float")
        );
    }
}
