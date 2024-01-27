package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.facade.Quark;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class YepCommand extends Command {
    public YepCommand() {
        super(list("/", "yep", "fuck"), "Reruns the last command with suggested name", "Reruns the last command with suggested name");
    }

    @Override
    public void action(CommandArguments arguments) {
        var commandLoop = Quark.commandLoop();
        commandLoop.tryGetFailedCommand().map(Quark::command).ifPresentOrElse(command -> {
            command.run(commandLoop.getFailedArguments());
        }, () -> {
            logger.error("The last command was correct.");
        });
    }
}
