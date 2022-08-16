package ru.anafro.quark.server.console;

import ru.anafro.quark.server.console.exceptions.CommandArgumentTypeDoesNotHaveIsSuitableValueFunctionDefinedException;
import ru.anafro.quark.server.utils.validation.Validators;

import java.util.function.Function;

public enum CommandParameterType {
    STRING(value -> true),
    INTEGER(Validators.INTEGER_STRING::isValid),
    FLOAT(Validators.FLOAT_STRING::isValid),
    BOOLEAN(value -> value.equals("true") || value.equals("false"));

    private final Function<String, Boolean> isValueSuitableFunction;

    CommandParameterType(Function<String, Boolean> isValueSuitable) {
        this.isValueSuitableFunction = isValueSuitable;
    }

    public boolean isValueSuitable(String value) {
        if(!isValueSuitableFunctionDefined()) {
            throw new CommandArgumentTypeDoesNotHaveIsSuitableValueFunctionDefinedException(this);
        }

        return isValueSuitableFunction.apply(value);
    }

    public boolean isValueSuitableFunctionDefined() {
        return isValueSuitableFunction != null;
    }
}
