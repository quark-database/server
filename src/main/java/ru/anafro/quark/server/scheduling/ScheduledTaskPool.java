package ru.anafro.quark.server.scheduling;

import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.ql.entities.LongEntity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.Iterator;
import java.util.List;

public class ScheduledTaskPool implements Iterable<ScheduledTask> {
    private final List<ScheduledTask> tasks = Lists.empty();

    public ScheduledTaskPool(ScheduledTask... tasks) {
        this.tasks.addAll(List.of(tasks));
    }

    public List<ScheduledTask> getTasks() {
        return tasks;
    }

    public void load() {
        Table.ensureExists("Quark.Scheduled Queries");
        Table.ensureExists("Quark.Scheduled Commands");

        var queries = Table.byName("Quark.Scheduled Queries").loadRecords();
        var commands = Table.byName("Quark.Scheduled Commands").loadRecords();

        for(var query : queries) {
            tasks.add(new InstructionScheduledTask(
                    ((StringEntity) query.getField("query").getValue()).getString(),
                    ((LongEntity) query.getField("period").getValue()).getLong()
            ));
        }

        for(var command : commands) {
            tasks.add(new CommandScheduledTask(
                    ((StringEntity) command.getField("command").getValue()).getString(),
                    ((LongEntity) command.getField("period").getValue()).getLong()
            ));
        }
    }

    @Override
    public Iterator<ScheduledTask> iterator() {
        return tasks.iterator();
    }
}
