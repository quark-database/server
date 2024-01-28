package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.ArrayList;
import java.util.Collections;

import static ru.anafro.quark.server.language.InstructionParameter.general;
import static ru.anafro.quark.server.language.InstructionParameter.required;

/**
 * This class represents the swap columns in instruction of Quark QL.
 * <br><br>
 * <p>
 * Note that you should not create instances of this class
 * by your own. Instead, use {@code Quark.instructions().get("swap columns in"); }
 * to get an instance of this class.
 *
 * <br><br>
 * <p>
 * You can check out the syntax of this instruction by running
 * <pre>
 * {@code
 * Quark.instructions().get("swap columns in").getSyntax();
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class SwapColumnsInstruction extends Instruction {

    /**
     * Creates a new instance of the swap columns in instruction
     * representing object.
     * <br><br>
     * <p>
     * Note that you should not create instances of this class
     * by your own. Instead, use Quark.instructions().get("swap columns in");
     * to get an instance of this class.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("swap columns in").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public SwapColumnsInstruction() {
        super(
                "swap columns in",

                "Swaps two columns in a table",

                "columns.swap",

                general("table"),

                required("first"),
                required("second")
        );
    }

    /**
     * Runs the action of this instruction with passed in arguments.
     * <br><br>
     * <p>
     * You can check out the syntax of this instruction by running
     * <pre>
     * {@code
     * Quark.instructions().get("swap columns in").getSyntax();
     * }
     * </pre>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var table = arguments.getTable("table");
        var firstColumnName = arguments.getColumn(table, "first").name();
        var secondColumnName = arguments.getColumn(table, "second").name();
        var newColumnOrder = new ArrayList<>(table.getHeader().getColumns());
        var firstIndex = Lists.indexOfKey(newColumnOrder, firstColumnName, ColumnDescription::name);
        var secondIndex = Lists.indexOfKey(newColumnOrder, secondColumnName, ColumnDescription::name);

        Collections.swap(newColumnOrder, firstIndex, secondIndex);

        table.getHeader().setColumns(newColumnOrder);
        table.saveHeader();

        result.ok("Successfully swapped two columns in the table.");
    }
}
