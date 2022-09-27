package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.exceptions.EntityCannotBeCastedException;
import ru.anafro.quark.server.utils.containers.UniqueList;

public abstract class EntityType {
    private final String name;
    private final UniqueList<String> castableFromTypes;
    public static final String[] CASTABLE_FROM_ANY_TYPE = null;

    public EntityType(String name, String... castableFromTypes) {
        this.name = name;
        this.castableFromTypes = castableFromTypes == null ? null : new UniqueList<>(castableFromTypes);
    }

    public String getName() {
        return name;
    }

    public UniqueList<String> getCastableFromTypes() {
        return castableFromTypes;
    }

    public abstract Entity makeEntity(String string);
    public abstract String toInstructionForm(Entity entity);
    protected abstract Entity castOrNull(Entity entity);

    @Override
    public boolean equals(Object object) {
        if(this == object) {
            return true;
        }

        if(!(object instanceof EntityType that)) {
            return false;
        }

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public boolean castableFrom(EntityType type) {
        return castableFromTypes == null || castableFromTypes.contains(type.getName());
    }

    public boolean castableFrom(String typeName) {
        return castableFrom(Quark.types().get(typeName));
    }

    public Entity cast(Entity entity) {
        if(!castableFrom(entity.getType())) {
            throw new EntityCannotBeCastedException(entity, this);
        }

        return castOrNull(entity);
    }
}
