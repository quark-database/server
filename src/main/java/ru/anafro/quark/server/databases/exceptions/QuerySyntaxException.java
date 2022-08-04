package ru.anafro.quark.server.databases.exceptions;

public class QuerySyntaxException extends QueryException {
    public QuerySyntaxException(String message) {
        super(message);
    }
}
