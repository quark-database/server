package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class ListScheduledTasksCommand extends Command {
    public ListScheduledTasksCommand() {
        super(
                new UniqueList<>(
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
            logger.info("Scheduled query: %s with period %d milliseconds".formatted(
                    record.getField("query").getValue().valueAs(String.class),
                    record.getField("period").getValue().valueAs(Long.class)
            ));
        });

        commands.forEach(record -> {
            logger.info("Scheduled command: %s with period %d milliseconds".formatted(
                    record.getField("command").getValue().valueAs(String.class),
                    record.getField("period").getValue().valueAs(Long.class)
            ));
        });

        logger.info("%d tasks in total: %d queries and %d commands.".formatted(
                queries.count() + commands.count(),
                queries.count(),
                commands.count()
        ));
    }
}
