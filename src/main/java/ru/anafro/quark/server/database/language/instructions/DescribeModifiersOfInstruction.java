package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;

import static ru.anafro.quark.server.database.language.InstructionParameter.general;

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
                general("table")
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var table = arguments.getTable("table");

        result.header("column name", "modifier name");

        for (var column : table.getColumns()) {
            for (var entity : column.modifiers()) {
                var columnName = column.name();
                var modifierName = entity.getModifier().getName();

                result.row(columnName, modifierName);
            }
        }

        result.ok("All the modifiers are described.");
    }
}
