package ru.anafro.quark.server.utils.time;

import static ru.anafro.quark.server.utils.time.TimeUnit.*;

public record TimeSpan(long milliseconds) {
    public TimeSpan(long milliseconds) {
        this.milliseconds = Math.max(0, milliseconds);
    }

    public static TimeSpan of(long time, TimeUnit unit) {
        return new TimeSpan(time * unit.milliseconds);
    }

    public static TimeSpan milliseconds(long milliseconds) {
        return TimeSpan.of(milliseconds, MILLISECONDS);
    }

    public static TimeSpan seconds(long seconds) {
        return TimeSpan.of(seconds, SECONDS);
    }

    public static TimeSpan minutes(long minutes) {
        return TimeSpan.of(minutes, MINUTES);
    }

    public static TimeSpan hours(long hours) {
        return TimeSpan.of(hours, HOURS);
    }

    public static TimeSpan days(long days) {
        return TimeSpan.of(days, DAYS);
    }

    public static TimeSpan weeks(long weeks) {
        return TimeSpan.of(weeks, WEEKS);
    }

    public static TimeSpan months(long months) {
        return TimeSpan.of(months, MONTHS);
    }

    public static TimeSpan years(long years) {
        return TimeSpan.of(years, YEARS);
    }

    public long convertTo(TimeUnit unit) {
        return milliseconds / unit.milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    @Override
    public long milliseconds() {
        return this.convertTo(MILLISECONDS);
    }
}