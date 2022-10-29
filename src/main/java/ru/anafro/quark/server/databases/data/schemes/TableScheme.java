package ru.anafro.quark.server.databases.data.schemes;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.data.Database;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.ql.InstructionTemplate;
import ru.anafro.quark.server.databases.ql.entities.ListEntity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.files.Databases;
import ru.anafro.quark.server.files.FileSystem;

import java.nio.file.Path;

public class TableScheme {
    private final CompoundedTableName tableName;
    private ListEntity columns;
    private ListEntity modifiers;

    public TableScheme(CompoundedTableName tableName, ListEntity columns, ListEntity modifiers) {
        this.tableName = tableName;
        this.columns = columns;
        this.modifiers = modifiers;
    }


    public TableScheme(String tableName, ListEntity columns, ListEntity modifiers) {
        this(new CompoundedTableName(tableName), columns, modifiers);
    }

    public void deploy() {
        if(!Database.exists(tableName.getDatabaseName())) {
            Database.create(tableName.getDatabaseName());
        }

        FileSystem.deleteIfExists(Path.of(Databases.FOLDER, tableName.getDatabaseName(), tableName.getTableName()).toString());

        Quark.info("System table '%s' is missing, creating a new one...".formatted(
                tableName
        ));

        Quark.runInstruction(new InstructionTemplate("""
                create table %s: columns = %s, modifiers = %s;
        """).format(
                new StringEntity(tableName.toCompoundedString()),
                columns,
                modifiers
        ));
    }

    public void rollback() {
        if(!Table.exists(tableName)) {
            return;
        }

        Table.byName(tableName).delete();
    }

    public CompoundedTableName getTableName() {
        return tableName;
    }

    public ListEntity getColumns() {
        return columns;
    }

    public ListEntity getModifiers() {
        return modifiers;
    }
}
