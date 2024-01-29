package ru.anafro.quark.server.utils.comparisons;

public final class Comparisons {
    private Comparisons() {
    }

    public static <T extends Comparable<T>> ComparisonClause<T> is(T left) {
        return new ComparisonClause<>(left);
    }
}
