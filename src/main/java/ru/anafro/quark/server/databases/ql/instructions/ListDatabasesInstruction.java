package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.Database;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionExecutionStatus;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.databases.views.TableViewHeader;
import ru.anafro.quark.server.databases.views.TableViewRow;
import ru.anafro.quark.server.networking.Server;

public class ListDatabasesInstruction extends Instruction {
    public ListDatabasesInstruction() {
        super("list databases", "databases.list");
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        result.header(new TableViewHeader("database name"));

        for(var database : Database.all()) {
            result.appendRow(new TableViewRow(database.getName()));
        }

        result.status(InstructionExecutionStatus.OK, "Database names are loaded.");
    }
}
