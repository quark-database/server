package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.structures.HashtableRecordCollection;
import ru.anafro.quark.server.debug.components.Debugger;
import ru.anafro.quark.server.debug.components.TextArea;
import ru.anafro.quark.server.debug.components.TextField;
import ru.anafro.quark.server.language.Expressions;
import ru.anafro.quark.server.utils.exceptions.Exceptions;
import ru.anafro.quark.server.utils.integers.Integers;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import javax.swing.*;
import java.awt.*;

public class HashtableDebugger extends Debugger {
    private final TextArea hashtableOutputArea;
    private final TextField tableInputField;
    private final TextField columnNameInputField;
    private final TextField valueInputField;

    public HashtableDebugger() {
        super("Hashtable", "hashtable", 600, 700);

        hashtableOutputArea = TextArea.console(0, 0, 600, 640);
        hashtableOutputArea.setEditable(false);
        hashtableOutputArea.setLineWrap(true);

        tableInputField = TextField.console(0, 640, 600, 20, this::updateHashtableOutput);
        columnNameInputField = TextField.console(0, 660, 600, 20, this::updateHashtableOutput);
        valueInputField = TextField.console(0, 680, 600, 20, this::updateHashtableOutput);

        JScrollPane scrollHashtableOutputArea = new JScrollPane(hashtableOutputArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollHashtableOutputArea.setBounds(0, 0, 600, 640);

        add(scrollHashtableOutputArea);
        add(tableInputField);
        add(columnNameInputField);
        add(valueInputField);
    }

    protected void updateHashtableOutput() {
        var tableName = tableInputField.getText();
        var columnName = columnNameInputField.getText();
        var stringValue = valueInputField.getText();

        try {
            var table = Table.byName(tableName);
            var hashtable = new HashtableRecordCollection(columnName);
            var output = new TextBuffer();

            table.all().forEach(hashtable::add);

            var value = Expressions.eval(stringValue);
            var index = Integers.positiveModulus(value.hashCode(), hashtable.getRecordChains().length);

            output.appendLine(STR."Computed hash of \{value.toInstructionForm()}: \{index}");

            for (int i = 0; i < hashtable.getRecordChains().length; i++) {
                var chain = hashtable.getRecordChains()[i];
                output.append(STR."\{i}: ");

                if (chain.getRecords().isEmpty()) {
                    output.appendLine("<empty>");
                } else {
                    if (index == i) {
                        output.append("[FOUND] ");
                    }
                    for (var record : chain) {
                        output.append(STR."\{record.getField(columnName).getEntity().toInstructionForm()}, ");
                    }

                    output.nextLine();
                }
            }

            hashtableOutputArea.setText(output.extractContent());
            hashtableOutputArea.setForeground(Color.BLACK);
        } catch (Exception exception) {
            hashtableOutputArea.setText(Exceptions.getTrace(exception));
            hashtableOutputArea.setForeground(Color.RED);
        }
    }
}
