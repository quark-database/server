package ru.anafro.quark.server.database.language.entities.constructors.columns;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.language.entities.ColumnEntity;
import ru.anafro.quark.server.database.language.entities.ColumnModifierEntity;
import ru.anafro.quark.server.database.language.entities.EntityConstructor;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.database.language.types.EntityType;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.List;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter.varargs;
import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorReturnDescription.returns;
import static ru.anafro.quark.server.utils.collections.Collections.list;

/**
 * This is the base class for all column constructor of Quark QL.
 * <br><br>
 * <p>
 * If you need to create a new column constructor, inherit your class
 * from this. <strong>Don't forget to register your constructor registry!</strong>
 * <pre>
 * {@code
 * Quark.constructors().add(new YourColumnConstructor());
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public abstract class ColumnConstructor extends EntityConstructor {

    /**
     * The type of the entity type the column will contain.
     *
     * @since Quark 1.1
     */
    private final EntityType<?> type;

    /**
     * The modifiers applying to the column by default.
     *
     * @since Quark 1.1
     */
    private final List<ColumnModifierEntity> modifiers;

    /**
     * The name of the column if the column name in the constructor is omitted.
     *
     * @since Quark 1.1
     */
    private final String defaultColumnName;

    /**
     * Creates a new column constructor object. Must be called only once
     * on the constructor registration.
     *
     * @param name      the name of the constructor in Quark QL.
     * @param typeName  the type of the column this constructor is going to represent.
     * @param modifiers the default column modifiers this constructor will apply on a column.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ColumnConstructor(String name, String typeName, ColumnModifierEntity... modifiers) {
        super(
                name,

                returns("the column description", "column"),
                required("column name", "str"),
                varargs("modifiers", "modifier")
        );
        this.type = Quark.type(typeName);
        this.defaultColumnName = null;
        this.modifiers = list(modifiers);
    }

    /**
     * Creates a new column entity instance.
     *
     * @param arguments the column constructor arguments
     * @return a new column entity.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    protected ColumnEntity invoke(InstructionEntityConstructorArguments arguments) {
        var columnName = getParameters().has("column name") ? arguments.getString("column name") : this.defaultColumnName;
        var additionalModifiers = arguments.getList(ColumnModifierEntity.class, "modifiers");
        var allModifiers = Lists.<ColumnModifierEntity>empty();

        allModifiers.addAll(additionalModifiers);
        allModifiers.addAll(modifiers);

        return new ColumnEntity(new ColumnDescription(columnName, type, allModifiers));
    }
}
