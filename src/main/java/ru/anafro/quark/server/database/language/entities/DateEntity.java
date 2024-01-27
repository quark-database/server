package ru.anafro.quark.server.database.language.entities;

import ru.anafro.quark.server.database.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.facade.Quark;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateEntity extends Entity {
    private final Date date;

    public DateEntity(Date date) {
        super("date");
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public Date getValue() {
        return date;
    }

    @Override
    public String format() {
        return new StringConstructorBuilder().name("date from timestamp").argument(new LongEntity(date.getTime())).format();
    }

    @Override
    public String getExactTypeName() {
        return getTypeName();
    }

    @Override
    public String toRecordForm() {
        return STR."D\{date.getTime()}";
    }

    @Override
    public int rawCompare(Entity entity) {
        return date.compareTo(((DateEntity) entity).getDate());
    }

    @Override
    public int hashCode() {
        return Quark.integerHashingFunction().hash((int) date.getTime());
    }

    @Override
    public String toString() {
        return new SimpleDateFormat().format(date);
    }
}
