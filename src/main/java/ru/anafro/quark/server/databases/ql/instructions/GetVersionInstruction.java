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

public class GetVersionInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public GetVersionInstruction() {
        super(
                "get version",
                "Returns Quark's version",
                TokenPermission.ALLOWED_FOR_ALL_TOKENS
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        result.header(new TableViewHeader("version"));
        result.appendRow(new TableViewRow(Quark.version().toString()));
        result.status(QueryExecutionStatus.OK, "Quark's version has been returned.");
    }
}
