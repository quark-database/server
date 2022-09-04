package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.utils.strings.Strings;

public class InvertCaseConstructor extends InstructionEntityConstructor {
    public InvertCaseConstructor() {
        super("invert case", InstructionEntityConstructorParameter.required("string where to switch case", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new StringEntity(Strings.invertCase(arguments.<StringEntity>get("string where to switch case").getString()));
    }
}
