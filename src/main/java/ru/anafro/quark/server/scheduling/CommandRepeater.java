package ru.anafro.quark.server.scheduling;

import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.time.TimeSpan;

public class CommandRepeater extends Repeater {
    private final String command;

    public CommandRepeater(String command, TimeSpan interval) {
        super(interval);
        this.command = command;
    }

    @Override
    public void performAction() {
        Quark.runCommand(command);
    }

    public String getCommand() {
        return command;
    }
}
