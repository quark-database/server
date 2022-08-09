package ru.anafro.quark.server.files;

public final class Assets {
    public static final String FOLDER = "Assets/";

    private Assets() {
        //
    }

    public static String get(String assetName) {
        return FOLDER + assetName;
    }
}
