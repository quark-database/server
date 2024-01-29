package ru.anafro.quark.server.utils.time;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.utils.types.classes.Enums;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

import static ru.anafro.quark.server.utils.comparisons.Comparisons.is;
import static ru.anafro.quark.server.utils.time.TimeUnit.*;

public final class TimeSpan implements Comparable<TimeSpan> {
    private long milliseconds;

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

    public long get(TimeUnit unit) {
        return milliseconds / unit.milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public boolean isNotInstant() {
        return milliseconds != 0;
    }

    private TimeUnit getUnit() {
        return Stream.of(values())
                .sorted(Comparator.comparingLong(TimeUnit::getMilliseconds).reversed())
                .filter(unit -> get(unit) != 0)
                .findFirst()
                .orElse(Enums.max(values(), TimeUnit::getMilliseconds));
    }

    public void subtract(TimeSpan subtrahend) {
        if (is(subtrahend).greaterThan(this)) {
            this.milliseconds = 0;
            return;
        }

        this.milliseconds -= subtrahend.getMilliseconds();
    }

    public TimeSpan copy() {
        return new TimeSpan(milliseconds);
    }

    @Override
    public String toString() {
        var unit = getUnit();

        return STR."\{get(unit)} \{unit.name().toLowerCase()}";
    }

    @Override
    public int compareTo(@NotNull TimeSpan that) {
        return (int) (this.milliseconds - that.getMilliseconds());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TimeSpan) obj;
        return this.milliseconds == that.milliseconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(milliseconds);
    }
}