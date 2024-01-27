package ru.anafro.quark.server.console;

import ru.anafro.quark.server.console.exceptions.CommandArgumentTypeDoesNotHaveIsSuitableValueFunctionDefinedException;
import ru.anafro.quark.server.utils.types.Booleans;
import ru.anafro.quark.server.utils.validation.Validators;

import java.util.function.Function;

public enum CommandParameterType {
    STRING(_ -> true),
    INTEGER(Validators.INTEGER_STRING::isValid),
    FLOAT(Validators.FLOAT_STRING::isValid),
    BOOLEAN(Booleans::canBeCreatedFromString);

    private final Function<String, Boolean> isValueSuitableFunction;

    CommandParameterType(Function<String, Boolean> isValueSuitable) {
        this.isValueSuitableFunction = isValueSuitable;
    }

    public boolean isValueSuitable(String value) {
        if (!isValueSuitableFunctionDefined()) {
            throw new CommandArgumentTypeDoesNotHaveIsSuitableValueFunctionDefinedException(this);
        }

        return isValueSuitableFunction.apply(value);
    }

    public boolean isValueSuitableFunctionDefined() {  // TODO: whats this??
        return isValueSuitableFunction != null;
    }
}
