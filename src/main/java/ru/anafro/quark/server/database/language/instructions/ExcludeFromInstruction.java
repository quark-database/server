package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.data.exceptions.ColumnNotFoundException;
import ru.anafro.quark.server.database.data.structures.HashtableRecordCollection;
import ru.anafro.quark.server.database.data.structures.PageTreeRecordCollection;
import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;

import static ru.anafro.quark.server.database.language.InstructionParameter.general;
import static ru.anafro.quark.server.database.language.InstructionParameter.required;

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
        var columnName = finder.columnName();
        var column = table.getColumn(columnName).orElseThrow(() -> new ColumnNotFoundException(table, columnName));
        var records = column.hasModifier("unique") ? new HashtableRecordCollection(columnName) : new PageTreeRecordCollection(columnName);

        records.addAll(table);
        records.exclude(finder);
        table.store(records);

        result.ok("Exclusion has been performed.");
    }
}
