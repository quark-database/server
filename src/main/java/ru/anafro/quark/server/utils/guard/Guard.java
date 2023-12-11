package ru.anafro.quark.server.utils.guard;


public final class Guard {
    private Guard() {}

    public static <T extends Number> void notNegative(T number) {
        if(number < 0) {
            throw InvalidArgumentException("The number expected to be not negative, but " + number + " received.");
        }

        return number;
    }
}