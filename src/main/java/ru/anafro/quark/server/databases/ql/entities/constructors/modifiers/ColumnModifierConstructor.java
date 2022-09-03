package ru.anafro.quark.server.databases.ql.entities.constructors.modifiers;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.entities.*;

import java.util.Arrays;
import java.util.stream.Stream;

public abstract class ColumnModifierConstructor extends InstructionEntityConstructor {
    public ColumnModifierConstructor(String modifierName, InstructionEntityConstructorParameter... parameters) {
        super(modifierName, Stream.concat(Stream.of(InstructionEntityConstructorParameter.required("column name", "str")), Arrays.stream(parameters)).toArray(InstructionEntityConstructorParameter[]::new));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new ColumnModifierEntity(arguments.<StringEntity>get("column name").getValue(), Quark.modifiers().get(getName()), arguments);
    }
}
