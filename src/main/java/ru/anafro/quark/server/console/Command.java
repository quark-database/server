package ru.anafro.quark.server.console;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.exceptions.CommandRuntimeException;
import ru.anafro.quark.server.logging.LogLevel;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.utils.containers.UniqueList;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.List;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public abstract class Command {
    protected final CommandLoop loop;
    protected final UniqueList<String> names;
    private final String shortDescription;
    private final String longDescription;
    protected final CommandParameters parameters;
    protected final Logger logger;

    public Command(UniqueList<String> names, String shortDescription, String longDescription, CommandParameters parameters) {
        this.loop = Quark.commandLoop();
        this.names = names;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.parameters = parameters;
        this.logger = new Logger("Command " + quoted(this.getPrimaryName()));

        logger.logFrom(LogLevel.DEBUG);
    }

    public Command(UniqueList<String> names, String shortDescription, String longDescription, CommandParameter... parameters) {
        this(names, shortDescription, longDescription, new CommandParameters(parameters));
    }

    public boolean named(String name) {
        return names.contains(name.toLowerCase());
    }

    public CommandParameters getParameters() {
        return parameters;
    }

    public UniqueList<String> getNames() {
        return names;
    }

    public abstract void action(CommandArguments arguments);

    public void run(CommandArguments arguments) {
        parameters.checkArgumentsValidity(arguments);

        action(arguments);
    }

    public boolean hasAliases() {
        return names.size() > 1;
    }

    public String getPrimaryName() {
        return names.get(0);
    }

    public List<String> getAliases() {
        return names.asList().subList(1, names.size());
    }

    public int aliasCount() {
        return names.size() - 1;
    }

    public CommandLoop getLoop() {
        return loop;
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

        for(var parameter : parameters) {
            syntax.append(" ");
            syntax.append(parameter.name());
            syntax.append(" ");
            syntax.append(parameter.required() ? "[" : "<");
            syntax.append(parameter.shortDescription());
            syntax.append(parameter.required() ? "]" : ">");
        }

        return syntax.extractContent();
    }
}
