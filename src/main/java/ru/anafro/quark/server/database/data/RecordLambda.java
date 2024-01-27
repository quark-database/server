package ru.anafro.quark.server.database.data;

import java.util.function.Function;

public interface RecordLambda<T> extends Function<TableRecord, T> {
    //
}
