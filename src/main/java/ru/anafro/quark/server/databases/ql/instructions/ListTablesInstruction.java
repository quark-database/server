package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.Database;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.databases.views.TableViewHeader;
import ru.anafro.quark.server.databases.views.TableViewRow;
import ru.anafro.quark.server.networking.Server;

public class ListTablesInstruction extends Instruction {
    public ListTablesInstruction() {
        super("list tables in", "table.list",

                InstructionParameter.general("database")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        result.header(new TableViewHeader("table name"));

        for(var table : Database.byName(arguments.<StringEntity>get("database").getValue()).allTables()) {
            result.appendRow(new TableViewRow(table.getName()));
        }

        result.status(InstructionExecutionStatus.OK, "Table names are loaded.");
    }
}
