package ru.anafro.quark.server.console;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.utils.collections.Collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CommandArguments implements Iterable<CommandArgument> {
    private final ArrayList<CommandArgument> arguments;

    public CommandArguments(CommandArgument... arguments) {
        this.arguments = new ArrayList<>(List.of(arguments));
    }

    public CommandArgument getArgument(String argumentName) {
        for (var argument : arguments) {
            if (argument.named(argumentName)) {
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

    public boolean doesntHave(String argumentName) {
        return !has(argumentName);
    }

    public String get(String argumentName) {
        return getArgument(argumentName).value();
    }

    public String getString(String argumentValue) {
        return get(argumentValue);
    }

    public boolean isNotEmpty() {
        return !arguments.isEmpty();
    }

    @NotNull
    @Override
    public Iterator<CommandArgument> iterator() {
        return arguments.iterator();
    }

    public ArrayList<CommandArgument> toList() {
        return arguments;
    }

    @Override
    public String toString() {
        return Collections.join(arguments, argument -> STR."\{argument.name()} = \{argument.value()}", ", ");
    }

    public Optional<CommandArgument> tryGet(String argumentName) {
        if (doesntHave(argumentName)) {
            return Optional.empty();
        }

        return Optional.of(getArgument(argumentName));
    }

    public Stream<CommandArgument> stream() {
        return arguments.stream();
    }
}
