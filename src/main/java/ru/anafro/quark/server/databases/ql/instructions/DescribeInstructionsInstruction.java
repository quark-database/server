package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.databases.ql.QueryExecutionStatus;
import ru.anafro.quark.server.databases.views.TableViewHeader;
import ru.anafro.quark.server.databases.views.TableViewRow;
import ru.anafro.quark.server.networking.Server;
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
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        result.header(new TableViewHeader("instruction name", "description", "permission", "parameter name", "is general", "is required", "parameter type"));

        for(var instruction : Quark.instructions()) {
            for(var parameter : instruction.getParameters()) {
                result.appendRow(new TableViewRow(
                        instruction.getName(),
                        instruction.getDescription(),
                        instruction.getPermission(),
                        parameter.getName(),
                        Boolean.toString(parameter.isGeneral()),
                        Boolean.toString(parameter.isRequired()),
                        parameter.getType()
                ));
            }
        }

        result.status(QueryExecutionStatus.OK, "All the instructions have been described.");
    }
}
