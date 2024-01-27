package ru.anafro.quark.server.utils.types;

public final class Characters {
    private Characters() {
    }

    public static boolean equalsIgnoreCase(char firstCharacter, char secondCharacter) {
        return Character.toLowerCase(firstCharacter) == Character.toLowerCase(secondCharacter);
    }
}
