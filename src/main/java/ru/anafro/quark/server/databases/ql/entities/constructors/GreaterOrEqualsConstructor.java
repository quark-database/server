package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class GreaterOrEqualsConstructor extends InstructionEntityConstructor {
    public GreaterOrEqualsConstructor() {
        super("greater or equals", InstructionEntityConstructorParameter.required("first number", "float"), InstructionEntityConstructorParameter.required("second number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new BooleanEntity(arguments.<FloatEntity>get("first number").getValue() >= arguments.<FloatEntity>get("second number").getValue());
    }
}