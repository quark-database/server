package ru.anafro.quark.server.scheduling;

import ru.anafro.quark.server.multithreading.AsyncService;
import ru.anafro.quark.server.multithreading.Threads;

public abstract class ScheduledTask extends AsyncService {
    private final long period;

    public ScheduledTask(String taskName, long period) {
        super("scheduled-task: " + taskName);
        this.period = period;
    }

    @Override
    public void run() {
        while(true) {
            Threads.freezeFor(period / 1000.0);
            action();
        }
    }

    public abstract void action();

    public long getPeriod() {
        return period;
    }
}
