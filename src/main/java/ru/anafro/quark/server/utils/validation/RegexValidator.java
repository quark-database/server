package ru.anafro.quark.server.utils.validation;

public record RegexValidator(String regex) implements Validator<String> {

    @Override
    public boolean isValid(String value) {
        return value.matches(regex);
    }
}
