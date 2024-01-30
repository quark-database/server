package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.database.data.exceptions.VariableNotFoundException;
import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;

import static ru.anafro.quark.server.language.InstructionParameter.general;
import static ru.anafro.quark.server.language.InstructionParameter.required;

public class GetVariableInInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public GetVariableInInstruction() {
        super(
                "get variable in",
                "Shows the variable value of the table",
                "table.variable.get",

                general("table"),
                required("name")
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var name = arguments.getString("name");
        var table = arguments.getTable();
        var value = table.getVariable(name).get().orElseThrow(() -> new VariableNotFoundException(table, name));

        result.header("variable value", "variable type");
        result.row(value.toInstructionForm(), value.getExactTypeName());
        result.ok("The variable is returned.");
    }
}
