package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.entities.Entity;

public abstract class EntityType {
    private final String name;

    public EntityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Entity makeEntity(String string);
    public abstract String toInstructionForm(Entity entity);

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
}
