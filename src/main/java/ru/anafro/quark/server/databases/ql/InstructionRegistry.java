package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.utils.patterns.NamedObjectsRegistry;

public class InstructionRegistry extends NamedObjectsRegistry<Instruction> {
    @Override
    protected String getNameOf(Instruction instruction) {
        return instruction.getName();
    }
}
