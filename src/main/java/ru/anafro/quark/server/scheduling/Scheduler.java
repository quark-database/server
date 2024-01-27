package ru.anafro.quark.server.scheduling;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.language.entities.LongEntity;
import ru.anafro.quark.server.database.language.entities.StringEntity;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.time.TimeSpan;

import java.util.Iterator;
import java.util.List;

public class Scheduler implements Iterable<ScheduledTask> {
    private final List<ScheduledTask> tasks = Lists.empty();

    public Scheduler(ScheduledTask... tasks) {
        this.tasks.addAll(List.of(tasks));
    }

    public void load() {
        Table.ensureExists("Quark.Scheduled Queries");
        Table.ensureExists("Quark.Scheduled Commands");

        var queries = Table.byName("Quark.Scheduled Queries").loadRecords();
        var commands = Table.byName("Quark.Scheduled Commands").loadRecords();

        for (var query : queries) {
            tasks.add(new InstructionScheduledTask(
                    ((StringEntity) query.getField("query").getEntity()).getString(),
                    TimeSpan.milliseconds(((LongEntity) query.getField("period").getEntity()).getLong())
            ));
        }

        for (var command : commands) {
            tasks.add(new CommandScheduledTask(
                    ((StringEntity) command.getField("command").getEntity()).getString(),
                    TimeSpan.milliseconds(((LongEntity) command.getField("period").getEntity()).getLong())
            ));
        }
    }

    @NotNull
    @Override
    public Iterator<ScheduledTask> iterator() {
        return tasks.iterator();
    }
}
