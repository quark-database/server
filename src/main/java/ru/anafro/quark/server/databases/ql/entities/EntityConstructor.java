package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.entities.exceptions.ConstructorEvaluationException;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public abstract class EntityConstructor {
    private final String name;
    private final InstructionEntityConstructorParameters parameters;
    private final InstructionEntityConstructorReturnDescription returnDescription;

    public EntityConstructor(String name, InstructionEntityConstructorReturnDescription returnDescription, InstructionEntityConstructorParameter... parameters) {
        this.name = name;
        this.returnDescription = returnDescription;
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

                if(!argument.getEntity().getTypeName().equals(parameter.type()) && !parameter.isWildcard()) {
                    if(Quark.types().get(parameter.type()).castableFrom(argument.getEntity().getType())) {
                         argument.setEntity(Quark.types().get(parameter.type()).cast(argument.getEntity()));
                    } else if(parameter.isVarargs() && !((ListEntity) argument.getEntity()).getTypeOfValuesInside().equals(parameter.type())) {
                        throw new ConstructorEvaluationException(this, "An argument you passed has type %s, but must have type %s".formatted(quoted(argument.getEntity().getType().getName()), quoted(parameters.getParameter(argument.getName()).type())));
                    }
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

        var result = invoke(arguments);

        if(result == null) {
            throw new QuarkException("Result of " + ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER + name + " is null. Provided arguments: " + Lists.join(arguments.getArgumentsAsList().stream().map(argument -> argument.getName() + " = " + argument.getEntity().toInstructionForm()).toList()));
        }

        if(result.mismatchesType(returnDescription.getType())) {
            if(returnDescription.getType().castableFrom(result.getType())) {
                result = returnDescription.getType().cast(result);
            } else {
                throw new ConstructorEvaluationException(this, "Constructor %s must return an entity with type %s or an entity that can be casted to this type, but it returned an entity %s with type %s.".formatted(
                        name,
                        returnDescription.getType().getName(),
                        result.toInstructionForm(),
                        result.getExactTypeName()
                ));
            }
        }

        return result;
    }

    public InstructionEntityConstructorReturnDescription getReturnDescription() {
        return returnDescription;
    }

    public String getSyntax() {
        TextBuffer syntax = new TextBuffer();

        syntax.append(ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER);
        syntax.append(name);
        syntax.append('(');
        syntax.nextLine();

        for(int index = 0; index < parameters.asList().size(); index++) {
            var parameter = parameters.parameterAt(index);
            syntax.append("\t");

            syntax.append(parameter.name() + " is " + (parameter.required() ? "" : "optional " ) + parameter.type() + (parameter.isVarargs() ? InstructionEntityConstructorParameter.VARARGS_TYPE_MARKER : ""));

            if(index + 1 != parameters.asList().size()) {
                syntax.append(',');
            }

            syntax.nextLine();
        }

        syntax.append(')');

        syntax.append(" -> ");
        syntax.append(returnDescription.getDescription());
        syntax.append(" is ");
        syntax.append(returnDescription.getType().getName());

        return syntax.extractContent();
    }
}
