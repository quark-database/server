package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.TableRecordSelector;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.exceptions.Exceptions;

public class SelectFromInstruction extends Instruction {
    public SelectFromInstruction() {
        super("select from", "data.select",

                InstructionParameter.general("table name"),

                InstructionParameter.required("selector", "selector"),
                InstructionParameter.optional("skip", "int"),
                InstructionParameter.optional("limit", "int")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var skip = arguments.has("skip") ? arguments.getInteger("skip") : 0;
        var limit = arguments.has("limit") ? arguments.getInteger("limit") : Integer.MAX_VALUE;
        var selector = arguments.get("selector").valueAs(TableRecordSelector.class);

        try {
            var table = Table.byName(arguments.getString("table name"));
            var selectedRecords = table.select(selector, skip, limit);

            result.header(table.createTableViewHeader());

            for(var selectedRecord : selectedRecords) {
                result.appendRow(selectedRecord.toTableViewRow());
            }

            result.status(InstructionExecutionStatus.OK, "%d rows successfully selected.".formatted(selectedRecords.size()));
        } catch(QuarkException exception) {
            result.status(InstructionExecutionStatus.SERVER_ERROR, "?" + Exceptions.getTraceAsString(exception));
        }
    }
}
