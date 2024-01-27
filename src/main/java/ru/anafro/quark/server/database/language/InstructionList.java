package ru.anafro.quark.server.database.language;

import ru.anafro.quark.server.utils.patterns.NamedObjectsList;

public class InstructionList extends NamedObjectsList<Instruction> {
    @Override
    protected String getNameOf(Instruction instruction) {
        return instruction.getName();
    }
}
