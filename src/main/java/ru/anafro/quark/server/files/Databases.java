package ru.anafro.quark.server.files;

public class Databases {
    public static final String FOLDER = "Databases/";

    private Databases() {
        //
    }

    public static String get(String databaseName) {
        return FOLDER + databaseName;
    }
}
