package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.entities.exceptions.TypeCanNotBeUsedInRecordsException;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.ArrayList;
import java.util.Iterator;

public class ListEntity extends Entity implements Iterable<Entity> {
    private final ArrayList<Entity> values;
    private final String typeOfValuesInside;

    public ListEntity(String typeOfValuesInside, Entity... entities) {
        super("list");
        this.values = Lists.empty();
        this.typeOfValuesInside = typeOfValuesInside;

        for(var value : entities) {
            add(value);
        }
    }

    public ListEntity(String typeOfValuesInside, Iterable<? extends Entity> entities) {
        super("list");
        this.values = Lists.empty();
        this.typeOfValuesInside = typeOfValuesInside;

        for(var entity : entities) {
            add(entity);
        }
    }

    public static ListEntity of(Iterable<? extends Entity> entities) {
        return new ListEntity("any", entities);
    }

    public static ListEntity of(Entity... entities) {
        return new ListEntity("any", entities);
    }

    public void add(Entity entity) {
        if(entity.getExactTypeName().equals(typeOfValuesInside) || typeOfValuesInside.equals(Entity.WILDCARD_TYPE)) {
            values.add(entity);
        } else if(Quark.type(typeOfValuesInside).castableFrom(entity.getType())) {
            values.add(Quark.type(typeOfValuesInside).cast(entity));
        } else {
            throw new DatabaseException("A list with type %s cannot contain an element %s with type %s".formatted(typeOfValuesInside, entity.getValue().toString(), entity.getType())); // TODO: Create a new exception type
        }
    }

    @Override
    public ArrayList<Entity> getValue() {
        return values;
    }

    @Override
    public String getExactTypeName() {
        return getType().getName() + " of " + getTypeOfValuesInside();
    }

    @Override
    public String toRecordForm() {
        throw new TypeCanNotBeUsedInRecordsException(getType());
    }

    @Override
    public int rawCompare(Entity entity) {
        var list = (ListEntity) entity;

        for(int index = 0; index < Math.min(size(), list.size()); index += 1) {
            if(valueAt(index).compareTo(list.valueAt(index)) != 0) {
                return valueAt(index).compareTo(list.valueAt(index));
            }
        }

        if(size() == list.size()) {
            return 0;
        } else {
            return size() - list.size();
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for(var entity : values) {
            hash += entity.hashCode();
        }

        return hash;
    }

    public String getTypeOfValuesInside() {
        return typeOfValuesInside;
    }

    public Entity valueAt(int index) {
        return values.get(index);
    }

    @Override
    public Iterator<Entity> iterator() {
        return values.iterator();
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }
}
