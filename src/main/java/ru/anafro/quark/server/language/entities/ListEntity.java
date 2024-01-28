package ru.anafro.quark.server.language.entities;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.language.entities.exceptions.TypeCanNotBeUsedInRecordsException;
import ru.anafro.quark.server.language.types.EntityType;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static ru.anafro.quark.server.utils.strings.English.pluralize;

public class ListEntity extends Entity implements Iterable<Entity> {
    private final ArrayList<Entity> values;
    private final String typeOfElements;

    public ListEntity(String typeOfElements, Entity... entities) {
        super("list");
        this.values = Lists.empty();
        this.typeOfElements = typeOfElements;

        for (var value : entities) {
            add(value);
        }
    }

    public ListEntity(String typeOfElements, Iterable<? extends Entity> entities) {
        super("list");
        this.values = Lists.empty();
        this.typeOfElements = typeOfElements;

        for (var entity : entities) {
            add(entity);
        }
    }

    public static ListEntity of(List<? extends Entity> entities) {
        if (entities.isEmpty()) {
            return ListEntity.empty();
        }

        return new ListEntity(entities.getFirst().getTypeName(), entities);
    }

    public static ListEntity of(Object... objects) {
        if (objects.length == 0) {
            return empty();
        }

        var elements = Stream.of(objects).map(Entity::wrap).toList();
        return new ListEntity(elements.getFirst().getTypeName(), elements);
    }

    public static ListEntity of(Entity... entities) {
        if (entities.length == 0) {
            return empty();
        }

        return new ListEntity(entities[0].getExactTypeName(), entities);
    }

    public static ListEntity empty() {
        return empty("any");
    }

    public static ListEntity empty(String type) {
        return new ListEntity(type);
    }

    public void add(Entity entity) {
        if (entity.getExactTypeName().equals(typeOfElements) || typeOfElements.equals(WILDCARD_TYPE)) {
            values.add(entity);
        } else if (Quark.type(typeOfElements).canBeCastedFrom(entity.getType())) {
            values.add(Quark.type(typeOfElements).cast(entity));
        } else {
            throw new DatabaseException("A list with type %s cannot contain an element %s with type %s".formatted(typeOfElements, entity.getValue().toString(), entity.getExactTypeName())); // TODO: Create a new exception type
        }
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
        return STR."\{getType().getName()} of \{getTypeNameOfElements()}";
    }

    @Override
    public String toRecordForm() {
        throw new TypeCanNotBeUsedInRecordsException(getType());
    }

    @Override
    public int rawCompare(Entity entity) {
        var list = (ListEntity) entity;

        for (int index = 0; index < Math.min(size(), list.size()); index += 1) {
            if (valueAt(index).compareTo(list.valueAt(index)) != 0) {
                return valueAt(index).compareTo(list.valueAt(index));
            }
        }

        if (size() == list.size()) {
            return 0;
        } else {
            return size() - list.size();
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

    public String getTypeNameOfElements() {
        return typeOfElements;
    }

    public boolean elementsDoesntHaveType(String type) {
        return !typeOfElements.equals(type);
    }

    public Entity valueAt(int index) {
        return values.get(index);
    }

    public Entity getFirst() {
        return valueAt(0);
    }

    @NotNull
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

    public boolean canNotBeMappedToType(EntityType<?> type) {
        var typeName = type.getName();
        var elementsType = Quark.type(typeOfElements);

        return elementsDoesntHaveType(typeName) && elementsType.canNotBeCastedTo(type);
    }

    public ListEntity mapToType(EntityType<?> mappingType) {
        if (canNotBeMappedToType(mappingType)) {
            var listTypeName = pluralize(this.typeOfElements);
            var mappingTypeName = mappingType.getName();
            throw new DatabaseException(STR."A list of \{listTypeName} cannot be mapped to type \{mappingTypeName}.");
        }

        if (this.isEmpty()) {
            return ListEntity.empty(mappingType.getName());
        }

        return ListEntity.of(values.stream().map(mappingType::cast).toList());
    }
}
