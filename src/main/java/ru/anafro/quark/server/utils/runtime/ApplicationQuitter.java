package ru.anafro.quark.server.utils.runtime;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

public final class ApplicationQuitter {
    public enum ApplicationQuitterStatus {
        STOP(0), RELOAD(1), CRASH(-1);

        private final int code;

        ApplicationQuitterStatus(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    private ApplicationQuitter() {
        throw new CallingUtilityConstructorException(getClass());
    }

    public static void quit(ApplicationQuitterStatus status) {
        System.exit(status.getCode());
    }
}
