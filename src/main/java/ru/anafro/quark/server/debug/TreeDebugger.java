package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.structures.PageTreeRecordCollection;
import ru.anafro.quark.server.debug.components.Debugger;
import ru.anafro.quark.server.debug.components.TextArea;
import ru.anafro.quark.server.debug.components.TextField;
import ru.anafro.quark.server.utils.exceptions.Exceptions;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class TreeDebugger extends Debugger {
    private final TextArea treeOutputArea;
    private final TextField tableInputField;
    private final TextField columnNameInputField;

    public TreeDebugger() {
        super("BTree", "tree", 900, 700);

        treeOutputArea = TextArea.console(0, 0, 900, 640);
        treeOutputArea.setEditable(false);
        treeOutputArea.setLineWrap(true);

        var valueInputField = TextField.console(0, 680, 900, 20, this::updateHashtableOutput);
        var scrollHashtableOutputArea = new JScrollPane(treeOutputArea, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
        this.tableInputField = TextField.console(0, 640, 900, 20, this::updateHashtableOutput);
        this.columnNameInputField = TextField.console(0, 660, 900, 20, this::updateHashtableOutput);

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

            table.all().forEach(tree::add);

            treeOutputArea.setText(tree.getTree().toString());
            treeOutputArea.setForeground(Color.BLACK);
        } catch (Exception exception) {
            treeOutputArea.setText(Exceptions.getTrace(exception));
            treeOutputArea.setForeground(Color.RED);
        }
    }
}
