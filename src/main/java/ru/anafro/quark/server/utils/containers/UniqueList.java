package ru.anafro.quark.server.utils.containers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UniqueList<T> implements Iterable<T> {
    private final List<T> values;

    @SafeVarargs
    public UniqueList(T... values) {
        this.values = new ArrayList<>(List.of(values));
    }

    public boolean contains(T value) {
        return values.contains(value);
    }

    public void add(T value) {
        if(!contains(value)) {
            values.add(value);
        }
    }

    public T get(int index) {
        return values.get(index);
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public ArrayList<T> asList() {
        return new ArrayList<>(values);
    }

    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }
}
