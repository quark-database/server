package ru.anafro.quark.server.scheduling;

import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.time.TimeSpan;

public class InstructionRepeater extends Repeater {
    private final String instruction;

    public InstructionRepeater(String query, TimeSpan interval) {
        super(interval);
        this.instruction = query;
    }

    @Override
    public void performAction() {
        Quark.query(instruction);
    }

    public String getInstruction() {
        return instruction;
    }
}
