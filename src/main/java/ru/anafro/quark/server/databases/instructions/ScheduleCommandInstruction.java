package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class ScheduleCommandInstruction extends Instruction {
    public ScheduleCommandInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("schedule command", "server.schedule.command",

                InstructionParameter.required("command"),

                InstructionParameter.required("every", InstructionParameter.Types.INT)
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
