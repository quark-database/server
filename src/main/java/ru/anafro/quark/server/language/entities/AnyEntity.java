package ru.anafro.quark.server.language.entities;

public class AnyEntity extends Entity {
    private final Entity entity;

    @SuppressWarnings("unused")
    public AnyEntity(Entity entity) {
        super("any");

        this.entity = entity;
    }

    @Override
    public Entity getValue() {
        return entity;
    }

    @Override
    public String format() {
        return entity.format();
    }

    @Override
    public String getExactTypeName() {
        return entity.getExactTypeName();
    }

    @Override
    public String toRecordForm() {
        return entity.toRecordForm();
    }

    @Override
    public int rawCompare(Entity entity) {
        return entity.rawCompare(this.entity);
    }

    @Override
    public int hashCode() {
        return entity.hashCode();
    }
}
