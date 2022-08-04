package ru.anafro.quark.server.databases;

public abstract class InstructionBuilder<T extends Instruction> {
    public abstract T build();
}
