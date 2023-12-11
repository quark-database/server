package ru.anafro.quark.server.console;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.exceptions.CommandException;
import ru.anafro.quark.server.console.parser.CommandParser;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.multithreading.AsyncService;
import ru.anafro.quark.server.multithreading.Threads;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.strings.StringSimilarityFinder;
import ru.anafro.quark.server.utils.time.TimeSpan;

import java.util.Scanner;

/**
 * Represents the command loop reading commands
 * from the console line. To get default command loop of Quark,
 * use: {@code Quark.commandLoop();}
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class CommandLoop extends AsyncService {

    /**
     * The delay in seconds to wait before running command loops.
     * @since Quark 1.1
     */
    private static final TimeSpan DELAY_BEFORE_RUNNING = TimeSpan.milliseconds(600);

    /**
     * The scanner that receives input from the console line.
     * @since Quark 1.1
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * The parser that handles commands received.
     * @since Quark 1.1
     */
    private final CommandParser parser = new CommandParser();

    /**
     * The server that commands can use inside their actions.
     * @deprecated This field will be removed soon.
     *             Use {@code Quark.server()} instead.
     * @since Quark 1.1
     */
    @Deprecated
    private final Server server;

    /**
     * Set this field to {@code true} to make the current
     * command reading the last one for this loop.
     * @since Quark 1.1
     */
    private boolean isReadingNextCommandsStopped;

    private final String commandPrefix = "/";

    /**
     * Creates a new command loop. It does not run by default.
     * To run it, use {@link CommandLoop#run()}.
     *
     * @param server the server.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     *
     * @deprecated The {@code server} argument is going to be removed.
     */
    @Deprecated
    public CommandLoop(Server server) {
        super("command-loop");
        this.server = server;
    }

    /**
     * It waits for 600 milliseconds, then it starts
     * reading commands from the console line.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public void startReadingCommands() {
        Threads.freezeFor(DELAY_BEFORE_RUNNING);

        while(!isReadingNextCommandsStopped()) {
            Console.synchronizedPrint("Quark Server > ");
            var input = scanner.nextLine().strip();

            if(input.startsWith(commandPrefix)) {
                try {
                    Quark.runCommand(input.substring(commandPrefix.length()));
                } catch(CommandException exception) {
                    Quark.logger().error(exception.getMessage());
                }
            } else if(!input.isBlank()) {
                try {
                    Quark.info(Quark.runInstruction(input).toString());
                } catch(QuarkException exception) {
                    Quark.error(exception.getMessage());
                }
            }
        }
    }

    /**
     * @return the server of this command loop.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     *
     * @deprecated This getter will be removed soon.
     *             Use {@code Quark.server()} instead.
     */
    @Deprecated
    public Server getServer() {
        return server;
    }

    /**
     * @return {@code true} if reading the next commands is stopped.
     *         Otherwise, this method returns {@code false}.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public boolean isReadingNextCommandsStopped() {
        return isReadingNextCommandsStopped;
    }

    /**
     * It finds the most similar command name to the one that was passed in
     * 
     * @param notExistingCommandName the command name that doesn't exist.
     * @return the name of the command that is most similar to the command that was not found.
     *
     * @since Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     *
     * @deprecated Use {@link CommandRegistry#suggest(String)} from {@link Quark#commands()} instead.
     */
    @Deprecated
    public String suggestCommand(String notExistingCommandName) {
        if(Quark.commands().count() == 0) {
            return null;
        }

        String suggestedCommand = Quark.commands().asList().get(0).getPrimaryName();
        double similarity = Double.NEGATIVE_INFINITY;

        for(var command : Quark.commands()) {
            for(var commandName : command.getNames()) {
                double currentSimilarity = StringSimilarityFinder.findSimilarity(notExistingCommandName, commandName);

                if(similarity <= currentSimilarity) {
                    suggestedCommand = commandName;
                    similarity = currentSimilarity;
                }
            }
        }

        return suggestedCommand;
    }

    /**
     * Stops reading the next commands. It means that
     * the command loop will not stop reading current command,
     * but it will stop reading the next ones.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public void stopReadingNextCommands() {
        this.isReadingNextCommandsStopped = true;
    }

    /**
     * @return the parser that this command loop uses
     *         to parse read commands.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public CommandParser getParser() {
        return parser;
    }

    /**
     * Runs reading commands. This method is an alias
     * for {@link CommandLoop#startReadingCommands()}.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void run() {
        startReadingCommands();
    }
}
