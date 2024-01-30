package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;

import static ru.anafro.quark.server.language.InstructionParameter.general;
import static ru.anafro.quark.server.language.InstructionParameter.required;

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
                general("table"),
                required("name"),
                required("value", "?")
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var table = arguments.getTable();
        var name = arguments.getString("name");
        var value = arguments.get("value");

        table.setVariable(name, value);
        result.ok("The variable value has been set.");
    }
}
