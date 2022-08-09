package ru.anafro.quark.server.databases;

import ru.anafro.quark.server.databases.instructions.entities.InstructionEntity;

public record InstructionArgument(String name, InstructionEntity value) {
}
