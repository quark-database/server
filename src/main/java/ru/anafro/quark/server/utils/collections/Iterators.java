package ru.anafro.quark.server.utils.collections;

import java.util.Iterator;

public final class Iterators {
    private Iterators() {
    }

    public static <T> boolean equals(Iterator<? extends T> firstIterator, Iterator<? extends T> secondIterator) {
        boolean same = true;

        while (firstIterator.hasNext()) {
            if (!secondIterator.hasNext() || !firstIterator.next().equals(secondIterator.next())) {
                return false;
            }
        }

        return true;
    }
}
