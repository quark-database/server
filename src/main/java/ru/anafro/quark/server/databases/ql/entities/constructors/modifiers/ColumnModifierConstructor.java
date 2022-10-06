package ru.anafro.quark.server.databases.ql.entities.constructors.modifiers;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.entities.*;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.varargs;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

public abstract class ColumnModifierConstructor extends EntityConstructor {
    public ColumnModifierConstructor(String modifierName, InstructionEntityConstructorParameter... parameters) {
        super(
                modifierName,

                returns("the column modifier", "modifier"),
                Stream.of(
                        Stream.of(required("column name", "str")),
                        Arrays.stream(parameters),
                        Stream.of(varargs("modifiers", "modifier"))
                ).flatMap(Function.identity()).toArray(InstructionEntityConstructorParameter[]::new)
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new ColumnModifierEntity(arguments.<StringEntity>get("column name").getValue(), Quark.modifiers().getOrThrow(getName(), "A column modifier with name %s is not found. Did you mean %s?".formatted(getName(), Quark.modifiers().suggest(getName()).getName())), arguments);
    }
}
