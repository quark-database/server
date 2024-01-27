package ru.anafro.quark.server.scheduling;

import ru.anafro.quark.server.multithreading.Threads;
import ru.anafro.quark.server.utils.time.TimeSpan;

public abstract class ScheduledTask implements Runnable {
    private final TimeSpan interval;
    private boolean isRunning = false;

    public ScheduledTask(TimeSpan interval) {
        this.interval = interval;
    }

    @Override
    public void run() {
        this.isRunning = true;

        while (this.isRunning) {
            Threads.sleepFor(interval);
            this.performAction();
        }
    }

    public abstract void performAction();

    @SuppressWarnings("unused")
    public void stop() {
        this.isRunning = false;
    }
}
