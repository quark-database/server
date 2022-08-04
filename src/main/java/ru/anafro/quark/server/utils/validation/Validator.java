package ru.anafro.quark.server.utils.validation;

public interface Validator<T> {
    boolean isValid(T value);
}
