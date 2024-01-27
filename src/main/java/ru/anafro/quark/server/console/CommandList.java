package ru.anafro.quark.server.console;

import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.patterns.NamedObjectsList;
import ru.anafro.quark.server.utils.strings.StringSimilarityFinder;

public class CommandList extends NamedObjectsList<Command> {
    @Override
    protected String getNameOf(Command command) {
        return command.getPrimaryName();
    }

    public String suggestName(String notExistingCommandName) {
        if (isEmpty()) {
            return null;
        }

        var suggestedCommandName = getFirst().getPrimaryName();
        var similarity = Double.NEGATIVE_INFINITY;

        for (var command : Quark.commands()) {
            for (var commandName : command.getNames()) {
                var currentSimilarity = StringSimilarityFinder.findSimilarity(notExistingCommandName, commandName);

                if (similarity <= currentSimilarity) {
                    suggestedCommandName = commandName;
                    similarity = currentSimilarity;
                }
            }
        }

        return suggestedCommandName;
    }

    @Override
    public Command get(String name) {
        for (var command : elements) {
            if (command.named(name)) {
                return command;
            }
        }

        return null;
    }

    @Override
    public boolean has(String name) {
        return elements.stream().anyMatch(command -> command.named(name));
    }
}
