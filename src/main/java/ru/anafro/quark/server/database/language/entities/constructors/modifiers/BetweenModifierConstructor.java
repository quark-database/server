package ru.anafro.quark.server.database.language.entities.constructors.modifiers;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter.required;

public class BetweenModifierConstructor extends ColumnModifierConstructor {
    public BetweenModifierConstructor() {
        super(
                "require between",

                required("min", "float"),
                required("max", "float")
        );
    }
}
