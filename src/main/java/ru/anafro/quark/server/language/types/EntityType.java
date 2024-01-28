package ru.anafro.quark.server.language.types;

import ru.anafro.quark.server.language.Expressions;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.exceptions.EntityCannotBeCastedException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.reflection.Reflection;

import java.util.List;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public abstract class EntityType<T extends Entity> {
    public static final String[] CAN_BE_CASTED_FROM_ANY_TYPE = null;
    protected final Class<T> entityType;
    private final String name;
    private final List<String> canBeCastedFromTypes;
    private final Class<?> nativeType;

    public EntityType(String name, Class<?> nativeType, Class<T> entityType, String... canBeCastedFromTypes) {
        this.name = name;
        this.nativeType = nativeType;
        this.entityType = entityType;
        this.canBeCastedFromTypes = canBeCastedFromTypes == null ? null : list(canBeCastedFromTypes);
    }

    public static EntityType<?> fromClass(Class<?> nativeType) {
        for (var type : Quark.types()) {
            if (type.canWrapType(nativeType)) {
                return type;
            }
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public List<String> getCanBeCastedFromTypes() {
        return canBeCastedFromTypes;
    }

    public T makeEntity(String string) {
        return Expressions.eval(entityType, string);
    }

    public abstract String toInstructionForm(Entity entity);

    protected abstract Entity castOrNull(Entity entity);

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof EntityType<?> that)) {
            return false;
        }

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public boolean canBeCastedFromAnyType() {
        return canBeCastedFromTypes == null;
    }

    public boolean canBeCastedTo(EntityType<?> type) {
        return type.canBeCastedFrom(this);
    }

    public boolean canNotBeCastedTo(EntityType<?> type) {
        return !canBeCastedTo(type);
    }

    public boolean canBeCastedFrom(EntityType<?> type) {
        return canBeCastedFromAnyType() || canBeCastedFromTypes.contains(type.getName());
    }

    public boolean canBeCastedFrom(String typeName) {
        return canBeCastedFrom(Quark.types().get(typeName));
    }

    public boolean canCast(Entity entity) {
        return canBeCastedFrom(entity.getType());
    }

    public boolean canNotCast(Entity entity) {
        return !canCast(entity);
    }

    public Entity cast(Entity entity) {
        if (entity.hasType(this)) {
            return entity;
        }

        if (!canCast(entity)) {
            throw new EntityCannotBeCastedException(entity, this);
        }

        return castOrNull(entity);
    }

    public Class<?> getNativeType() {
        return nativeType;
    }

    public boolean canWrapType(Class<?> type) {
        if (nativeType == null) {
            return Reflection.instanceOf(this, NullType.class);
        }

        return type.equals(nativeType) || Reflection.isPrimitiveType(type, nativeType);
    }

    public boolean canWrap(Object object) {
        return canWrapType(object.getClass());
    }

    public boolean canNotWrap(Object object) {
        return !canWrap(object);
    }

    public T wrap(Object object) {
        if (canNotWrap(object)) {
            return null;
        }

        return Reflection.newInstance(Reflection.getConstructor(entityType, nativeType), object);
    }

    public boolean hasNativeType() {
        return nativeType != null;
    }

    @Override
    public String toString() {
        return name;
    }
}
