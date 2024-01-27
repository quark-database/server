package ru.anafro.quark.server.utils.time;


public enum TimeUnit {
    MILLISECONDS(1L),
    SECONDS(1000L),
    MINUTES(60_000L),
    HOURS(360_000L),
    DAYS(8_640_000L),
    WEEKS(60_480_000L),
    MONTHS(259_200_000L),
    YEARS(3_153_600_000L);

    final long milliseconds;

    TimeUnit(long milliseconds) {
        this.milliseconds = milliseconds;
    }
}