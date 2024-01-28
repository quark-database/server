package ru.anafro.quark.server.language;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.exceptions.InstructionMustHaveOnlyOneGeneralParameterException;
import ru.anafro.quark.server.utils.collections.Collections;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class InstructionParameters implements Iterable<InstructionParameter> {
    public final List<InstructionParameter> parameters = Lists.empty();

    public InstructionParameters(InstructionParameter... parameters) {
        for (var parameter : parameters) {
            add(parameter);
        }
    }

    public boolean has(String parameterName) {
        return get(parameterName) != null;
    }

    public boolean doesntHave(InstructionArgument argument) {
        return doesntHave(argument.name());
    }

    public boolean doesntHave(String parameterName) {
        return !has(parameterName);
    }

    public InstructionParameter get(String parameterName) {
        for (var parameter : parameters) {
            if (parameter.getName().equals(parameterName)) {
                return parameter;
            }
        }

        return null;
    }

    public boolean hasGeneralParameter() {
        return getGeneralParameter() != null;
    }

    public void add(InstructionParameter parameter) {
        if (this.hasGeneralParameter() && parameter.isGeneral()) {
            throw new InstructionMustHaveOnlyOneGeneralParameterException(getGeneralParameter().getName(), parameter.getName());
        }

        parameters.add(parameter);
    }

    @Deprecated(since = "3")
    public InstructionParameter getGeneralParameter() {
        for (var parameter : parameters) {
            if (parameter.isGeneral()) {
                return parameter;
            }
        }

        return null;
    }

    public Optional<InstructionParameter> tryGetGeneralParameter() {
        for (var parameter : parameters) {
            if (parameter.isGeneral()) {
                return Optional.of(parameter);
            }
        }

        return Optional.empty();
    }

    @NotNull
    @Override
    public Iterator<InstructionParameter> iterator() {
        return parameters.iterator();
    }

    public Stream<InstructionParameter> stream() {
        return parameters.stream();
    }

    public List<InstructionParameter> getAdditionalParameters() {
        return stream().filter(InstructionParameter::isAdditional).toList();
    }

    public int getAdditionalParameterCount() {
        return getAdditionalParameters().size();
    }

    public boolean hasAdditionalParameters() {
        return getAdditionalParameterCount() > 0;
    }

    public String format(InstructionArguments arguments) {
        return Collections.join(getAdditionalParameters()
                        .stream()
                        .filter(parameter -> arguments.has(parameter.getName()))
                        .toList(),
                parameter -> parameter.format(arguments.get(parameter.getName())),
                ",\n"
        );
    }
}
