package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.ql.entities.exceptions.TypeCanNotBeUsedInRecordsException;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.ArrayList;
import java.util.Iterator;

public class RecordEntity extends Entity implements Iterable<Entity> {
    private final ArrayList<Entity> values;

    public RecordEntity(Entity... values) {
        super("record");
        this.values = Lists.empty();

        for(var value : values) {
            add(value);
        }
    }

    private void add(Entity value) {
        values.add(value);
    }

    public ArrayList<Entity> getValues() {
        return values;
    }

    @Override
    public ArrayList<Entity> getValue() {
        return values;
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
    public Iterator<Entity> iterator() {
        return values.iterator();
    }
}
