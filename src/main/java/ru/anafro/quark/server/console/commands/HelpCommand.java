package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.strings.English;

import static ru.anafro.quark.server.console.CommandParameter.optional;
import static ru.anafro.quark.server.utils.collections.Collections.list;
import static ru.anafro.quark.server.utils.collections.Lists.join;

public class HelpCommand extends Command {
    public HelpCommand() {
        super(list("help", "what", "wtf", "?"),
                "Shows the help menu",
                "Opens up the help menu contains all the commands with descriptions and syntax",
                optional("command", "A command you need help with", "If you need a help with some command (for example, you don't know the argument names or the command purpose), just add a command name to display the help"),
                optional("constructor", "A constructor you need help with", "If you need a help with some constructor. just add the constructor name")
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        if (arguments.has("command")) {
            var commandName = arguments.get("command");

            if (Quark.commands().doesntHave(commandName)) {
                error(STR."There is no command with name \{commandName}. Type \{this} to see list of commands.");
            }

            var command = Quark.commands().get(commandName);
            var syntax = command.getSyntax();

            logger.info(STR."Command \{command.getPrimaryName()}'s help");
            logger.info(command.getLongDescription());

            if (command.hasAliases()) {
                var aliases = join(command.getAliases());
                var pronoun = English.demonstrativePronoun(aliasCount());
                var names = English.pluralize("name", aliasCount());

                logger.info(STR."You can also use \{pronoun} \{names} instead: \{aliases}");
            }

            logger.info(STR."Syntax: \{syntax}");

            if (command.hasParameters()) {
                logger.info("Parameters: ");
                for (var parameter : command.getParameters()) {
                    logger.info(STR."\t\{parameter}");
                }
            }

            return;
        }

        if (arguments.has("constructor")) {
            var constructors = Quark.constructors();
            var constructorName = arguments.get("constructor");

            if (constructors.doesntHave(constructorName)) {
                error(STR."There is no construtor '\{constructorName}'.");
            }

            var constructor = constructors.get(constructorName);
            logger.info(constructor.getSyntax());

            return;
        }


        synchronized (Quark.commands().asList()) {
            for (var command : Quark.commands()) {
                var commandName = loop.getCommandPrefix() + command.getPrimaryName();
                var commandDescription = command.getShortDescription();

                logger.info(STR."\{commandName}: \{commandDescription}");

                for (var parameter : command.getParameters()) {
                    logger.info(STR."\t\{parameter}");
                }

                logger.info("");
                logger.info("");
                logger.info("");
            }
        }
    }
}
