package ru.anafro.quark.server.scheduling;

import ru.anafro.quark.server.multithreading.AsyncService;
import ru.anafro.quark.server.multithreading.Threads;

public abstract class ScheduledTask extends AsyncService {
    private final long period;

    public ScheduledTask(String taskName, TimeSpan interval) {
        super("scheduled-task: " + taskName);
        this.period = period;
    }

    @Override
    public final void run() {
        while(true) {
            Threads.freezeFor(interval);
            action();
        }
    }

    public abstract void action();

    public long getPeriod() {
        return period;
    }
}
