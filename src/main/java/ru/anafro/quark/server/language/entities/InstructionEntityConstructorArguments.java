package ru.anafro.quark.server.language.entities;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.language.parser.exceptions.InstructionParserException;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.integers.Integers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class InstructionEntityConstructorArguments implements Iterable<InstructionEntityConstructorArgument> {
    private final List<InstructionEntityConstructorArgument> arguments;

    public InstructionEntityConstructorArguments(InstructionEntityConstructorArgument... arguments) {
        this.arguments = new ArrayList<>(List.of(arguments));
    }

    public static InstructionEntityConstructorArguments positional(EntityConstructor constructor, Object[] nativeArguments) {
        var arguments = new InstructionEntityConstructorArgument[nativeArguments.length];
        var parameters = constructor.getParameters();

        for (var index = 0; index < arguments.length; index += 1) {
            var argument = arguments[index];
            var parameter = parameters.parameterAt(Integers.indexLimit(index, parameters.count()));

            arguments[index] = new InstructionEntityConstructorArgument(parameter.name(), Entity.wrap(argument));
        }

        return new InstructionEntityConstructorArguments(arguments);
    }

    public boolean has(String argumentName) {
        return getArgument(argumentName) != null;
    }

    public boolean doesntHave(String argumentName) {
        return !has(argumentName);
    }

    public Entity getEntity(String argumentName) {
        return getArgument(argumentName).getEntity();
    }

    public InstructionEntityConstructorArgument getArgument(String argumentName) {
        for (var argument : arguments) {
            if (argument.getName().equals(argumentName)) {
                return argument;
            }
        }

        return null;
    }

    public void add(InstructionEntityConstructorArgument argument) {
        if (has(argument.getName())) {
            throw new InstructionParserException("An argument with name %s already exists".formatted(quoted(argument.getName())));
        }

        arguments.add(argument);
    }

    public void add(String argumentName, Entity argumentValue) {
        var argument = new InstructionEntityConstructorArgument(argumentName, argumentValue);
        add(argument);
    }

    @NotNull
    @Override
    public Iterator<InstructionEntityConstructorArgument> iterator() {
        return arguments.iterator();
    }

    private <T extends Entity> T get(Class<T> type, String argumentName) {
        if (doesntHave(argumentName)) {
            throw new DatabaseException("Requesting an argument %s, which does not exist".formatted(quoted(argumentName))); // TODO: Make a new exception type
        }

        return type.cast(getArgument(argumentName).getEntity());
    }

    private <T extends Entity> Optional<T> tryGet(Class<T> type, String argumentName) {
        if (doesntHave(argumentName)) {
            return Optional.empty();
        }

        return Optional.of(get(type, argumentName));
    }

    public <T extends Entity> T getOrAdd(Class<T> type, String argumentName, T ifDoesntExist) {
        if (doesntHave(argumentName)) {
            add(argumentName, ifDoesntExist);
            return ifDoesntExist;
        }

        return get(type, argumentName);
    }

    public String getString(String argumentName) {
        return this.get(StringEntity.class, argumentName).getValue();
    }

    public int getInt(String argumentName) {
        return this.get(IntegerEntity.class, argumentName).getValue();
    }

    public Optional<Integer> tryGetInt(String argumentName) {
        return this.tryGet(IntegerEntity.class, argumentName).map(IntegerEntity::getValue);
    }

    public boolean getBoolean(String argumentName) {
        return this.get(BooleanEntity.class, argumentName).getValue();
    }

    public Optional<Boolean> tryGetBoolean(String argumentName) {
        return this.tryGet(BooleanEntity.class, argumentName).map(BooleanEntity::getValue);
    }

    public float getFloat(String argumentName) {
        return this.get(FloatEntity.class, argumentName).getValue();
    }

    public Optional<Float> tryGetFloat(String argumentName) {
        return this.tryGet(FloatEntity.class, argumentName).map(FloatEntity::getValue);
    }

    public Entity getObject(String argumentName) {
        return this.get(Entity.class, argumentName);
    }

    public ListEntity getList(String argumentName) {
        return this.get(ListEntity.class, argumentName);
    }

    public <T extends Entity> List<T> getList(Class<T> elementsType, String argumentName) {
        return this.get(ListEntity.class, argumentName).tryGetValueAsListOf(elementsType).orElseThrow();
    }

    public List<Entity> toList() {
        return arguments.stream().map(InstructionEntityConstructorArgument::getEntity).toList();
    }

    public List<InstructionEntityConstructorArgument> getArgumentsAsList() {
        return arguments;
    }

    public long getLong(String argumentName) {
        return this.get(LongEntity.class, argumentName).getValue();
    }

    @Override
    public String toString() {
        return Lists.join(arguments, argument -> STR."\{argument.getName()} = \{argument.getEntity().toInstructionForm()}");
    }
}
