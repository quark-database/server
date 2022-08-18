package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.databases.ql.entities.InstructionEntity;
import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;

import java.util.ArrayList;
import java.util.Iterator;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class InstructionArguments implements Iterable<InstructionArgument> {
    private final ArrayList<InstructionArgument> arguments = new ArrayList<>();

    public InstructionArguments(InstructionArgument... arguments) {
        for(var argument : arguments) {
            add(argument);
        }
    }

    public ArrayList<InstructionArgument> asList() {
        return arguments;
    }

    public void add(InstructionArgument argument) {
        if(has(argument.name())) {
            throw new InstructionSyntaxException(this, "You already set the %s's value".formatted(argument.name()), "Please, remove the repeating setting of argument " + argument.name());
        } else {
            arguments.add(argument);
        }
    }

    public boolean has(String argumentName) {
        return getArgument(argumentName) != null;
    }

    public <T extends InstructionEntity> T get(String argumentName) {
        if(!has(argumentName)) {
            throw new InstructionSyntaxException(this, "Argument %s is requested, but wasn't provided".formatted(quoted(argumentName)), "Please, add this argument when you use the instruction");
        }

        return (T) getArgument(argumentName).value();
    }

    public InstructionArgument getArgument(String argumentName) {
        for(var argument : arguments) {
            if(argument.name().equals(argumentName)) {
                return argument;
            }
        }

        return null;
    }

    @Override
    public Iterator<InstructionArgument> iterator() {
        return arguments.iterator();
    }

    public int size() {
        return arguments.size();
    }
}
