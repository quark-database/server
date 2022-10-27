package ru.anafro.quark.server.scheduling;

import ru.anafro.quark.server.api.Quark;

public class InstructionScheduledTask extends ScheduledTask {
    private final String instruction;

    public InstructionScheduledTask(String query, long delay) {
        super("query '" + query + "'", delay);
        this.instruction = query;
    }

    @Override
    public void action() {
        Quark.runInstruction(instruction);
    }

    public String getInstruction() {
        return instruction;
    }
}
