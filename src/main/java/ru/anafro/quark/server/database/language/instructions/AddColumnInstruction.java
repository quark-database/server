package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.data.RecordField;
import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.database.exceptions.QueryException;
import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionParameter;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;
import ru.anafro.quark.server.database.language.exceptions.GeneratedValueMismatchesColumnTypeException;

/**
 * This class represents the add column instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("add column"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("add column").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class AddColumnInstruction extends Instruction {

    /**
     * Creates a new instance of the add column instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("add column");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("add column").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public AddColumnInstruction() {
        super("add column",

                "Adds a new column to a table",

                "column.add",

                InstructionParameter.general("definition", "column"),

                InstructionParameter.required("table"),
                InstructionParameter.optional("generator", "generator")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("add column").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var table = arguments.getTable("table");
        var columnDescription = arguments.getColumnDescription("definition");
        var columnName = columnDescription.name();
        var columnType = columnDescription.type();

        if (table.hasColumn(columnName)) {
            throw new DatabaseException(STR."A column with name '\{columnName}' already exists.");
        }

        table.getHeader().addColumn(columnDescription);

        var records = table.selectAll();
        columnDescription.tryGetGeneratingModifier().ifPresentOrElse(modifierEntity -> {
            var modifier = modifierEntity.getModifier();
            var modifierArguments = modifierEntity.getModifierArguments();

            for (var record : records) {
                var generatedField = RecordField.empty(columnName);

                modifier.prepareField(table, generatedField, modifierArguments);
                record.add(generatedField);
            }
        }, () -> {
            var generator = arguments.tryGetGenerator("generator").orElseThrow(() -> new QueryException("Add column needs a generator or a generating modifier."));

            for (var record : records) {
                var generatedEntity = generator.apply(record);

                if (columnType.canCast(generatedEntity)) {
                    generatedEntity = generatedEntity.castTo(columnType);
                }

                if (generatedEntity.doesntHaveType(columnType)) {
                    throw new GeneratedValueMismatchesColumnTypeException(generator, columnDescription, generatedEntity);
                }

                record.add(new RecordField(columnName, generatedEntity));
            }
        });

        table.store(records);
        table.saveHeader();

        result.ok("A column was added successfully.");
    }
}
