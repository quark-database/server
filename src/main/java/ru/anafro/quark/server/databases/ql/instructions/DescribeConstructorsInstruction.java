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
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        result.header(new TableViewHeader(
                "constructor name",
                "return description",
                "return type",
                "parameter name",
                "parameter type",
                "is varargs",
                "is required",
                "is wildcard"
        ));

        for(var constructor : Quark.constructors()) {
            for(var parameter : constructor.getParameters()) {
                result.appendRow(new TableViewRow(
                        constructor.getName(),
                        constructor.getReturnDescription().getDescription(),
                        constructor.getReturnDescription().getType().getName(),
                        parameter.name(),
                        parameter.type(),
                        Boolean.toString(parameter.isVarargs()),
                        Boolean.toString(parameter.required()),
                        Boolean.toString(parameter.isWildcard())
                ));
            }
        }

        result.status(QueryExecutionStatus.OK, "All the constructors have been described.");
    }
}
