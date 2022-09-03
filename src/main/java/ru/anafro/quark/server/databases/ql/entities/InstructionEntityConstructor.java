package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.ql.entities.exceptions.ConstructorEvaluationException;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.strings.TextBuffer;

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

    protected abstract Entity invoke(InstructionEntityConstructorArguments arguments);

    public Entity eval(InstructionEntityConstructorArguments arguments) {
        if(parameters.hasVarargs() && !arguments.has(parameters.getVarargs().name())) {
            arguments.add(new InstructionEntityConstructorArgument(parameters.getVarargs().name(), new ListEntity(parameters.getVarargs().type())));
        }

        for(var argument : arguments) {
            if(parameters.has(argument.getName())) {
                var parameter = parameters.getParameter(argument.getName());

                if(argument.getEntity() == null) {
                    throw new ConstructorEvaluationException(this, "An argument %s must not be null.".formatted(argument.getName()));
                }

                if(!argument.getEntity().getTypeName().equals(parameter.type()) && !parameter.isWildcard()) { // TODO (maybe): TL;DR
                    throw new ConstructorEvaluationException(this, "An argument you passed has type %s, but must have type %s".formatted(quoted(argument.getEntity().getType().getName()), quoted(parameters.getParameter(argument.getName()).type())));
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
        TextBuffer syntax = new TextBuffer();

        syntax.append(ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER);
        syntax.append(name);
        syntax.append('(');

        syntax.append(Lists.join(parameters.asList().stream().map(parameter -> parameter.name() + " is " + parameter.type() + (parameter.required() ? "" : " optional" ) + (parameter.isVarargs() ? InstructionEntityConstructorParameter.VARARGS_TYPE_MARKER : "")).collect(Collectors.toList()), ", "));

        syntax.append(')');

        return syntax.extractContent();
    }
}
