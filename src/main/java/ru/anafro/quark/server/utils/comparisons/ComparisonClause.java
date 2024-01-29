package ru.anafro.quark.server.utils.comparisons;

public final class ComparisonClause<T extends Comparable<T>> {
    private final T left;

    public ComparisonClause(T left) {
        this.left = left;
    }

    public boolean greaterThan(T right) {
        return left.compareTo(right) > 0;
    }
}
