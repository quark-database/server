package ru.anafro.quark.server.database.language;

import ru.anafro.quark.server.database.language.entities.Entity;

public record InstructionArgument(String name, Entity value) {
}
