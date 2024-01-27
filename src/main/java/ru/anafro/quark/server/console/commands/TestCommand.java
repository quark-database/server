package ru.anafro.quark.server.console.commands;

import org.jetbrains.annotations.TestOnly;
import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.console.exceptions.CommandRuntimeException;
import ru.anafro.quark.server.database.data.parser.RecordParser;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class TestCommand extends Command {
    @TestOnly
    public TestCommand() {
        super(list("test"), "Runs the small developer tests", "Runs the small developers tests for Quark developers.", new CommandParameter("for", "The test name", "A name of the test to be run"), new CommandParameter("json", "The test json", "A json for testing code. The usage of json passed differs from test to test.", false));
    }

    @Override
    public void action(CommandArguments arguments) {
        switch (arguments.get("for")) {
            case "record-parse" -> {
                if (!arguments.has("json")) {
                    error("Record needed");
                }

                var parser = new RecordParser();
                parser.parse(arguments.get("json"));

                logger.info(parser.getRecord().toString());
            }
            case "command-arg" -> {
                if (arguments.has("json")) {
                    logger.info(arguments.get("json"));
                } else {
                    logger.info("<json argument is not present>");
                }
            }
            default -> throw new CommandRuntimeException("No such test named %s.".formatted(arguments.get("for")));
        }
    }
}
