package ru.anafro.quark.server.databases.ql;

public abstract class InstructionBuilder<T extends Instruction> {
    public abstract T build();
}
