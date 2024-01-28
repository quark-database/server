package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.database.data.ExpressionTableRecordSelector;
import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;
import ru.anafro.quark.server.utils.integers.Counter;

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
        var table = arguments.getTable("table");
        var selector = arguments.tryGetSelector("selector").orElse(ExpressionTableRecordSelector.SELECT_ALL);
        var count = new Counter();

        table.getRecords().forEach(record -> count.incrementIf(selector.selects(record)));

        result.header("count");
        result.row(String.valueOf(count.getCount()));
        result.ok("Records have been counted.");
    }
}
