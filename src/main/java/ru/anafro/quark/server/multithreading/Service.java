package ru.anafro.quark.server.multithreading;

import ru.anafro.quark.server.utils.types.classes.Classes;

public abstract class Service {
    public abstract void start();

    public abstract void stop();

    public String getName() {
        return Classes.getHumanReadableClassName(this);
    }
}
