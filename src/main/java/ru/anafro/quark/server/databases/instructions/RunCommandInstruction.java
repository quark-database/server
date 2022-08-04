package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class RunCommandInstruction extends Instruction {
    public RunCommandInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("run command", "server.command",

                InstructionParameter.required("command")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
