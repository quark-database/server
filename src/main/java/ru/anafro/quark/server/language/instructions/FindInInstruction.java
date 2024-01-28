package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.database.data.exceptions.ColumnNotFoundException;
import ru.anafro.quark.server.database.data.structures.HashtableRecordCollection;
import ru.anafro.quark.server.database.data.structures.PageTreeRecordCollection;
import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;

import static ru.anafro.quark.server.language.InstructionParameter.general;
import static ru.anafro.quark.server.language.InstructionParameter.required;

public class FindInInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public FindInInstruction() {
        super(
                "find in",
                "Quickly finds a record by a single field",
                "table.find",
                general("table"),
                required("finder", "finder")
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var finder = arguments.getFinder("finder");
        var table = arguments.getTable("table");
        var columnName = finder.columnName();
        var column = table.getColumn(columnName).orElseThrow(() -> new ColumnNotFoundException(table, columnName));
        var records = column.hasModifier("unique") ?
                new HashtableRecordCollection(finder.columnName()) :
                new PageTreeRecordCollection(finder.columnName());

        records.addAll(table);
        table.getRecords().forEach(records::add);

        records.find(finder).ifPresentOrElse(record -> {
            result.header(table.createViewHeader());
            result.row(record);
            result.ok("A row has been found.");
        }, () -> result.ok("A row hasn't been found."));
    }
}
