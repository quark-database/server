package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.databases.views.TableView;

public class TableViewCollectionFinished extends Event {
    private final TableView tableView;

    public TableViewCollectionFinished(TableView tableView) {
        this.tableView = tableView;
    }

    public TableView getTableView() {
        return tableView;
    }
}
