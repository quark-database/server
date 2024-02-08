package ru.anafro.quark.server.multithreading.exceptions;

import ru.anafro.quark.server.utils.exceptions.UtilityException;

public class ThreadInterruptionException extends UtilityException {
    public ThreadInterruptionException(Thread thread, InterruptedException cause) {
        super(STR."Thread \{thread.getName()} is interrupted.", cause);
    }
}
