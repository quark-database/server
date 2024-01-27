package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.console.CommandArgument;
import ru.anafro.quark.server.console.CommandParameter;

public class CommandArgumentHasWrongTypeException extends CommandException {
    public CommandArgumentHasWrongTypeException(CommandParameter parameter, CommandArgument argument) {
        super(STR."The \{parameter.type().name().toLowerCase()} parameter '\{parameter.name()}' can be '\{argument.value()}'.");
    }
}
