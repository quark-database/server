package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.api.Quark;

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
    public String getExactTypeName() {
        return getTypeName();
    }

    @Override
    public String toRecordForm() {
        return "D" + getDate().getTime();
    }

    @Override
    public int rawCompare(Entity entity) {
        return date.compareTo(((DateEntity) entity).getDate());
    }

    @Override
    public int hashCode() {
        return Quark.integerHashingFunction().hash((int) date.getTime());
    }
}
