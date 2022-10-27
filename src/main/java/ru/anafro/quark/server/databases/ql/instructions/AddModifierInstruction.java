package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.ColumnModifierEntity;
import ru.anafro.quark.server.networking.Server;

public class AddModifierInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public AddModifierInstruction() {
        super(
                "add modifier to",
                "Adds a new table modifier",
                "table.add modifier",
                InstructionParameter.general("table"),
                InstructionParameter.required("modifier", "modifier")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var modifier = arguments.<ColumnModifierEntity>get("modifier");
        var tableName = arguments.getString("table");

        if(!Table.exists(tableName)) {
            throw new TableNotFoundException(new CompoundedTableName(tableName));
        }

        var table = Table.byName(tableName);

        table.getHeader().getModifiers().add(modifier);
        table.getHeader().save();

        result.status(QueryExecutionStatus.OK, "A modifier has been added.");
    }
}
