package ru.anafro.quark.server.console;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.exceptions.CommandException;
import ru.anafro.quark.server.console.parser.CommandParser;
import ru.anafro.quark.server.multithreading.AsyncService;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.strings.StringSimilarityFinder;

import java.util.Scanner;

public class CommandLoop implements AsyncService {
    private final Scanner scanner = new Scanner(System.in);
    private final CommandParser parser = new CommandParser();
    private final Server server;
    private boolean isReadingNextCommandsStopped;

    public CommandLoop(Server server) {
        this.server = server;
    }

    public void startReadingCommands() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            //
        }

        while(!isReadingNextCommandsStopped()) {
            try {
                System.out.print("> ");
                Quark.runCommand(scanner.nextLine().strip());
            } catch(CommandException exception) {
                Quark.logger().error(exception.getMessage());
            }
        }
    }

    public Server getServer() {
        return server;
    }

    public boolean isReadingNextCommandsStopped() {
        return isReadingNextCommandsStopped;
    }

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

    public void stopReadingNextCommands() {
        this.isReadingNextCommandsStopped = true;
    }

    public CommandParser getParser() {
        return parser;
    }

    @Override
    public void run() {
        startReadingCommands();
    }
}
