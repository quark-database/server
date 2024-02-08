package ru.anafro.quark.server.scheduling;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.multithreading.Service;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.time.TimeSpan;

import java.util.Iterator;
import java.util.List;

import static ru.anafro.quark.server.database.data.Table.systemTable;

public class ServiceLoader implements Iterable<Service> {
    private final List<Service> services = Lists.empty();

    public void load() {
        for (var query : systemTable("Scheduled Queries").all()) {
            services.add(new InstructionRepeater(
                    query.getString("query"),
                    TimeSpan.milliseconds(query.getLong("period")
                    )));
        }

        for (var command : systemTable("Scheduled Commands").all()) {
            services.add(new CommandRepeater(
                    command.getString("command"),
                    TimeSpan.milliseconds(command.getLong("period")
                    )));
        }
    }

    @NotNull
    @Override
    public Iterator<Service> iterator() {
        return services.iterator();
    }
}
