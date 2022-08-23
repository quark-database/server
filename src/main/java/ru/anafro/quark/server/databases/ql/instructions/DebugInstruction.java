package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.views.TableViewHeader;
import ru.anafro.quark.server.databases.views.TableViewRow;
import ru.anafro.quark.server.networking.Server;

import java.util.Random;
import java.util.UUID;

public class DebugInstruction extends Instruction {
    public DebugInstruction() {
        super("debug", "!unsafe.debug", InstructionParameter.general("general parameter", "?"));
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        result.header(new TableViewHeader("id", "name", "email", "password"));

        for(int i = 0; i < 100; i++) {
            result.appendRow(new TableViewRow(String.valueOf(i), UUID.randomUUID().toString(), "a".repeat(new Random().nextInt(30)) + "@" + "b".repeat(new Random().nextInt(30)) + ".com", UUID.randomUUID().toString()));
        }

        result.status(InstructionExecutionStatus.OK, "100 rows are generated successfully");
    }
}
