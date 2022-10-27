package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.databases.ql.entities.Entity;

import java.util.Arrays;

public class InstructionTemplate {
    private final String template;

    public InstructionTemplate(String template) {
        this.template = template;
    }

    public String format(Entity... entities) {
        return template.formatted(Arrays.stream(entities).map(Entity::toInstructionForm).toArray());
    }

    public String getTemplate() {
        return template;
    }
}
