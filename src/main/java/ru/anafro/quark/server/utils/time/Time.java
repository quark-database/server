package ru.anafro.quark.server.utils.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Time {
    private Time() {
    }

    public static String clock() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }
}
