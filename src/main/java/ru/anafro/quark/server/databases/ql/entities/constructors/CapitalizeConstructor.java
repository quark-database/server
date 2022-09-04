package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.utils.strings.Strings;

public class CapitalizeConstructor extends InstructionEntityConstructor {
    public CapitalizeConstructor() {
        super("capitalize", InstructionEntityConstructorParameter.required("string to capitalize", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new StringEntity(Strings.capitalize(arguments.<StringEntity>get("string to capitalize").getString()));
    }
}
