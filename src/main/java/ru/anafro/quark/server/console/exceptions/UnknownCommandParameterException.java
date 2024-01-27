package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.console.CommandArgument;
import ru.anafro.quark.server.console.CommandParameter;

import java.util.List;

import static ru.anafro.quark.server.utils.collections.Collections.join;

public class UnknownCommandParameterException extends CommandException {
    public UnknownCommandParameterException(List<CommandParameter> parameters, CommandArgument argument) {
        super(STR."There is no parameter \{argument.name()} among: \{join(parameters, CommandParameter::name)}");
    }
}
