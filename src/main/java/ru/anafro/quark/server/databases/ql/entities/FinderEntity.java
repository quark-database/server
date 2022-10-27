package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.TableRecordFinder;
import ru.anafro.quark.server.databases.ql.entities.exceptions.TypeCanNotBeUsedInRecordsException;

public class FinderEntity extends Entity {
    private final TableRecordFinder finder;

    public FinderEntity(TableRecordFinder finder) {
        super("finder");
        this.finder = finder;
    }

    @Override
    public TableRecordFinder getValue() {
        return finder;
    }

    @Override
    public String getExactTypeName() {
        return getTypeName();
    }

    @Override
    public String toRecordForm() {
        throw new TypeCanNotBeUsedInRecordsException(getType());
    }

    @Override
    public int rawCompare(Entity entity) {
        return finder.getColumnName().compareTo(((FinderEntity) entity).getValue().getColumnName());
    }

    @Override
    public int hashCode() {
        return Quark.stringHashingFunction().hash(toInstructionForm());
    }
}
