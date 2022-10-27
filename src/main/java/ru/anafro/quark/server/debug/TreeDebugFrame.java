package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.structures.PageTreeRecordCollection;
import ru.anafro.quark.server.debug.components.DebugFrame;
import ru.anafro.quark.server.debug.components.TextArea;
import ru.anafro.quark.server.debug.components.TextField;
import ru.anafro.quark.server.utils.exceptions.Exceptions;

import javax.swing.*;
import java.awt.*;

public class TreeDebugFrame extends DebugFrame {
    private TextArea treeOutputArea;
    private TextField tableInputField;
    private TextField columnNameInputField;
    private TextField valueInputField;

    public TreeDebugFrame() {
        super("BTree", "tree", 900, 700);
    }

    @Override
    protected void constructInterface() {
        treeOutputArea = TextArea.console(0, 0, 900, 640);
        treeOutputArea.setEditable(false);
        treeOutputArea.setLineWrap(true);

        tableInputField = TextField.console(0, 640, 900, 20, this::updateHashtableOutput);
        columnNameInputField = TextField.console(0, 660, 900, 20, this::updateHashtableOutput);
        valueInputField = TextField.console(0, 680, 900, 20, this::updateHashtableOutput);

        JScrollPane scrollHashtableOutputArea = new JScrollPane(treeOutputArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollHashtableOutputArea.setBounds(0, 0, 900, 640);

        add(scrollHashtableOutputArea);
        add(tableInputField);
        add(columnNameInputField);
        add(valueInputField);
    }

    protected void updateHashtableOutput() {
        var tableName = tableInputField.getText();
        var columnName = columnNameInputField.getText();

        try {
            var table = Table.byName(tableName);
            var tree = new PageTreeRecordCollection(columnName);

            table.getRecords().forEach(tree::add);

            treeOutputArea.setText(tree.getTree().toString());
            treeOutputArea.setForeground(Color.BLACK);
        } catch(Exception exception) {
            treeOutputArea.setText(Exceptions.getTraceAsString(exception));
            treeOutputArea.setForeground(Color.RED);
        }
    }
}
