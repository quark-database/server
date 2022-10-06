package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.parser.exceptions.InstructionParserException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class InstructionEntityConstructorArguments implements Iterable<InstructionEntityConstructorArgument> {
    private final List<InstructionEntityConstructorArgument> arguments;

    public InstructionEntityConstructorArguments(List<InstructionEntityConstructorArgument> arguments) {
        this.arguments = arguments;
    }

    public InstructionEntityConstructorArguments(InstructionEntityConstructorArgument... arguments) {
        this.arguments = new ArrayList<>(List.of(arguments));
    }

    public boolean has(String argumentName) {
        return getArgument(argumentName) != null;
    }

    public InstructionEntityConstructorArgument getArgument(String argumentName) {
        for(var argument : arguments) {
            if(argument.getName().equals(argumentName)) {
                return argument;
            }
        }

        return null;
    }

    public void add(InstructionEntityConstructorArgument argument) {
        if(has(argument.getName())) {
            throw new InstructionParserException("An argument with name %s already exists".formatted(quoted(argument.getName())));
        }

        arguments.add(argument);
    }

    @Override
    public Iterator<InstructionEntityConstructorArgument> iterator() {
        return arguments.iterator();
    }

    public <T extends Entity> T get(String argumentName) {
        if(!has(argumentName)) {
            throw new DatabaseException("Requesting an argument %s, which does not exist".formatted(quoted(argumentName))); // TODO: Make a new exception type
        }

        return (T) getArgument(argumentName).getEntity();
    }

    public String getString(String argumentName) {
        return this.<StringEntity>get(argumentName).getValue();
    }

    public int getInt(String argumentName) {
        return this.<IntegerEntity>get(argumentName).getValue();
    }

    public boolean getBoolean(String argumentName) {
        return this.<BooleanEntity>get(argumentName).getValue();
    }

    public float getFloat(String argumentName) {
        return this.<FloatEntity>get(argumentName).getValue();
    }

    public Object getObject(String argumentName) {
        return this.get(argumentName).getValue();
    }

    public ArrayList<Entity> getList(String argumentName) {
        return this.<ListEntity>get(argumentName).getValue();
    }

    public List<Entity> toList() {
        return arguments.stream().map(InstructionEntityConstructorArgument::getEntity).toList();
    }

    public List<InstructionEntityConstructorArgument> getArgumentsAsList() {
        return arguments;
    }

    public long getLong(String argumentName) {
        return this.<LongEntity>get(argumentName).getValue();
    }
}
