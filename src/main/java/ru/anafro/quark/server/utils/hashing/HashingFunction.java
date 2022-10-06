package ru.anafro.quark.server.utils.hashing;

public abstract class HashingFunction<T> {
    private final String name;

    public HashingFunction(String name) {
        this.name = name;
    }

    public abstract int hash(T object);

    public String getName() {
        return name;
    }
}
