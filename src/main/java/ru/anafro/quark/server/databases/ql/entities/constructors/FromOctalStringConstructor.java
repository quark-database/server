package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class FromOctalStringConstructor extends InstructionEntityConstructor {
    public FromOctalStringConstructor() {
        super("from octal string", InstructionEntityConstructorParameter.required("octal string to convert to integer", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new IntegerEntity(Integer.parseInt(arguments.<StringEntity>get("octal string to convert to integer").getString(), 8));
    }
}
