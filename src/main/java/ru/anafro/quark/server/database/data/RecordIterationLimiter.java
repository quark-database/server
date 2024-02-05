package ru.anafro.quark.server.database.data;

public class RecordIterationLimiter {
    public static final RecordIterationLimiter UNLIMITED = new RecordIterationLimiter(0, Integer.MAX_VALUE);
    private int skip;
    private int limit;

    public RecordIterationLimiter(int skip, int limit) {
        this.skip = skip;
        this.limit = limit;
    }

    public static RecordIterationLimiter limiter(int skip, int limit) {
        return new RecordIterationLimiter(skip, limit);
    }

    public boolean isSkipNeeded() {
        return skip > 0;
    }

    public boolean fitsTheLimit() {
        return this.limit > 0;
    }

    public void skipped() {
        if (isSkipNeeded()) {
            skip--;
        }
    }

    public void selected() {
        if (fitsTheLimit()) {
            limit--;
        }
    }

    public int getSkip() {
        return skip;
    }

    public int getLimit() {
        return limit;
    }
}
