package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class MaxConstructor extends InstructionEntityConstructor {
    public MaxConstructor() {
        super("max", InstructionEntityConstructorParameter.required("first number", "float"), InstructionEntityConstructorParameter.varargs("more numbers", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        float maxNumber = arguments.<FloatEntity>get("float entity").getValue();

        for(var numberEntity : arguments.<ListEntity>get("more numbers")) {
            var currentNumber = numberEntity.valueAs(Float.class);

            if(currentNumber > maxNumber) {
                maxNumber = currentNumber;
            }
        }

        return new FloatEntity(maxNumber);
    }
}
