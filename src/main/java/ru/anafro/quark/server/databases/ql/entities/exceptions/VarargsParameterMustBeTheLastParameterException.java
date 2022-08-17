package ru.anafro.quark.server.databases.ql.entities.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter;
import ru.anafro.quark.server.utils.strings.English;

public class VarargsParameterMustBeTheLastParameterException extends DatabaseException {
    public VarargsParameterMustBeTheLastParameterException(InstructionEntityConstructorParameter varargsParameter, int foundAtIndex, int parameterCount) {
        super("The %d%s constructor parameter %s is marked as a vararg, but it is not the last parameter (total parameter count is %d)".formatted(
                foundAtIndex,
                English.ordinalSuffixFor(foundAtIndex),
                varargsParameter,
                parameterCount
        ));
    }
}
