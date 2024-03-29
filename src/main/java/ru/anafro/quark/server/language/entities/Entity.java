package ru.anafro.quark.server.language.entities;

import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.language.entities.exceptions.EntitiesCannotBeComparedException;
import ru.anafro.quark.server.language.entities.exceptions.InstructionEntityCastException;
import ru.anafro.quark.server.language.types.EntityType;
import ru.anafro.quark.server.utils.patterns.exceptions.ObjectIsMissingInRegistryException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class Entity implements Comparable<Entity> {
    public static final String WILDCARD_TYPE = "?";
    private final EntityType<?> type;

    public Entity(String type) {
        this.type = Quark.types().tryGet(type).orElseThrow(() -> new ObjectIsMissingInRegistryException("Type %s is not exist. Did you mean %s?".formatted(type, Quark.types().suggest(type).getName())));
    }

    public static <T> Entity wrap(T object) {
        if (object instanceof Entity entity) {
            return entity;
        }

        for (var type : Quark.types()) {
            if (type.canWrap(object)) {
                return type.wrap(object);
            }
        }

        return null;
    }

    public static <T> List<Entity> wrapMany(T[] objects) {
        return Arrays.stream(objects).map(Entity::wrap).toList();
    }

    public <T> T valueAs(Class<T> clazz) {
        var object = this.getValue();

        if (clazz.isInstance(object)) {
            return clazz.cast(object);
        } else {
            throw new InstructionEntityCastException(object, clazz);
        }
    }

    public <T> Optional<List<T>> tryGetValueAsListOf(Class<T> elementsType) {
        if (!(this instanceof ListEntity list)) {
            return Optional.empty();
        }

        var elements = list.getValue();
        return Optional.of(elements.stream().map(elementsType::cast).toList());
    }

    public String toInstructionForm() {
        return type.toInstructionForm(this);
    }

    public EntityType<?> getType() {
        return type;
    }

    public String getTypeName() {
        return type.getName();
    }

    public boolean hasType(EntityType<?> type) {
        return type.equals(this.type);
    }

    public boolean hasType(String typeName) {
        return hasType(Quark.types().get(typeName));
    }

    public boolean doesntHaveType(EntityType<?> type) {
        return !hasType(type);
    }

    public boolean doesntHaveType(String typeName) {
        return doesntHaveType(Quark.types().get(typeName));
    }

    public abstract Object getValue();

    public abstract String format();

    public abstract String getExactTypeName();

    public abstract String toRecordForm();

    public abstract int rawCompare(Entity entity);

    public Entity castTo(EntityType<?> type) {
        return type.cast(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        var entity = (Entity) obj;
        return Objects.equals(getValue(), entity.getValue());
    }

    @Override
    public abstract int hashCode();

    @Override
    public int compareTo(Entity entity) {
        if (entity.doesntHaveType(getType())) {
            throw new EntitiesCannotBeComparedException(this, entity);
        }

        return rawCompare(entity);
    }

    @Override
    public String toString() {
        return toInstructionForm();
    }

    public boolean hasExactType(String exactTypeName) {
        return exactTypeName.equals(this.getExactTypeName());
    }

    public boolean doesntHaveExactType(String exactTypeName) {
        return !hasExactType(exactTypeName);
    }
}
