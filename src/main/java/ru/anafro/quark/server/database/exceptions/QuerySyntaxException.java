package ru.anafro.quark.server.database.exceptions;

public class QuerySyntaxException extends QueryException {
    public QuerySyntaxException(String message) {
        super(message);
    }
}
