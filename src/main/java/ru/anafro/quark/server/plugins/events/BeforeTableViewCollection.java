package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;

public class BeforeTableViewCollection extends Event {
    private final InstructionResultRecorder recorder;

    public BeforeTableViewCollection(InstructionResultRecorder recorder) {
        this.recorder = recorder;
    }

    public InstructionResultRecorder getRecorder() {
        return recorder;
    }
}
