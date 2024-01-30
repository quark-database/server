package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;

import static ru.anafro.quark.server.language.InstructionParameter.general;
import static ru.anafro.quark.server.language.InstructionParameter.required;

public class ExcludeFromInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ExcludeFromInstruction() {
        super(
                "exclude from",
                "Excludes the records matches the finder",
                "table.exclude",

                general("table"),
                required("finder", "finder")
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var table = arguments.getTable("table");
        var finder = arguments.getFinder("finder");

        table.exclude(finder);
        result.ok("Exclusion has been performed.");
    }
}
