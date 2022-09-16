package ru.anafro.quark.server.console;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.exceptions.CommandException;
import ru.anafro.quark.server.console.parser.CommandParser;
import ru.anafro.quark.server.multithreading.AsyncService;
import ru.anafro.quark.server.multithreading.Threads;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.strings.StringSimilarityFinder;

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
public class CommandLoop implements AsyncService {

    /**
     * The delay in seconds to wait before running command loops.
     * @since Quark 1.1
     */
    private static final float DELAY_BEFORE_RUNNING_SECONDS = 0.6F;

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
        Threads.freezeFor(DELAY_BEFORE_RUNNING_SECONDS);

        while(!isReadingNextCommandsStopped()) {
            try {
                System.out.print("> ");
                Quark.runCommand(scanner.nextLine().strip());
            } catch(CommandException exception) {
                Quark.logger().error(exception.getMessage());
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
