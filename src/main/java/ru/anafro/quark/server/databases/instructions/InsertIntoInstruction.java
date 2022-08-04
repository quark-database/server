package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.networking.Server;

public class InsertIntoInstruction extends Instruction {
    public InsertIntoInstruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        super("insert into", "table.insert",

                InstructionParameter.required("table"),

                InstructionParameter.required("record", "array")
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {

    }
}
