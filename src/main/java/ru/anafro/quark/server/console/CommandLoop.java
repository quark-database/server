package ru.anafro.quark.server.console;

import ru.anafro.quark.server.console.parser.CommandParser;
import ru.anafro.quark.server.entertainment.Greeter;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.language.Query;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.utils.strings.Strings;
import ru.anafro.quark.server.utils.types.classes.Enums;

import java.util.Optional;

import static ru.anafro.quark.server.language.ResponseStatus.OK;
import static ru.anafro.quark.server.logging.LoggingLevel.ERROR;
import static ru.anafro.quark.server.logging.LoggingLevel.INFO;

/**
 * Represents the command loop reading commands
 * from the console line. To get default command loop of Quark,
 * use: {@code Quark.commandLoop();}
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class CommandLoop implements Runnable {
    /**
     * The parser that handles commands received.
     *
     * @since Quark 1.1
     */
    private final CommandParser parser = new CommandParser();
    private final String commandPrefix = "/";
    private final Logger logger = new Logger(getClass());
    private String failedCommand = null;
    private CommandArguments failedArguments = null;
    private boolean isRunning = true;
    private boolean isCleanModeOn = false;

    /**
     * @return the parser that this command loop uses
     * to parse read commands.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public CommandParser getParser() {
        return parser;
    }

    /**
     * Starts reading commands from the console line.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    public synchronized void run() {
        while (Quark.server().isClosed()) {
            Thread.yield();
        }

        Greeter.greet();
        while (this.isRunning && Console.hasNextLine()) {
            var prompt = Console.prompt(Quark.NAME).transform(this::preparePrompt);

            try {
                if (isCleanModeOn) {
                    Console.clear();
                }

                if (isCommand(prompt)) {
                    var commandPrompt = prepareCommand(prompt);
                    var parser = Quark.commandLoop().getParser();
                    var command = parser.parse(commandPrompt);
                    var arguments = parser.getArguments();

                    command.run(arguments);
                    forgetFailedCommand();
                }

                if (isQuery(prompt)) {
                    var query = Query.make(prompt);
                    var result = query.execute();
                    var instruction = query.instruction();
                    var arguments = query.arguments();
                    var status = result.responseStatus();
                    var message = result.message();
                    var view = result.tableView();
                    var time = result.milliseconds();

                    logger.log(status == OK ? INFO : ERROR, STR."""
                            Executed a query:
                            \{instruction.format(arguments)}

                            \{Enums.getDisplayName(status)}: \{message}
                            \{view}

                            Done in \{time} ms
                            """);
                }
            } catch (QuarkException exception) {
                Quark.error(exception.getMessage());
            }
        }
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }

    private String preparePrompt(String prompt) {
        return prompt.strip();
    }

    private String prepareCommand(String prompt) {
        return Strings.removeLeading(prompt, commandPrefix);
    }

    private boolean isCommand(String prompt) {
        return prompt.startsWith(commandPrefix);
    }

    private boolean isQuery(String prompt) {
        return !isCommand(prompt) && !prompt.isBlank();
    }

    public Optional<String> tryGetFailedCommand() {
        return Optional.ofNullable(failedCommand);
    }

    public void setFailedCommand(String failedCommand) {
        this.failedCommand = failedCommand;
    }

    public void forgetFailedCommand() {
        this.failedCommand = null;
        this.failedArguments = null;
    }

    public CommandArguments getFailedArguments() {
        return failedArguments;
    }

    public void setFailedArguments(CommandArguments failedArguments) {
        this.failedArguments = failedArguments;
    }

    public void toggleCleanMode() {
        isCleanModeOn = !isCleanModeOn;
    }

    public boolean isCleanModeOn() {
        return isCleanModeOn;
    }

    public void stop() {
        this.isRunning = false;
    }
}
