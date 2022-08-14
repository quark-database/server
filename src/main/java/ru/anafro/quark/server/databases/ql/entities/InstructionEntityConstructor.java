package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.ql.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.strings.StringBuffer;

import java.util.stream.Collectors;

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

    public abstract InstructionEntity eval(InstructionEntityConstructorArguments arguments);

    public String getSyntax() {
        StringBuffer syntax = new StringBuffer();

        syntax.append(ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER);
        syntax.append('(');

        syntax.append(Lists.join(parameters.asList().stream().map(parameter -> parameter.name() + " is " + parameter.type() + (parameter.required() ? "" : " optional" )).collect(Collectors.toList()), ", "));

        syntax.append(')');

        return syntax.extractValue();
    }
}
