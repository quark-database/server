package ru.anafro.quark.server.databases.ql.entities.constructors.columns;

import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.types.EntityType;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.ArrayList;
import java.util.List;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

/**
 * This is the base class for all column constructor of Quark QL.
 * <br><br>
 *
 * If you need to create a new column constructor, inherit your class
 * from this. <strong>Don't forget to register your constructor registry!</strong>
 * <pre>
 * {@code
 * Quark.constructors().add(new YourColumnConstructor());
 * }
 * </pre>
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public abstract class ColumnConstructor extends EntityConstructor {

    /**
     * The type of the entity type the column will contain.
     * @since Quark 1.1
     */
    private final EntityType type;

    /**
     * The modifiers applying to the column by default.
     * @since Quark 1.1
     */
    private final ArrayList<ColumnModifierEntity> modifiers;

    /**
     * The name of the column if the column name in the constructor is omitted.
     * @since Quark 1.1
     */
    private final String defaultColumnName;

    /**
     * Creates a new column constructor object. Must be called only once
     * on the constructor registration.
     *
     * @param name the name of the constructor in Quark QL.
     * @param type the type of the column this constructor is going to represent.
     * @param modifiers the default column modifiers this constructor will apply on a column.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public ColumnConstructor(String name, EntityType type, ColumnModifierEntity... modifiers) {
        super(
                name,

                returns("the column description", "column"),

                required("column name", "str")
        );
        this.type = type;
        this.defaultColumnName = null;
        this.modifiers = Lists.empty();

        for(var modifier : modifiers) {
            addModifier(modifier);
        }
    }

    /**
     * Creates a new column constructor object. Must be called only once
     * on the constructor registration.
     *
     * @param name the name of the constructor in Quark QL.
     * @param type the type of the column this constructor is going to represent.
     * @param defaultColumnName the default column name if the column name is omitted.
     * @param modifiers the default column modifiers this constructor will apply on a column.
     * @param parameters the parameters of the constructor.
     *
     * @since Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public ColumnConstructor(String name, EntityType type, String defaultColumnName, List<ColumnModifierEntity> modifiers, InstructionEntityConstructorParameter... parameters) {
        super(
                name,

                returns("the column description", "column"),

                parameters
        );
        this.type = type;
        this.defaultColumnName = defaultColumnName;
        this.modifiers = Lists.empty();

        for(var modifier : modifiers) {
            addModifier(modifier);
        }
    }

    /**
     * @return the modifiers this constructor adds by default.
     *
     * @since Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public ArrayList<ColumnModifierEntity> getModifiers() {
        return modifiers;
    }

    /**
     * @return the type of the column this constructor is going to represent.
     *
     * @since Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public EntityType getType() {
        return type;
    }

    /**
     * @return the default column name if the column name is omitted.
     *
     * @since Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public String getDefaultColumnName() {
        return defaultColumnName;
    }

    /**
     * Creates a new column entity instance.
     *
     * @param arguments the column constructor arguments
     * @return a new column entity.
     *
     * @since Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    protected ColumnEntity invoke(InstructionEntityConstructorArguments arguments) {
        var columnName = getParameters().has("column name") ? arguments.getString("column name") : this.defaultColumnName;
        return new ColumnEntity(new ColumnDescription(columnName, type, modifiers));
    }

    /**
     * Adds a new default modifier to this constructor.
     * Should be called only inside the constructor of {@link ColumnConstructor}.
     *
     * @param modifier the adding modifier.
     *
     * @since Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private void addModifier(ColumnModifierEntity modifier) {
        modifiers.add(modifier);
    }
}
