package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.databases.ql.types.EntityType;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class ModifiersCommand extends Command {
    public ModifiersCommand() {
        super(new UniqueList<>("modifiers", "lm"), "Shows all the modifiers", "Shows all the column modifiers provided by Quark");
    }

    @Override
    public void action(CommandArguments arguments) {
        for(var modifier : Quark.modifiers()) {
            logger.info("%s for types %s. Application priority: %s".formatted(modifier.getName(), modifier.isAnyTypeAllowed() ? "[any type]" : Lists.join(modifier.getAllowedTypes().stream().map(EntityType::getName).toList()), modifier.getApplicationPriority()));
        }
    }
}
