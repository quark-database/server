package ru.anafro.quark.server.console;

import ru.anafro.quark.server.console.exceptions.CommandRuntimeException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.logging.LoggingLevel;
import ru.anafro.quark.server.plugins.events.BeforeRunCommandEvent;
import ru.anafro.quark.server.plugins.events.CommandFinishedEvent;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Command {
    protected final CommandLoop loop;
    protected final Set<String> names;
    protected final CommandParameters parameters;
    protected final Logger logger;
    private final String primaryName;
    private final String shortDescription;
    private final String longDescription;

    public Command(List<String> names, String shortDescription, String longDescription, CommandParameters parameters) {
        this.loop = Quark.commandLoop();
        this.names = new HashSet<>(names);
        this.primaryName = names.getFirst();
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.parameters = parameters;
        this.logger = new Logger(loop.getCommandPrefix() + primaryName);

        logger.logFrom(LoggingLevel.DEBUG);
    }

    public Command(List<String> names, String shortDescription, String longDescription, CommandParameter... parameters) {
        this(names, shortDescription, longDescription, new CommandParameters(parameters));
    }

    public boolean named(String name) {
        return names.contains(name.toLowerCase());
    }

    public CommandParameters getParameters() {
        return parameters;
    }

    public boolean hasParameters() {
        return !parameters.isEmpty();
    }

    public Set<String> getNames() {
        return names;
    }

    public abstract void action(CommandArguments arguments);

    public void run(CommandArguments arguments) {
        Quark.fireEvent(new BeforeRunCommandEvent(this, arguments));

        parameters.ensureArgumentsAreValid(arguments);
        action(arguments);

        Quark.fireEvent(new CommandFinishedEvent(this, arguments));
    }

    public boolean hasAliases() {
        return names.size() > 1;
    }

    public String getPrimaryName() {
        return primaryName;
    }

    public List<String> getAliases() {
        return names.stream().toList().subList(1, names.size());
    }

    public int aliasCount() {
        return names.size() - 1;
    }

    protected void error(String message) {
        throw new CommandRuntimeException(message);
    }

    public Logger getLogger() {
        return logger;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getSyntax() {
        TextBuffer syntax = new TextBuffer(getPrimaryName());

        for (var parameter : parameters) {
            syntax.append(" ");
            syntax.append(parameter.name());
            syntax.append(" ");
            syntax.append(parameter.isRequired() ? "[" : "<");
            syntax.append(parameter.shortDescription());
            syntax.append(parameter.isRequired() ? "]" : ">");
        }

        return syntax.extractContent();
    }

    @Override
    public String toString() {
        return STR."\{loop.getCommandPrefix()}\{primaryName}";
    }
}
