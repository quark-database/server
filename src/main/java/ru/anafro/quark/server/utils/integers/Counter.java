package ru.anafro.quark.server.utils.integers;

public class Counter {
    private int count = 0;

    public void count() {
        count++;
    }

    public void countIf(boolean condition) {
        if(condition) {
            count();
        }
    }

    public int getCount() {
        return count;
    }
}
