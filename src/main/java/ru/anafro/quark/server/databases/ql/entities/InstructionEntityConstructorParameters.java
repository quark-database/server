package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.ql.entities.exceptions.InstructionEntityConstructorParameterAlreadyExistsException;

import java.util.ArrayList;
import java.util.Iterator;

public class InstructionEntityConstructorParameters implements Iterable<InstructionEntityConstructorParameter> {
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

    public InstructionEntityConstructorParameter getParameter(String parameterName) {
        for(var parameter : parameters) {
            if(parameter.name().equals(parameterName)) {
                return parameter;
            }
        }

        return null;
    }

    public boolean has(String parameterName) {
        return getParameter(parameterName) != null;
    }

    public ArrayList<InstructionEntityConstructorParameter> asList() {
        return parameters;
    }

    @Override
    public Iterator<InstructionEntityConstructorParameter> iterator() {
        return parameters.iterator();
    }

    public InstructionEntityConstructorParameter parameterAt(int parameterIndex) {
        return parameters.get(parameterIndex);
    }
}
