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

    public ListEntity(String typeOfValuesInside, Entity... values) {
        super("list");
        this.values = Lists.empty();
        this.typeOfValuesInside = typeOfValuesInside;

        for(var value : values) {
            add(value);
        }
    }

    public ListEntity(String typeOfValuesInside, Iterable<? extends Entity> values) {
        super("list");
        this.values = Lists.empty();
        this.typeOfValuesInside = typeOfValuesInside;

        for(var value : values) {
            add(value);
        }
    }

    public void add(Entity entity) {
        if(entity.getExactTypeName().equals(typeOfValuesInside) || typeOfValuesInside.equals(Entity.WILDCARD_TYPE)) {
            values.add(entity);
        } else if(Quark.types().get(typeOfValuesInside).castableFrom(entity.getType())) {
            values.add(Quark.types().get(typeOfValuesInside).cast(entity));
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
