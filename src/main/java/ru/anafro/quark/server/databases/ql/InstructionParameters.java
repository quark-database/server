package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.exceptions.InstructionMustHaveOnlyOneGeneralParameterException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class InstructionParameters implements Iterable<InstructionParameter> {
    public final List<InstructionParameter> parameters = new ArrayList<>();

    public InstructionParameters(InstructionParameter... parameters) {
        for(var parameter : parameters) {
            add(parameter);
        }
    }

    public boolean has(String parameterName) {
        return get(parameterName) != null;
    }

    public InstructionParameter get(String parameterName) {
        for(var parameter : parameters) {
            if(parameter.getName().equals(parameterName)) {
                return parameter;
            }
        }

        return null;
    }

    public boolean hasGeneralParameter() {
        return getGeneralParameter() != null;
    }

    public void add(InstructionParameter parameter) {
        if(this.hasGeneralParameter() && parameter.isGeneral()) {
            throw new InstructionMustHaveOnlyOneGeneralParameterException(getGeneralParameter().getName(), parameter.getName());
        }

        parameters.add(parameter);
    }

    public InstructionParameter getGeneralParameter() {
        for(var parameter : parameters) {
            if(parameter.isGeneral()) {
                return parameter;
            }
        }

        return null;
    }

    public List<InstructionParameter> asList() {
        return parameters;
    }

    @Override
    public Iterator<InstructionParameter> iterator() {
        return parameters.iterator();
    }

    public InstructionParameter parameterAt(int index) {
        if(index < 0 || index >= parameters.size()) {
            throw new DatabaseException("Cannot get a constructor's parameter at index " + index);
        }

        return parameters.get(index);
    }

    public Stream<InstructionParameter> stream() {
        return parameters.stream();
    }
}
