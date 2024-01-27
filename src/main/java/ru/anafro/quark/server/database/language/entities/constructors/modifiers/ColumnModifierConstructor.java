package ru.anafro.quark.server.database.language.entities.constructors.modifiers;

import ru.anafro.quark.server.database.language.entities.*;
import ru.anafro.quark.server.facade.Quark;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorReturnDescription.returns;

public abstract class ColumnModifierConstructor extends EntityConstructor {
    public ColumnModifierConstructor(String modifierName, InstructionEntityConstructorParameter... parameters) {
        super(
                modifierName,

                returns("the column modifier", "modifier"),
                Stream.of(
                        Arrays.stream(parameters)
                ).flatMap(Function.identity()).toArray(InstructionEntityConstructorParameter[]::new)
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new ColumnModifierEntity(Quark.modifiers().getOrThrow(getName(), "A column modifier with name %s is not found. Did you mean %s?".formatted(getName(), Quark.modifiers().suggest(getName()).getName())), arguments);
    }
}
