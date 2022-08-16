package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.containers.UniqueList;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class HelpCommand extends Command {
    public HelpCommand() {
        super(new UniqueList<>("help", "what", "wtf", "?"),
                "Shows the help menu",
                "Opens up the help menu contains all the commands with descriptions and syntax",
                new CommandParameter("command", "A command you need help with", "If you need a help with some command (for example, you don't know the argument names or the command purpose), just add a command name to display the help", false)
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        if(arguments.has("command")) {
            if(loop.hasCommand(arguments.get("command"))) {
                var command = loop.getCommand(arguments.get("command"));
                logger.info("Command %s's help".formatted(command.getPrimaryName()));
                logger.info(command.getLongDescription());
                if(command.hasAliases()) {
                    logger.info("You can also use %s name%s instead: %s".formatted(command.aliasCount() == 1 ? "this" : "these", command.aliasCount() == 1 ? "" : "s", Lists.join(command.getAliases())));
                }
                logger.info("Syntax: " + command.getSyntax());

                if(command.getParameters().count() != 0) { // TODO: Change to !.isEmpty()
                    logger.info("Parameters:");
                    for(var parameter : command.getParameters()) {
                        logger.info("\t[%s] %s - %s".formatted(parameter.required() ? "Required" : "Optional", quoted(parameter.name()), parameter.longDescription()));
                    }
                }
            } else {
                error("There is no command with name %s. To open the list of existing commands, write %s command with no arguments.".formatted(quoted(arguments.get("command")), quoted(this.getPrimaryName())));
            }
        } else {
            for(var command : loop.getCommands()) {
                logger.info(command.getShortDescription());
                logger.info(command.getSyntax());
                for(var parameter : command.getParameters()) {
                    logger.info("\t[%s] %s - %s".formatted(parameter.required() ? "Required" : "Optional", quoted(parameter.name()), parameter.longDescription()));
                }

                logger.info("");
            }
        }
    }
}
