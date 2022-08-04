package ru.anafro.quark.server.databases.instructions;

import ru.anafro.quark.server.databases.InstructionResultRecorder;
import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;
import ru.anafro.quark.server.databases.exceptions.QuerySyntaxException;
import ru.anafro.quark.server.networking.Ports;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.networking.exceptions.PortIsUnavailableException;

public class ChangePortToInstruction extends Instruction {
    public ChangePortToInstruction() {
        super("change port to",
                "server.port.change",

                InstructionParameter.required("port", InstructionParameter.Types.INT)
        );
    }

    @Override
    public void action(Server server, InstructionResultRecorder result) {
        int newPort = getParameterByName("port").asInt();

        if(Ports.isInvalid(newPort)) {
            throw new QuerySyntaxException("Port should be between %d and %d, not %d".formatted(Ports.FIRST, Ports.LAST, newPort));
        }

        if(Ports.isUnavailable(newPort)) {
            throw new PortIsUnavailableException(newPort);
        }

        server.getConfiguration().setPort(newPort);
        server.reload();
    }
}
