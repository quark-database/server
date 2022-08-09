package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionArguments;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class ScheduleQueryInstruction extends Instruction {
    public ScheduleQueryInstruction() {
        super("schedule query", "server.schedule.query",

                InstructionParameter.general("query"),

                InstructionParameter.required("every", InstructionParameter.Types.INT)
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {

    }
}
