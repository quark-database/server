package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionParameter;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
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
