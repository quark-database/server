package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class ScheduleQueryInstruction extends Instruction {
    public ScheduleQueryInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("schedule query", "server.schedule.query",

                InstructionParameter.required("query"),

                InstructionParameter.required("every", InstructionParameter.Types.INT)
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
