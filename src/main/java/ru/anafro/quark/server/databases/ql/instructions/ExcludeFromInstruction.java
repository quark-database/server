package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.data.structures.HashtableRecordCollection;
import ru.anafro.quark.server.databases.data.structures.PageTreeRecordCollection;
import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.FinderEntity;
import ru.anafro.quark.server.networking.Server;

public class ExcludeFromInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ExcludeFromInstruction() {
        super(
                "exclude from",
                "Excludes the records matches the finder",
                "table.exclude",

                InstructionParameter.general("table"),
                InstructionParameter.required("finder", "finder")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var tableName = arguments.getString("table");
        var finder = arguments.<FinderEntity>get("finder").getValue();

        if(!Table.exists(tableName)) {
            throw new TableNotFoundException(new CompoundedTableName(tableName));
        }

        var table = Table.byName(tableName);

        if(table.getHeader().missingColumn(finder.getColumnName())) {
            throw new QueryException("There's no column named %s.".formatted(finder.getColumnName()));
        }

        var records = table.getHeader().getModifiers().stream().anyMatch(modifier -> modifier.getModifier().getName().equals("require unique") && modifier.getColumnName().equals(finder.getColumnName())) ?
                new HashtableRecordCollection(finder.getColumnName()) :
                new PageTreeRecordCollection(finder.getColumnName());

        table.getRecords().forEach(record -> {
            records.add(record);
        });

        records.exclude(finder);

        table.getRecords().save(records);

        result.status(QueryExecutionStatus.OK, "Exclusion has been performed.");
    }
}
