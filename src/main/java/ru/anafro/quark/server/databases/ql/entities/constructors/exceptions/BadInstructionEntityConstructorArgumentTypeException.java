package ru.anafro.quark.server.databases.ql.entities.constructors.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;

public class BadInstructionEntityConstructorArgumentTypeException extends DatabaseException { // This should be viewed in code editor, change to InstructionSyntaxException
    public BadInstructionEntityConstructorArgumentTypeException(InstructionEntityConstructor constructor, String parameterName) {
        super("%s's value passed to constructor %s is wrong, because it should have type %s. Check out this constructor syntax: %s".formatted(parameterName, constructor.getName(), constructor.getParameters().getParameter(parameterName).type(), constructor.getSyntax()));
    }
}
