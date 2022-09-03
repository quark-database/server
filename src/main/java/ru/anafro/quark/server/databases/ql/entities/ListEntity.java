package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;
import java.util.Iterator;

public class ListEntity extends Entity implements Iterable<Entity> {
    private final ArrayList<Entity> values;
    private final String typeOfValuesInside;

    public ListEntity(String typeOfValuesInside, Entity... values) {
        super("list of " + typeOfValuesInside);
        this.values = new ArrayList<>();
        this.typeOfValuesInside = typeOfValuesInside;

        for(var value : values) {
            add(value);
        }
    }

    public void add(Entity entity) {
        if(entity.getExactTypeName().equals(typeOfValuesInside) || typeOfValuesInside.equals(Entity.WILDCARD_TYPE)) {
            this.values.add(entity);
        } else {
            throw new DatabaseException("A list with type %s cannot contain an element %s with type %s".formatted(typeOfValuesInside, entity.getValue().toString(), entity.getType())); // TODO: Create a new exception type
        }
    }

    @Override
    public ArrayList<Entity> getValue() {
        return values;
    }

    @Override
    public String getValueAsString() {
        TextBuffer listAsString = new TextBuffer();

        listAsString.append('[');

        for(int index = 0; index < values.size(); index++) {
            var entity = valueAt(index);
            listAsString.append(entity.getValueAsString());

            if(index != values.size() - 1) {
                listAsString.append(", ");
            }
        }

        listAsString.append(']');

        return listAsString.extractContent();
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
