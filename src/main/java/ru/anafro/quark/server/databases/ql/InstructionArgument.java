package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.databases.ql.entities.InstructionEntity;

public record InstructionArgument(String name, InstructionEntity value) {
}
