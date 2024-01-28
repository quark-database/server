package ru.anafro.quark.server.language.entities;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.language.entities.exceptions.ConstructorHasNoVarargsException;
import ru.anafro.quark.server.language.entities.exceptions.InstructionEntityConstructorParameterAlreadyExistsException;
import ru.anafro.quark.server.language.entities.exceptions.VarargsParameterInConstructorAlreadyExistsException;
import ru.anafro.quark.server.language.entities.exceptions.VarargsParameterMustBeTheLastParameterException;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.strings.English;

import java.util.ArrayList;
import java.util.Iterator;

public class InstructionEntityConstructorParameters implements Iterable<InstructionEntityConstructorParameter> {
    private final ArrayList<InstructionEntityConstructorParameter> parameters = Lists.empty();

    public InstructionEntityConstructorParameters(InstructionEntityConstructorParameter... parameters) {
        for (int index = 0; index < parameters.length; index++) {
            var parameter = parameters[index];

            if (parameter.isVarargs() && index != parameters.length - 1) {
                throw new VarargsParameterMustBeTheLastParameterException(parameter, index, parameters.length);
            }

            add(parameter);
        }
    }

    public void add(InstructionEntityConstructorParameter parameter) {
        if (has(parameter.name())) {
            throw new InstructionEntityConstructorParameterAlreadyExistsException(parameter.name());
        }

        if (hasVarargs() && parameter.isVarargs()) {
            throw new VarargsParameterInConstructorAlreadyExistsException(getVarargs());
        }

        parameters.add(parameter);
    }

    public InstructionEntityConstructorParameter get(String parameterName) {
        for (var parameter : parameters) {
            if (parameter.name().equals(parameterName)) {
                return parameter;
            }
        }

        return null;
    }

    public boolean hasVarargs() {
        if (parameters.isEmpty()) {
            return false;
        }

        return parameterAt(parameters.size() - 1).isVarargs();
    }

    public InstructionEntityConstructorParameter getVarargs() {
        if (!hasVarargs()) {
            throw new ConstructorHasNoVarargsException();
        }

        return parameterAt(parameters.size() - 1);
    }

    public boolean has(String parameterName) {
        return get(parameterName) != null;
    }

    public ArrayList<InstructionEntityConstructorParameter> asList() {
        return parameters;
    }

    @NotNull
    @Override
    public Iterator<InstructionEntityConstructorParameter> iterator() {
        return parameters.iterator();
    }

    public InstructionEntityConstructorParameter parameterAt(int parameterIndex) {
        if (parameterIndex >= parameters.size()) {
            throw new DatabaseException("Cannot get the %d%s parameter, because there are only %d parameters".formatted(parameterIndex, English.ordinalSuffixFor(parameterIndex), parameters.size())); // TODO: Create a new exception type
        }

        return parameters.get(parameterIndex);
    }

    public int count() {
        return parameters.size();
    }
}
