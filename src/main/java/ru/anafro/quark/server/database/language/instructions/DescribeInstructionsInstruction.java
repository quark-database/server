package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.security.TokenPermission;

public class DescribeInstructionsInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public DescribeInstructionsInstruction() {
        super(
                "_describe instructions",

                "Describes all the instructions. Don't use.",

                TokenPermission.ALLOWED_FOR_ALL_TOKENS
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        result.header("instruction name", "description", "permission", "parameter name", "is general", "is required", "parameter type");

        for (var instruction : Quark.instructions()) {
            for (var parameter : instruction.getParameters()) {
                result.row(
                        instruction.getName(),
                        instruction.getDescription(),
                        instruction.getPermission(),
                        parameter.getName(),
                        Boolean.toString(parameter.isGeneral()),
                        Boolean.toString(parameter.isRequired()),
                        parameter.getType()
                );
            }
        }

        result.ok("All the instructions have been described.");
    }
}
