package ru.anafro.quark.server.databases.data.schemes;

import ru.anafro.quark.server.utils.patterns.NamedObjectsRegistry;

public class TableSchemeRegistry extends NamedObjectsRegistry<TableScheme> {

    @Override
    protected String getNameOf(TableScheme scheme) {
        return scheme.getTableName().toCompoundedString();
    }

    /**
     * Deploys all the schemes from the registry.
     *
     * @since   Quark 2.0
     * @author  Anatoly Frolov <contact@anafro.ru>
     */
    public void deploy() {
        for(var scheme : this) {
            scheme.deploy();
        }
    }
}
