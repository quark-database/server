package ru.anafro.quark.server.database.language.entities;

import ru.anafro.quark.server.database.language.entities.exceptions.ConstructorEvaluationException;
import ru.anafro.quark.server.database.language.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.database.language.types.EntityType;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ru.anafro.quark.server.utils.strings.English.pluralize;
import static ru.anafro.quark.server.utils.strings.English.withArticle;

public abstract class EntityConstructor {
    private final String name;
    private final InstructionEntityConstructorParameters parameters;
    private final InstructionEntityConstructorReturnDescription returnDescription;

    public EntityConstructor(String name, InstructionEntityConstructorReturnDescription returnDescription, InstructionEntityConstructorParameter... parameters) {
        this.name = name;
        this.returnDescription = returnDescription;
        this.parameters = new InstructionEntityConstructorParameters(parameters);
    }

    public static String convertCamelCaseToConstructorCase(String camelCase) {
        var buffer = new TextBuffer();

        for (char character : camelCase.toCharArray()) {
            if (Character.isUpperCase(character)) {
                buffer.append(' ');
            }

            buffer.append(Character.toLowerCase(character));
        }

        return buffer.getContent();
    }

    public String getName() {
        return name;
    }

    public InstructionEntityConstructorParameters getParameters() {
        return parameters;
    }

    protected abstract Entity invoke(InstructionEntityConstructorArguments arguments);

    public Entity eval(Object... arguments) {
        return eval(InstructionEntityConstructorArguments.positional(this, arguments));
    }

    public Entity eval(InstructionEntityConstructorArguments arguments) {
        if (parameters.hasVarargs() && arguments.doesntHave(parameters.getVarargs().name())) {
            var varargsName = parameters.getVarargs().name();
            var varargsType = parameters.getVarargs().type();

            arguments.add(varargsName, ListEntity.empty(varargsType));
        }

        for (var argument : arguments) {
            var types = Quark.types();
            var argumentName = argument.getName();
            var argumentEntity = argument.getEntity();
            var argumentType = argument.getEntity().getType();
            var argumentTypeName = argument.getEntity().getTypeName();

            if (!parameters.has(argumentName)) {
                throw new ConstructorEvaluationException(this, STR."No such parameter \{argumentName}.");
            }

            if (argumentEntity == null) {
                throw new ConstructorEvaluationException(this, STR."The argument \{argumentName} must not be null.");
            }

            var parameter = parameters.get(argumentName);
            var parameterTypeName = parameter.type();

            if (parameter.isWildcard() || argumentTypeName.equals(parameterTypeName)) {
                continue;
            }

            if (types.doesntHave(parameterTypeName)) {
                throw new ConstructorEvaluationException(this, STR."The parameter type \{parameterTypeName} is not present.");
            }

            var parameterType = types.get(parameterTypeName);

            if (argumentType.canBeCastedTo(parameterType)) {
                var castedArgument = parameterType.cast(argumentEntity);
                argument.setEntity(castedArgument);
            }

            if (parameter.isVarargs() && argument.hasType("list")) {
                var varargs = argument.asList();
                var varargsType = varargs.getTypeNameOfElements();

                if (varargs.canNotBeMappedToType(parameterType)) {
                    throw new ConstructorEvaluationException(this, STR."Varargs of type \{parameterTypeName} cannot contain \{pluralize(varargsType)}.");
                }

                argument.setEntity(varargs.mapToType(parameterType));
            }
        }

        for (var parameter : parameters) {
            var parameterName = parameter.name();

            if (parameter.isRequired() && arguments.doesntHave(parameterName)) {
                throw new ConstructorEvaluationException(this, STR."The required parameter \{parameterName} is missing.");
            }
        }

        var constructorResult = invoke(arguments);
        var returnType = returnDescription.getType();

        if (constructorResult == null) {
            throw new QuarkException(STR."Result of the constructor \{name} must not be null. The arguments are \{arguments}.");
        }

        if (constructorResult.doesntHaveType(returnType) && returnType.canNotCast(constructorResult)) {
            var constructorResultType = constructorResult.getExactTypeName();
            var returnTypeName = withArticle(returnType.getName());
            throw new ConstructorEvaluationException(this, STR."Constructor \{name} must return \{returnTypeName}, but it returned (\{constructorResultType}) \{constructorResult}.");
        }

        return returnType.cast(constructorResult);
    }

    public InstructionEntityConstructorReturnDescription getReturnDescription() {
        return returnDescription;
    }

    public boolean hasReturnType(String typeName) {
        return getReturnType().getName().equals(typeName);
    }

    public boolean doesntHaveReturnType(String typeName) {
        return !hasReturnType(typeName);
    }

    public EntityType<?> getReturnType() {
        return returnDescription.getType();
    }

    public String getSyntax() {
        TextBuffer syntax = new TextBuffer();

        syntax.appendIf(returnDescription.isNullable(), "<blue>nullable </>");
        syntax.append(STR."<blue>\{returnDescription.getType().getName()}</> ");
        syntax.append(STR."<yellow>\{ConstructorNameInstructionToken.CONSTRUCTOR_PREFIX}\{name}</>");

        if (parameters.count() != 0) {
            syntax.append('(');
            for (int index = 0; index < parameters.asList().size(); index++) {
                var parameter = parameters.parameterAt(index);
                syntax.append(STR."\{parameter.isRequired() ? "" : "<blue>optional</> "}<blue>\{parameter.type()}</><blue>\{parameter.isVarargs() ? " varargs" : ""}</>: <grayish>\{parameter.name()}</>");

                if (index + 1 != parameters.asList().size()) {
                    syntax.append(", ");
                }

            }
            syntax.append(')');
        }

        return syntax.extractContent();
    }

    @Override
    public String toString() {
        return this.getSyntax();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Meta {
        String name();
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Optional {
        boolean set() default true;
    }
}
