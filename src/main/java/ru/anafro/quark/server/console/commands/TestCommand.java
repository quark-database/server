package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.console.exceptions.CommandRuntimeException;
import ru.anafro.quark.server.databases.data.parser.RecordParser;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class TestCommand extends Command {
    public TestCommand() {
        super(new UniqueList<>("test"), "Runs the small developer tests", "Runs the small developers tests for Quark developers.", new CommandParameter("for", "The test name", "A name of the test to be run"), new CommandParameter("data", "The test data", "A data for testing code. The usage of data passed differs from test to test.", false));
    }

    @Override
    public void action(CommandArguments arguments) {
        switch(arguments.get("for")) {
            case "record-parse" -> {
                if(!arguments.has("data")) {
                    error("Record needed");
                }

                var parser = new RecordParser();
                parser.parse(arguments.get("data"));

                logger.info(parser.getRecord().toString());
            }
            case "command-arg" -> {
                if(arguments.has("data")) {
                    logger.info(arguments.get("data"));
                } else {
                    logger.info("<data argument is not present>");
                }
            }
            default -> throw new CommandRuntimeException("No such test named %s.".formatted(arguments.get("for")));
        }
    }
}
