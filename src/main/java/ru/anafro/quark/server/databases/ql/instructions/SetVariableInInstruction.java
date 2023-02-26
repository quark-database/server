package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.data.exceptions.VariableNotFoundException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.networking.Server;

public class SetVariableInInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public SetVariableInInstruction() {
        super(
                "set variable in",
                "Sets a new value for a variable in the table",
                "table.variable.set",
                InstructionParameter.general("table"),
                InstructionParameter.required("name"),
                InstructionParameter.required("value", "?")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var tableName = arguments.getString("table");
        var variableName = arguments.getString("name");

        if(!Table.exists(tableName)) {
            throw new TableNotFoundException(new CompoundedTableName(tableName));
        }

        var table = Table.byName(tableName);

        if(table.getVariableFolder().missingVariable(variableName)) {
            throw new VariableNotFoundException(table, variableName);
        }

        var variable = table.getVariableFolder().getVariable(variableName);
        variable.set(arguments.get("value"));

        result.status(QueryExecutionStatus.OK, "A new variable value has been set.");
    }
}