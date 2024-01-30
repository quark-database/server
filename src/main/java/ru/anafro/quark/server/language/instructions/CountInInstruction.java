package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.database.data.ExpressionTableRecordSelector;
import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;

import static ru.anafro.quark.server.language.InstructionParameter.general;
import static ru.anafro.quark.server.language.InstructionParameter.optional;

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
                general("table"),
                optional("selector", "selector")
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var table = arguments.getTable();
        var selector = arguments.tryGetSelector().orElse(ExpressionTableRecordSelector.SELECT_ALL);

        result.header("count");
        result.row(table.count(selector));

        result.ok("The records have been counted.");
    }
}
