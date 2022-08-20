package ru.anafro.quark.server.utils.exceptions;

public class CallingUtilityConstructorException extends UtilityException {
    public CallingUtilityConstructorException(Class<?> utilityClass) {
        super("new " + utilityClass.getSimpleName() + "(...) call is illegal, because " + utilityClass.getName() + " is a utility class. Call static methods inside instead.");
    }
}
