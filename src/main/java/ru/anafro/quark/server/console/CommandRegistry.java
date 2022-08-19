package ru.anafro.quark.server.console;

import ru.anafro.quark.server.utils.patterns.NamedObjectsRegistry;

public class CommandRegistry extends NamedObjectsRegistry<Command> {
    @Override
    protected String getNameOf(Command command) {
        return command.getPrimaryName();
    }

    @Override
    public Command get(String name) {
        for(var command : registeredObjects) {
            if(command.named(name)) {
                return command;
            }
        }

        return null;
    }

    @Override
    public boolean has(String name) {
        return registeredObjects.stream().anyMatch(command -> command.named(name));
    }
}
