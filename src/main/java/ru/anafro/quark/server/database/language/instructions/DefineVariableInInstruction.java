package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.TableName;
import ru.anafro.quark.server.database.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.database.data.exceptions.VariableAlreadyExistsException;
import ru.anafro.quark.server.database.language.*;

public class DefineVariableInInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public DefineVariableInInstruction() {
        super(
                "define variable in",
                "Defines a new variable in the table",
                "table.variable.define",
                InstructionParameter.general("table"),
                InstructionParameter.required("name"),
                InstructionParameter.required("value", "?")
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var tableName = arguments.getString("table");
        var variableName = arguments.getString("name");

        if (!Table.exists(tableName)) {
            throw new TableNotFoundException(new TableName(tableName));
        }

        var table = Table.byName(tableName);

        if (table.getVariableDirectory().hasVariable(variableName)) {
            throw new VariableAlreadyExistsException(table, variableName);
        }

        var variable = table.getVariableDirectory().getVariable(variableName);
        variable.set(arguments.get("value"));

        result.ok("A new variable value has been set.");
    }
}
