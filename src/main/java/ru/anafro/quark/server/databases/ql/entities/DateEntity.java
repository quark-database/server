package ru.anafro.quark.server.databases.ql.entities;

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
}
