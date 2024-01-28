package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;

import static ru.anafro.quark.server.language.InstructionParameter.general;
import static ru.anafro.quark.server.language.InstructionParameter.required;

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
                general("table"),
                required("column", "str"),
                required("modifier", "modifier")
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var table = arguments.getTable("table");
        var modifier = arguments.getModifier("modifier");
        var column = arguments.getColumn(table, "column");

        column.addModifier(modifier);
        table.saveHeader();

        result.ok("A modifier has been added.");
    }
}
