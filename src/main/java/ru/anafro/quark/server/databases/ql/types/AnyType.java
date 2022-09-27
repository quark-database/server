package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.Entity;

/**
 * Represents the type of entities that can
 * contain any entity. This type is similar to {@code Object}
 * in Java or C#.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class AnyType extends EntityType {

    /**
     * Creates a new type object.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public AnyType() {
        super("any", CASTABLE_FROM_ANY_TYPE);
    }

    /**
     * Creates a new entity with {@code any} type
     * from a string.
     *
     * @param  string an entity as a string.
     * @return an entity from the string passed in.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public Entity makeEntity(String string) {
        return ConstructorEvaluator.eval(string);
    }

    /**
     * Converts object to an instruction form.
     *
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public String toInstructionForm(Entity entity) {
        return null;
    }

    /**
     * Casts an entity to this type.
     *
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    protected Entity castOrNull(Entity entity) {
        return entity;
    }
}
