package ru.anafro.quark.server.databases.data.schemes;

import ru.anafro.quark.server.utils.patterns.NamedObjectsRegistry;

public class TableSchemeRegistry extends NamedObjectsRegistry<TableScheme> {

    @Override
    protected String getNameOf(TableScheme scheme) {
        return scheme.getTableName().toCompoundedString();
    }
}
