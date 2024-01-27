package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.database.data.Table;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class ListScheduledTasksCommand extends Command {
    public ListScheduledTasksCommand() {
        super(
                list(
                        "list-scheduled-tasks",
                        "scheduled-list",
                        "task-list",
                        "sch",
                        "delayed-tasks",
                        "scheduled-tasks",
                        "scheduled-task-list",
                        "scheduled-tasks-list",
                        "show-tasks",
                        "show-scheduled",
                        "delayed-tasks-list"
                ),
                "Lists all the scheduled queries and commands.",
                "Shows the list of all the scheduled tasks: queries and commands."

        );
    }

    @Override
    public void action(CommandArguments arguments) {
        var queries = Table.byName("Quark.Scheduled Queries").loadRecords();
        var commands = Table.byName("Quark.Scheduled Commands").loadRecords();

        queries.forEach(record -> {
            var query = record.getString("query");
            var period = record.getLong("period");

            logger.info(STR."Scheduled query: \{query} with period \{period} milliseconds");
        });

        commands.forEach(record -> {
            var command = record.getString("command");
            var period = record.getLong("period");

            logger.info(STR."Scheduled command: \{command} with period \{period} milliseconds");
        });

        var queriesCount = queries.count();
        var commandsCount = commands.count();

        logger.info(STR."\{queriesCount + commandsCount} tasks in total: \{queriesCount} queries and \{commandsCount} commands.");
    }
}
