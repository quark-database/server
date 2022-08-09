package ru.anafro.quark.server.console;

import ru.anafro.quark.server.console.exceptions.NoSuchCommandArgumentException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandArguments implements Iterable<CommandArgument> {
    private final ArrayList<CommandArgument> arguments;

    public CommandArguments(CommandArgument... arguments) {
        this.arguments = new ArrayList<>(List.of(arguments));
    }

    public CommandArgument getArgument(String argumentName) {
        for(var argument : arguments) {
            if(argument.named(argumentName)) {
                return argument;
            }
        }

        return null;
    }

    public void add(CommandArgument argument) {
        arguments.add(argument);
    }

    public void addEmpty(String argumentName) {
        add(new CommandArgument(argumentName, ""));
    }

    public boolean has(String argumentName) {
        return getArgument(argumentName) != null;
    }

    public String get(String argumentName) {
        return getArgument(argumentName).value();
    }

    public String getString(String argumentValue) {
        return get(argumentValue);
    }

    public int getInteger(String argumentName) {
        return (int) getArgument(argumentName).as(CommandParameterType.INTEGER);
    }

    public float getFloat(String argumentName) {
        return (float) getArgument(argumentName).as(CommandParameterType.FLOAT);
    }

    public boolean getBoolean(String argumentName) {
        return (boolean) getArgument(argumentName).as(CommandParameterType.BOOLEAN);
    }

    @Override
    public Iterator<CommandArgument> iterator() {
        return arguments.iterator();
    }

    public ArrayList<CommandArgument> toList() {
        return arguments;
    }
}
