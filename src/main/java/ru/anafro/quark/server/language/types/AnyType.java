package ru.anafro.quark.server.language.types;

import ru.anafro.quark.server.language.entities.AnyEntity;
import ru.anafro.quark.server.language.entities.Entity;

/**
 * Represents the type of entities that can
 * contain any entity. This type is similar to {@code Object}
 * in Java or C#.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class AnyType extends EntityType<AnyEntity> {

    /**
     * Creates a new type object.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public AnyType() {
        super("any", Object.class, AnyEntity.class, CAN_BE_CASTED_FROM_ANY_TYPE);
    }

    /**
     * Converts object to an instruction form.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    public String toInstructionForm(Entity entity) {
        return null;
    }

    /**
     * Casts an entity to this type.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected Entity castOrNull(Entity entity) {
        return entity;
    }
}
