package ru.anafro.quark.server.scheduling;

import ru.anafro.quark.server.multithreading.Service;
import ru.anafro.quark.server.multithreading.Threads;
import ru.anafro.quark.server.utils.time.TimeSpan;

public abstract class Repeater extends Service {
    private final TimeSpan interval;
    private boolean isRunning = false;

    public Repeater(TimeSpan interval) {
        this.interval = interval;
    }

    @Override
    public void start() {
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
