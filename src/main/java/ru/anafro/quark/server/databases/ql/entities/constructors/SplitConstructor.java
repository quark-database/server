package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

import java.util.stream.Stream;

public class SplitConstructor extends InstructionEntityConstructor {
    public SplitConstructor() {
        super("split", InstructionEntityConstructorParameter.required("string to split", "str"), InstructionEntityConstructorParameter.required("delimiter", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new ListEntity("str", Stream.of(arguments.<StringEntity>get("string to split").getString().split(arguments.<StringEntity>get("delimiter").getString())).map(StringEntity::new).toList());
    }
}
