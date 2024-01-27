package ru.anafro.quark.server.database.language;

import ru.anafro.quark.server.database.language.entities.Entity;

import java.util.Arrays;

public record InstructionTemplate(String template) {

    public String format(Entity... entities) {
        return template.formatted(Arrays.stream(entities).map(Entity::toInstructionForm).toArray());
    }
}
