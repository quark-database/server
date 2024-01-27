package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.console.CommandParameterType;

public class CommandArgumentTypeDoesNotHaveIsSuitableValueFunctionDefinedException extends CommandException {
    public CommandArgumentTypeDoesNotHaveIsSuitableValueFunctionDefinedException(CommandParameterType type) {
        super(STR."Command argument type \{type.name()} was told to check the validity of value, but there's no 'isValueSuitableFunction' set inside this type");
    }
}
