package ru.anafro.quark.server.database.data.schemes;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.TableName;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.strings.Strings;

import java.util.List;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class TableScheme {
    private final TableName tableName;
    private final List<ColumnDescription> columns;

    public TableScheme(ColumnDescription... columns) {
        var tableName = Strings.convertPascalCaseToCapitalizedCase(Strings.removeTrailing(getClass().getSimpleName(), TableScheme.class.getSimpleName()));

        this.tableName = new TableName(Quark.NAME, tableName);
        this.columns = list(columns);
    }


    public void up() {
        if (Table.exists(tableName)) {
            return;
        }

        Quark.info(STR."Creating a missing table '\{tableName}'...");
        Table.create(tableName, columns);
    }

    public void down() {
        Table.deleteIfExists(tableName);
    }

    public TableName getTableName() {
        return tableName;
    }
}
