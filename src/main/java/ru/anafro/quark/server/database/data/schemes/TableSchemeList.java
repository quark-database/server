package ru.anafro.quark.server.database.data.schemes;

import ru.anafro.quark.server.utils.patterns.NamedObjectsList;

public class TableSchemeList extends NamedObjectsList<TableScheme> {

    @Override
    protected String getNameOf(TableScheme scheme) {
        return scheme.getTableName().toCompoundedString();
    }

    /**
     * Deploys all the schemes from the registry.
     *
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 2.0
     */
    public void up() {
        forEach(TableScheme::up);
    }

    public void down() {
        forEach(TableScheme::down);
    }
}
