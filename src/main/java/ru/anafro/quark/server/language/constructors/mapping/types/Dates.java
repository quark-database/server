package ru.anafro.quark.server.language.constructors.mapping.types;

import java.util.Date;

@SuppressWarnings("unused")
public final class Dates {
    private Dates() {
    }

    public static Date now() {
        return new Date();
    }
}
