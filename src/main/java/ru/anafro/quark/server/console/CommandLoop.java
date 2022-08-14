package ru.anafro.quark.server.console;

import ru.anafro.quark.server.console.exceptions.CommandException;
import ru.anafro.quark.server.console.exceptions.CommandWithThisNameAlreadyExistsException;
import ru.anafro.quark.server.console.exceptions.NoSuchCommandException;
import ru.anafro.quark.server.console.parser.CommandParser;
import ru.anafro.quark.server.networking.Server;

import java.util.ArrayList;
import java.util.Scanner;

public class CommandLoop {
    private final ArrayList<Command> commands = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private final CommandParser parser = new CommandParser();
    private final Server server;
    private boolean isReadingNextCommandsStopped;

    public CommandLoop(Server server) {
        this.server = server;
    }

    public void startReadingCommandsAsync() {
        while(!isReadingNextCommandsStopped()) {
            System.out.print("> ");

            try {
                parser.parse(scanner.nextLine().strip());

                String commandName = parser.getCommandName();
                CommandArguments arguments = parser.getArguments();

                if(hasCommand(commandName)) {
                    Command command = getCommand(commandName);
                    command.run(arguments);
                    System.out.println();
                } else {
                    throw new NoSuchCommandException(commandName);
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
}
