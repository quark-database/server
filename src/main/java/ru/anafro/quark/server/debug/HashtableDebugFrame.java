package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.structures.HashtableRecordCollection;
import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.debug.components.DebugFrame;
import ru.anafro.quark.server.debug.components.TextArea;
import ru.anafro.quark.server.debug.components.TextField;
import ru.anafro.quark.server.utils.exceptions.Exceptions;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import javax.swing.*;
import java.awt.*;

public class HashtableDebugFrame extends DebugFrame {
    private TextArea hashtableOutputArea;
    private TextField tableInputField;
    private TextField columnNameInputField;
    private TextField valueInputField;

    public HashtableDebugFrame() {
        super("Hashtable", "hashtable", 600, 700);
    }

    @Override
    protected void constructInterface() {
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
        var value = valueInputField.getText();

        try {
            var table = Table.byName(tableName);
            var hashtable = new HashtableRecordCollection(columnName);
            var output = new TextBuffer();

            table.getRecords().forEach(hashtable::add);

            var index = ConstructorEvaluator.eval(value).hashCode();

            for (int i = 0; i < hashtable.getRecordChains().length; i++) {
                var chain = hashtable.getRecordChains()[i];
                output.append(i + ": ");

                if (chain.getRecords().size() == 0) {
                    output.appendLine("<empty>");
                } else {
                    if(index == i) {
                        output.append("[FOUND] ");
                    }
                    for(var record : chain) {
                        output.append(record.getField(columnName).getValue().toInstructionForm() + ", ");
                    }

                    output.nextLine();
                }
            }

            hashtableOutputArea.setText(output.extractContent());
            hashtableOutputArea.setForeground(Color.BLACK);
        } catch(Exception exception) {
            hashtableOutputArea.setText(Exceptions.getTraceAsString(exception));
            hashtableOutputArea.setForeground(Color.RED);
        }
    }
}
