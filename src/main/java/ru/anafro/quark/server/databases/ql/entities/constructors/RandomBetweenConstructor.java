package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

import java.util.Random;

public class RandomBetweenConstructor extends InstructionEntityConstructor {
    private static final Random random = new Random();

    public RandomBetweenConstructor() {
        super("random between", InstructionEntityConstructorParameter.required("inclusive min", "int"), InstructionEntityConstructorParameter.required("exclusive max", "int"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        IntegerEntity min = arguments.get("inclusive min");
        IntegerEntity max = arguments.get("exclusive max");

        return new IntegerEntity(random.nextInt(min.getValue(), max.getValue()));
    }
}
