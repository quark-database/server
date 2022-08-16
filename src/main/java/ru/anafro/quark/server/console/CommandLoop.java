package ru.anafro.quark.server.console;

import ru.anafro.quark.server.console.exceptions.CommandException;
import ru.anafro.quark.server.console.exceptions.CommandWithThisNameAlreadyExistsException;
import ru.anafro.quark.server.console.exceptions.NoSuchCommandException;
import ru.anafro.quark.server.console.parser.CommandParser;
import ru.anafro.quark.server.multithreading.AsyncService;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.strings.StringSimilarityFinder;

import java.util.ArrayList;
import java.util.Scanner;

public class CommandLoop implements AsyncService {
    private final ArrayList<Command> commands = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private final CommandParser parser = new CommandParser();
    private final Server server;
    private boolean isReadingNextCommandsStopped;

    public CommandLoop(Server server) {
        this.server = server;
    }

    public void startReadingCommands() {
        while(!isReadingNextCommandsStopped()) {
            System.out.print("> ");

            try {
                String commandLine = scanner.nextLine().strip();

                if(commandLine.isBlank()) {
                    continue;
                }

                parser.parse(commandLine);

                String commandName = parser.getCommandName();
                CommandArguments arguments = parser.getArguments();

                if(hasCommand(commandName)) {
                    Command command = getCommand(commandName);
                    command.run(arguments);
                    System.out.println();
                } else {
                    throw new NoSuchCommandException(this, commandName);
                }
            } catch(CommandException exception) {
                System.out.println(exception.getMessage());
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
        if(commands.isEmpty()) {
            return null;
        }

        String suggestedCommand = commands.get(0).getPrimaryName();
        double similarity = Double.NEGATIVE_INFINITY;

        for(var command : commands) {
            for(var commandName : command.getNames()) {
                if(similarity <= StringSimilarityFinder.findSimilarity(notExistingCommandName, commandName)) { // TODO: Repeated code
                    suggestedCommand = commandName;
                    similarity = StringSimilarityFinder.findSimilarity(notExistingCommandName, commandName);   // TODO: Repeated code
                }
            }
        }

        return suggestedCommand;
    }

    public void stopReadingNextCommands() {
        this.isReadingNextCommandsStopped = true;
    }

    public void registerCommand(Command registeringCommand) {
        for(var registeringCommandName : registeringCommand.getNames()) {
            for(var existingCommand : commands) {
                if(existingCommand.named(registeringCommandName)) {
                    throw new CommandWithThisNameAlreadyExistsException(registeringCommandName);
                }
            }
        }

        commands.add(registeringCommand);
    }

    public void registerCommand(CommandBuilder builder) {
        registerCommand(builder.build());
    }

    public Command getCommand(String commandName) {
        for(var command : commands) {
            if(command.named(commandName)) {
                return command;
            }
        }

        return null;
    }

    public boolean hasCommand(String commandName) {
        return getCommand(commandName) != null;
    }

    public CommandParser getParser() {
        return parser;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    @Override
    public void run() {
        startReadingCommands();
    }
}
