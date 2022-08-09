package ru.anafro.quark.server.databases.instructions.entities;

import ru.anafro.quark.server.databases.instructions.entities.exceptions.InstructionEntityConstructorParameterAlreadyExistsException;

import java.util.ArrayList;

public class InstructionEntityConstructorParameters {
    private final ArrayList<InstructionEntityConstructorParameter> parameters = new ArrayList<>();

    public InstructionEntityConstructorParameters(InstructionEntityConstructorParameter... parameters) {
        for(var parameter : parameters) {
            add(parameter);
        }
    }

    public void add(InstructionEntityConstructorParameter parameter) {
        if(has(parameter.name())) {
            throw new InstructionEntityConstructorParameterAlreadyExistsException(parameter.name());
        }

        parameters.add(parameter);
    }

    public InstructionEntityConstructorParameter get(String parameterName) {
        for(var parameter : parameters) {
            if(parameter.name().equals(parameterName)) {
                return parameter;
            }
        }

        return null;
    }

    public boolean has(String parameterName) {
        return get(parameterName) != null;
    }
}
