package ru.anafro.quark.server.language.entities;

import ru.anafro.quark.server.database.data.parser.RecordCharacterEscapeService;
import ru.anafro.quark.server.facade.Quark;

public class StringEntity extends Entity {
    private final String value;

    public StringEntity(String value) {
        super("str");
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String format() {
        return STR."<italic><salad>\"\{value}\"</></>";
    }

    @Override
    public String toInstructionForm() {
        return STR."\"\{new RecordCharacterEscapeService().wrapEscapableCharacters(value)}\"";
    }

    @Override
    public String getExactTypeName() {
        return getType().getName();
    }

    @Override
    public String toRecordForm() {
        return toInstructionForm();
    }

    @Override
    public int rawCompare(Entity entity) {
        return value.compareTo(((StringEntity) entity).getString());
    }

    @Override
    public int hashCode() {
        return Quark.stringHashingFunction().hash(value);
    }

    public String getString() {
        return getValue();
    }
}
