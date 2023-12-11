package ru.anafro.quark.server.utils.time;

import ru.anafro.quark.server.utils.guard.Guard;
import static ru.anafro.quark.server.utils.time.TimeUnit.*;

public final class TimeSpan {
    private final long milliseconds;

    public TimeSpan(long milliseconds) {
        this.milliseconds = Guard.notNegative(milliseconds);
    }

    public static TimeSpan of(long time, TimeUnit unit) {
        return new TimeSpan(time * unit.milliseconds());
    }

    public static TimeSpan milliseconds(long milliseconds) {
        return TimeSpan.of(milliseconds, MILLISECONDS);
    }

    public static TimeSpan seconds(long seconds) {
        return new TimeSpan(seconds, SECONDS);
    }

    public static TimeSpan minutes(long minutes) {
        return new TimeSpan(minutes, MINUTES);
    }

    public static TimeSpan hours(long hours) {
        return new TimeSpan(hours, HOURS);
    }

    public static TimeSpan days(long days) {
        return new TimeSpan(days, DAYS);
    }

    public static TimeSpan months(long months) {
        return new TimeSpan(months, MONTHS);
    }

    public static TimeSpan years(long years) {
        return new TimeSpan(years, YEARS);
    }

    public boolean isInstant() {
        return this.milliseconds == 0;
    }

    public long getMilliseconds() {
        return TimeSpan.of(milliseconds, MILLISECONDS);
    }

    public long getSeconds() {
        return new TimeSpan(seconds, SECONDS);
    }

    public long getMinutes() {
        return new TimeSpan(minutes, MINUTES);
    }

    public long getHours() {
        return new TimeSpan(hours, HOURS);
    }

    public long getDays() {
        return new TimeSpan(days, DAYS);
    }

    public long getMonths() {
        return new TimeSpan(months, MONTHS);
    }

    public long getYears() {
        return new TimeSpan(years, YEARS);
    }
}