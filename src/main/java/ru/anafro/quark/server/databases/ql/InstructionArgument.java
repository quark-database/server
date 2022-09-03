package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.databases.ql.entities.Entity;

public record InstructionArgument(String name, Entity value) {
}
