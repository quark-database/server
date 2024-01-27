package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.facade.Quark;

import static ru.anafro.quark.server.console.CommandParameter.required;
import static ru.anafro.quark.server.utils.collections.Collections.list;

public class ListCommand extends Command {
    public ListCommand() {
        super(
                list("list"),
                "Lists objects",
                "Lists objects of type you specify",
                required("of", "Object type", "Type of objects you want to specify")
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        if (arguments.doesntHave("of")) {
            var commandSyntax = this.getSyntax();
            error(STR."Specify the list type: \{commandSyntax}");
        }

        switch (arguments.getString("of")) {
            case "constructors" -> logConstructors();
            case "instructions" -> logInstructions();
            case "commands" -> logCommands();

            default -> error("Unknown list type.");
        }
    }

    private void logConstructors() {
        for (var constructor : Quark.constructors()) {
            logger.info(constructor.getName());
        }
    }

    private void logInstructions() {
        for (var instruction : Quark.instructions()) {
            logger.info(instruction.getName());
        }
    }

    private void logCommands() {
        for (var command : Quark.commands()) {
            logger.info(command.getPrimaryName());
        }
    }
}
