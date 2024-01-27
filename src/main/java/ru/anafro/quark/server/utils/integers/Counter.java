package ru.anafro.quark.server.utils.integers;

public class Counter {
    private int count = 0;

    public void increment() {
        count++;
    }

    public void incrementIf(boolean condition) {
        if (condition) {
            increment();
        }
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return String.valueOf(count);
    }
}
