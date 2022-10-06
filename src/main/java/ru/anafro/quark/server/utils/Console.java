package ru.anafro.quark.server.utils;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

public final class Console {
    private Console() {
        throw new CallingUtilityConstructorException(getClass());
    }

    public static void synchronizedPrintln(String message) {
        synchronized(System.out) {
            System.out.println(message);
        }
    }

    public static void synchronizedPrint(String message) {
        synchronized(System.out) {
            System.out.print(message);
        }
    }
}

