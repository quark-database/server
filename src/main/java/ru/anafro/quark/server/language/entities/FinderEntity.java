package ru.anafro.quark.server.language.entities;

import ru.anafro.quark.server.database.data.TableRecordFinder;
import ru.anafro.quark.server.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.language.entities.exceptions.TypeCanNotBeUsedInRecordsException;
import ru.anafro.quark.server.facade.Quark;

public class FinderEntity extends Entity {
    private final TableRecordFinder finder;

    public FinderEntity(TableRecordFinder finder) {
        super("finder");
        this.finder = finder;
    }

    public TableRecordFinder getFinder() {
        return finder;
    }

    @Override
    public TableRecordFinder getValue() {
        return finder;
    }

    @Override
    public String format() {
        return new StringConstructorBuilder()
                .name("finder")
                .argument(new StringEntity(finder.columnName()))
                .argument(finder.findingValue())
                .format();
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
        return finder.columnName().compareTo(((FinderEntity) entity).getValue().columnName());
    }

    @Override
    public int hashCode() {
        return Quark.stringHashingFunction().hash(toInstructionForm());
    }
}
