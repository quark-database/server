package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.RecordField;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.databases.data.structures.RecordCollectionResolver;
import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.entities.ColumnEntity;
import ru.anafro.quark.server.databases.ql.entities.GeneratorEntity;
import ru.anafro.quark.server.databases.ql.exceptions.GeneratedValueMismatchesColumnTypeException;
import ru.anafro.quark.server.networking.Server;

/**
 * This class represents the add column instruction of Quark QL.
 * <br><br>
 *
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("add column"); }
 * to get an instance of this class.
 *
 * <br><br>
 *
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("add column").getSyntax();
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class AddColumnInstruction extends Instruction {

    /**
     * Creates a new instance of the add column instruction
     * representing object.
     * <br><br>
     *
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("add column");
     * to get an instance of this class.
     * <br><br>
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("add column").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
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
     *
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("add column").getSyntax();
     * }
     * </pre>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var tableName = new CompoundedTableName(arguments.getString("table"));

        if(!Table.exists(tableName.toCompoundedString())) {
            throw new TableNotFoundException(tableName);
        }

        var table = Table.byName(tableName);
        var columnDescription = arguments.<ColumnEntity>get("definition").getColumnDescription();

        if(table.getHeader().hasColumn(columnDescription.getName())) {
            result.status(QueryExecutionStatus.SERVER_ERROR, "A column with name '%s' already exists. Did you suddenly run this instruction twice? Or tried to add a column to a wrong table? If not, you can choose different name.");
            return;
        }

        table.getHeader().addColumn(columnDescription);
        var records = table.loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.JUST_SELECT_EVERYTHING));

        if(columnDescription.getModifiers().stream().anyMatch(modifierEntity -> modifierEntity.getModifier().areValuesShouldBeGenerated())) {
            var generatingModifier = columnDescription.getModifiers().stream().filter(modifierEntity -> modifierEntity.getModifier().areValuesShouldBeGenerated()).findFirst().get();
            for(var record : records) {
                var addingField = new RecordField(columnDescription.getName(), null);
                generatingModifier.getModifier().beforeRecordInsertion(table, addingField, generatingModifier.getModifierArguments());
                record.addField(addingField);
            }
        } else if(arguments.has("generator")) {
            var generator = arguments.<GeneratorEntity>get("generator").getGenerator();

            for(var record : records) {
                var generatedFieldValue = generator.apply(record);

                if(columnDescription.getType().castableFrom(generatedFieldValue.getType())) {
                    generatedFieldValue = columnDescription.getType().cast(generatedFieldValue);
                }

                if(generatedFieldValue.mismatchesType(columnDescription.getType())) {
                    throw new GeneratedValueMismatchesColumnTypeException(generator, columnDescription, generatedFieldValue);
                }

                record.addField(new RecordField(columnDescription.getName(), generatedFieldValue));
            }
        } else {
            throw new QueryException("Add column needs a generator or a generating modifier.");
        }

        table.getRecords().save(records);
        table.getHeader().save();

        result.status(QueryExecutionStatus.OK, "A column was added successfully.");
    }
}
