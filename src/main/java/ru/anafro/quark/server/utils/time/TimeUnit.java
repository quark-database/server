package ru.anafro.quark.server.utils.time;


public enum TimeUnit {
    MILLISECONDS(1),
    SECONDS(1000),
    MINUTES(60_000),
    HOURS(360_000),
    DAYS(8_640_000),
    MONTHS(259_200_000),
    YEARS(3_153_600_000);

    final long milliseconds;

    TimeUnit(long milliseconds) {
        this.milliseconds = milliseconds;
    }
}