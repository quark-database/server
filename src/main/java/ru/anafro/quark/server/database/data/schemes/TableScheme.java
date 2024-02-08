package ru.anafro.quark.server.database.data.schemes;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.TableName;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.strings.Strings;

import java.util.List;

import static ru.anafro.quark.server.database.data.Database.systemDatabase;
import static ru.anafro.quark.server.database.data.Table.systemTable;
import static ru.anafro.quark.server.utils.collections.Collections.list;

public class TableScheme {
    private final String tableName;
    private final List<ColumnDescription> columns;

    public TableScheme(ColumnDescription... columns) {
        this.tableName = Strings.convertPascalCaseToCapitalizedCase(Strings.removeTrailing(getClass().getSimpleName(), TableScheme.class.getSimpleName()));
        this.columns = list(columns);
    }


    public void up() {
        if (systemDatabase().hasTable(tableName)) {
            return;
        }

        Quark.info(STR."Creating a missing table '\{tableName}'...");
        Table.create(new TableName(systemDatabase().getName(), tableName), columns);
    }

    public void down() {
        systemTable(tableName).delete();
    }

    public String getTableName() {
        return tableName;
    }
}
