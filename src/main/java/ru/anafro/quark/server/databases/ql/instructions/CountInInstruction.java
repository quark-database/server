package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.ExpressionTableRecordSelector;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.SelectorEntity;
import ru.anafro.quark.server.databases.views.TableViewHeader;
import ru.anafro.quark.server.databases.views.TableViewRow;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.integers.Counter;

public class CountInInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public CountInInstruction() {
        super(
                "count in",
                "Counts records matching condition",
                "table.count in",
                InstructionParameter.general("table"),
                InstructionParameter.optional("selector", "selector")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var tableName = arguments.getString("table");
        var selector = arguments.has("selector") ? arguments.<SelectorEntity>get("selector").getSelector() : ExpressionTableRecordSelector.SELECT_ALL;

        if(!Table.exists(tableName)) {
            throw new TableNotFoundException(new CompoundedTableName(tableName));
        }

        var table = Table.byName(tableName);
        var count = new Counter();

        table.getRecords().forEach(record -> count.countIf(selector.shouldBeSelected(record)));

        result.header(new TableViewHeader("count"));
        result.appendRow(new TableViewRow(String.valueOf(count.getCount())));
        result.status(QueryExecutionStatus.OK, "Records have been counted.");
    }
}
