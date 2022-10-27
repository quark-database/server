package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.views.TableViewHeader;
import ru.anafro.quark.server.databases.views.TableViewRow;
import ru.anafro.quark.server.networking.Server;

public class DescribeModifiersOfInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public DescribeModifiersOfInstruction() {
        super(
                "_describe modifiers of",
                "Shows all the modifiers of the table. Don't use",
                "table.describe modifiers",
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

        result.header(new TableViewHeader("column name", "modifier name"));

        for(var modifier : table.getHeader().getModifiers()) {
            result.appendRow(new TableViewRow(
                    modifier.getColumnName(),
                    modifier.getModifier().getName()
            ));
        }

        result.status(QueryExecutionStatus.OK, "All the modifiers are described.");
    }
}
