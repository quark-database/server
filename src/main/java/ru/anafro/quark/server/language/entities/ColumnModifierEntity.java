package ru.anafro.quark.server.language.entities;

import ru.anafro.quark.server.database.data.ColumnModifier;
import ru.anafro.quark.server.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.language.entities.exceptions.TypeCanNotBeUsedInRecordsException;
import ru.anafro.quark.server.facade.Quark;

import java.util.Objects;

public class ColumnModifierEntity extends Entity {
    private final ColumnModifier modifier;
    private final InstructionEntityConstructorArguments modifierArguments;

    public ColumnModifierEntity(ColumnModifier modifier, InstructionEntityConstructorArguments modifierArguments) {
        super("modifier");

        Objects.requireNonNull(modifier, "Modifier should be present when creating ColumnModifierEntity");

        this.modifier = modifier;
        this.modifierArguments = modifierArguments;
    }

    public ColumnModifierEntity(ColumnModifier modifier) {
        this(modifier, new InstructionEntityConstructorArguments());
    }

    public ColumnModifierEntity(String modifierName) {
        this(Quark.modifier(modifierName));
    }

    @Override
    public ColumnModifier getValue() {
        return modifier;
    }

    @Override
    public String format() {
        return new StringConstructorBuilder()
                .name(modifier.getName())
                .format();
    }

    public ColumnModifier getModifier() {
        return modifier;
    }

    @Override
    public String toInstructionForm() {
        return new StringConstructorBuilder()
                .name(modifier.getName())
                .build();
    }

    @Override
    public String getExactTypeName() {
        return getTypeName();
    }

    @Override
    public String toRecordForm() {
        throw new TypeCanNotBeUsedInRecordsException(getType());
    }

    @Override
    public int rawCompare(Entity entity) {
        return modifier.getName().compareTo(((ColumnModifierEntity) entity).getModifier().getName());
    }

    @Override
    public int hashCode() {
        return Quark.stringHashingFunction().hash(toInstructionForm());
    }

    public InstructionEntityConstructorArguments getModifierArguments() {
        return modifierArguments;
    }
}
