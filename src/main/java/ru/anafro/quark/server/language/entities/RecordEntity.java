package ru.anafro.quark.server.language.entities;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.TableRecord;
import ru.anafro.quark.server.language.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.language.entities.exceptions.TypeCanNotBeUsedInRecordsException;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class RecordEntity extends Entity implements Iterable<Entity> {
    private final ArrayList<Entity> values;

    public RecordEntity(Entity... values) {
        super("record");
        this.values = Lists.empty();

        for (var value : values) {
            add(value);
        }
    }

    public static RecordEntity record(Object... values) {
        return new RecordEntity(Entity.wrapMany(values).toArray(Entity[]::new));
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
    public String format() {
        return new StringConstructorBuilder().name(getTypeName()).arguments(values).format();
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
        var record = (RecordEntity) entity;

        for (int index = 0; index < Math.min(values.size(), record.getValues().size()); index += 1) {
            if (values.get(index).compareTo(record.getValues().get(index)) != 0) {
                return values.get(index).compareTo(record.getValues().get(index));
            }
        }

        if (values.size() == record.getValues().size()) {
            return 0;
        } else {
            return values.size() - record.getValues().size();
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for (var entity : values) {
            hash += entity.hashCode();
        }

        return hash;
    }

    @NotNull
    @Override
    public Iterator<Entity> iterator() {
        return values.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof TableRecord record) return record.equals(this);
        if (getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RecordEntity entities = (RecordEntity) o;
        return Objects.equals(values, entities.values);
    }
}
