package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.security.TokenPermission;

public class DescribeConstructorsInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public DescribeConstructorsInstruction() {
        super(
                "_describe constructors",

                "Describes all the constructors. Don't use.",

                TokenPermission.ALLOWED_FOR_ALL_TOKENS
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        result.header(
                "constructor name",
                "return description",
                "return type",
                "parameter name",
                "parameter type",
                "is varargs",
                "is required",
                "is wildcard"
        );

        for (var constructor : Quark.constructors()) {
            for (var parameter : constructor.getParameters()) {
                result.row(
                        constructor.getName(),
                        constructor.getReturnDescription().getDescription(),
                        constructor.getReturnDescription().getType().getName(),
                        parameter.name(),
                        parameter.type(),
                        Boolean.toString(parameter.isVarargs()),
                        Boolean.toString(parameter.isRequired()),
                        Boolean.toString(parameter.isWildcard())
                );
            }
        }

        result.ok("All the constructors have been described.");
    }
}
