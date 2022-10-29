package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.views.TableViewHeader;
import ru.anafro.quark.server.databases.views.TableViewRow;
import ru.anafro.quark.server.networking.Server;

public class ListVariablesInInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ListVariablesInInstruction() {
        super(
                "list variables in",
                "Shows all the variables inside the table",
                "table.variable.list",
                InstructionParameter.general("table")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var tableName = arguments.getString("table");

        if(!Table.exists(tableName)) {
            throw new TableNotFoundException(new CompoundedTableName(tableName));
        }

        var table = Table.byName(tableName);

        result.header(new TableViewHeader("variable name", "variable value", "variable type"));

        for(var variable : table.getVariableFolder()) {
            var value = variable.get();
            result.appendRow(new TableViewRow(variable.getName(), value.toInstructionForm(), value.getExactTypeName()));
        }

        result.status(QueryExecutionStatus.OK, "All the variables in the table have been listed.");
    }
}
