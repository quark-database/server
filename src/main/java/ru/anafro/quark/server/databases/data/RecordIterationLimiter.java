package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.exceptions.RecordIterationLimiterInvalidParametersException;

public class RecordIterationLimiter {
    private int skip;
    private int limit;

    public RecordIterationLimiter(int skip, int limit) {
        if(skip < 0) {
            throw new RecordIterationLimiterInvalidParametersException("'Skip' cannot be negative, but %d received.".formatted(skip));
        }

        if(limit < 0) {
            throw new RecordIterationLimiterInvalidParametersException("'Limit' cannot be negative, but %d received.".formatted(skip));
        }

        this.skip = skip;
        this.limit = limit;
    }

    public boolean isSkipNeeded() {
        return skip != 0;
    }

    public boolean fitsTheLimit() {
        return this.limit != 0;
    }

    public void skipped() {
        if(skip == 0) {
            return;
        }

        skip--;
    }

    public void selected() {
        if(limit == 0) {
            return;
        }

        limit--;
    }

    public int getSkip() {
        return skip;
    }

    public int getLimit() {
        return limit;
    }
}
