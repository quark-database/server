package ru.anafro.quark.server.databases.ql.entities.constructors.columns;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.exceptions.ColumnModifierNotFoundException;
import ru.anafro.quark.server.databases.ql.types.EntityType;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.ArrayList;
import java.util.List;

public abstract class ColumnConstructor extends InstructionEntityConstructor {
    private final EntityType type;
    private final ArrayList<ColumnModifier> modifiers;
    private final String defaultColumnName;

    public ColumnConstructor(String name, EntityType type, String... modifiersNames) {
        super(name, InstructionEntityConstructorParameter.required("column name", "str"));
        this.type = type;
        this.defaultColumnName = null;
        this.modifiers = Lists.empty();

        for(var modifierName : modifiersNames) {
            if(Quark.modifiers().missing(modifierName)) {
                throw new ColumnModifierNotFoundException(modifierName);
            }

            modifiers.add(Quark.modifiers().get(modifierName));
        }
    }

    public ColumnConstructor(String name, EntityType type, String defaultColumnName, List<String> modifiersNames, InstructionEntityConstructorParameter... parameters) {
        super(name, parameters);
        this.type = type;
        this.defaultColumnName = defaultColumnName;
        this.modifiers = Lists.empty();

    }

    public ArrayList<ColumnModifier> getModifiers() {
        return modifiers;
    }

    public EntityType getType() {
        return type;
    }

    public String getDefaultColumnName() {
        return defaultColumnName;
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        var columnName = getParameters().has("column name") ? arguments.<StringEntity>get("column name").getValue() : this.defaultColumnName;
        return new ColumnEntity(new ColumnDescription(columnName, type, modifiers));
    }

    private void addModifier(String modifierName) {
        if(Quark.modifiers().missing(modifierName)) {
            throw new ColumnModifierNotFoundException(modifierName);
        }

        modifiers.add(Quark.modifiers().get(modifierName));
    }
}
