package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class RunCommand extends Command {
    public RunCommand() {
        super(new UniqueList<>("run", "!"), "Runs an instruction or a command", "Runs an instruction or a command", new CommandParameter("instruction", "An instruction to run", "An instruction that should be run", false), new CommandParameter("command", "A command to run", "A command that should be run", false));
    }

    @Override
    public void action(CommandArguments arguments) {
        if(arguments.has("instruction") && arguments.has("command")) {
            error("'run' command should receive either an instruction or command, but you provided them both. Keep only one");
        }

        if(!arguments.has("instruction") && !arguments.has("command")) {
            error("'run' command should receive either an instruction or command, but you provided none of them. Add one");
        }

        if(arguments.has("command")) {
            Quark.runCommand(arguments.get("command"));
        }

        if(arguments.has("instruction")) {
            logger.info(Quark.runInstruction(arguments.get("instruction")).toString());
        }
    }
}
