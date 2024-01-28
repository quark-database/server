package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.language.types.EntityType;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.collections.Lists;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class ModifiersCommand extends Command {
    public ModifiersCommand() {
        super(list("modifiers", "lm"), "Shows all the modifiers", "Shows all the column modifiers provided by Quark");
    }

    @Override
    public void action(CommandArguments arguments) {
        for (var modifier : Quark.modifiers()) {
            var name = modifier.getName();
            var types = modifier.isAnyTypeAllowed() ? "(Any type)" : Lists.join(modifier.getAllowedTypes(), EntityType::getName);
            var priority = modifier.getApplicationPriority();

            logger.info(STR."\{name} for types \{types}. Application priority: \{priority}");
        }
    }
}
