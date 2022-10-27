package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.console.CommandScriptFile;

public class CommandScriptFileCannotBeReadException extends CommandException {
    public CommandScriptFileCannotBeReadException(CommandScriptFile scriptFile, Throwable becauseOf) {
        super("A command script file '%s' cannot be read, because of %s".formatted(
                scriptFile.getPath(),
                becauseOf.toString()
        ));
        initCause(becauseOf);
    }
}
