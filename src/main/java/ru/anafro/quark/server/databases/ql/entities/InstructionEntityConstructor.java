package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.ql.entities.exceptions.ConstructorEvaluationException;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.strings.StringBuffer;

import java.util.stream.Collectors;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public abstract class InstructionEntityConstructor {
    private final String name;
    private final InstructionEntityConstructorParameters parameters;

    public InstructionEntityConstructor(String name, InstructionEntityConstructorParameter... parameters) {
        this.name = name;
        this.parameters = new InstructionEntityConstructorParameters(parameters);
    }

    public String getName() {
        return name;
    }

    public InstructionEntityConstructorParameters getParameters() {
        return parameters;
    }

    protected abstract InstructionEntity invoke(InstructionEntityConstructorArguments arguments);

    public InstructionEntity eval(InstructionEntityConstructorArguments arguments) {
        for(var argument : arguments) {
            if(parameters.has(argument.getName())) {
                if(argument.getEntity() == null) {
                    throw new ConstructorEvaluationException(this, "An argument %s must not be null.".formatted(argument.getName()));
                }

                if(!argument.getEntity().getName().equals(parameters.getParameter(argument.getName()).type())) { // TODO (maybe): TL;DR
                    throw new ConstructorEvaluationException(this, "An argument you passed has type %s, but must have type %s".formatted(quoted(argument.getEntity().getName()), quoted(parameters.getParameter(argument.getName()).type())));
                }
            } else {
                throw new ConstructorEvaluationException(this, "You added an argument %s, but it does not exist in parameter list of this constructor.".formatted(quoted(argument.getName())));
            }
        }

        for(var parameter : parameters) {
            if(parameter.required() && !arguments.has(parameter.name())) {
                throw new ConstructorEvaluationException(this, "You have to add an argument %s, because this parameter is required.".formatted(parameter.name()));
            }
        }

        return invoke(arguments);
    }

    public String getSyntax() {
        StringBuffer syntax = new StringBuffer();

        syntax.append(ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER);
        syntax.append(name);
        syntax.append('(');

        syntax.append(Lists.join(parameters.asList().stream().map(parameter -> parameter.name() + " is " + parameter.type() + (parameter.required() ? "" : " optional" )).collect(Collectors.toList()), ", "));

        syntax.append(')');

        return syntax.extractContent();
    }
}
