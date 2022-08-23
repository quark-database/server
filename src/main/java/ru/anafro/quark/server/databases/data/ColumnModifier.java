package ru.anafro.quark.server.databases.data;

import java.util.ArrayList;
import java.util.List;

public abstract class ColumnModifier {
    private final ColumnModifierApplicationPriority applicationPriority;
    private final ArrayList<String> allowedTypes;
    private final String name;

    public ColumnModifier(ColumnModifierApplicationPriority applicationPriority, String name, String... allowedTypes) {
        this.applicationPriority = applicationPriority;
        this.name = name;
        this.allowedTypes = new ArrayList<>(List.of(allowedTypes));
    }

    public ArrayList<String> getAllowedTypes() {
        return allowedTypes;
    }

    public String getName() {
        return name;
    }

    public ColumnModifierApplicationPriority getApplicationPriority() {
        return applicationPriority;
    }

    public abstract boolean areValuesOfColumnShouldBeGenerated();
    public abstract boolean checkValidity(Table table, TableRecord record);
    public abstract void beforeRecordInsertion(Table table, TableRecord record);
    public abstract boolean isColumnDeletionAllowed(Table table);
}
