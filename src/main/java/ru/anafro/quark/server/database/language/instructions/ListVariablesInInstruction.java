package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;

import static ru.anafro.quark.server.database.language.InstructionParameter.general;

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
                general("table")
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var tableName = arguments.getString("table");
        var table = Table.byName(tableName);

        result.header("variable name", "variable value", "variable type");

        for (var variable : table.getVariableDirectory()) {
            variable.get().ifPresent(value -> {
                var variableName = variable.getName();
                var variableCode = value.toInstructionForm();
                var variableType = value.getExactTypeName();

                result.row(variableName, variableCode, variableType);
            });
        }

        result.ok("All the variables in the table have been listed.");
    }
}