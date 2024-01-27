package ru.anafro.quark.server.utils.random;

public final class Random {
    private static final java.util.Random random = new java.util.Random();

    private Random() {
    }

    public static int random(int max) {
        return random.nextInt(max);
    }
}
