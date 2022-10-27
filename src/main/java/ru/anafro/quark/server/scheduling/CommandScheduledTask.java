package ru.anafro.quark.server.scheduling;

import ru.anafro.quark.server.api.Quark;

public class CommandScheduledTask extends ScheduledTask {
    private final String command;

    public CommandScheduledTask(String command, long delay) {
        super("command '" + command + "'", delay);
        this.command = command;
    }

    @Override
    public void action() {
        Quark.runCommand(command);
    }

    public String getCommand() {
        return command;
    }
}
