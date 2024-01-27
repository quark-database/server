package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.strings.English;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class CleanModeCommand extends Command {
    public CleanModeCommand() {
        super(
                list(
                        "clean-mode",
                        "auto-clean",
                        "clear-mode",
                        "auto-clear",
                        "acl"
                ),
                "Enables/Disables the Clean Mode",
                "When Clean mode is enabled, the console is cleared after each command"
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        Quark.commandLoop().toggleCleanMode();
        logger.info(STR."Clean mode is switched \{English.onOrOff(Quark.commandLoop().isCleanModeOn())}");
    }
}
