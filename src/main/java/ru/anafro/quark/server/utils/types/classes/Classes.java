package ru.anafro.quark.server.utils.types.classes;

import ru.anafro.quark.server.Main;
import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;
import ru.anafro.quark.server.utils.strings.Strings;
import ru.anafro.quark.server.utils.strings.TextBuffer;

public final class Classes {
    private Classes() {
        throw new UtilityClassInstantiationException(getClass());
    }

    public static String toHumanReadableName(String className) {
        var buffer = new TextBuffer();
        var wordLength = 0;

        for (char character : className.toCharArray()) {
            if (Character.isUpperCase(character) && wordLength != 1) {
                buffer.append(' ');
                wordLength = 0;
            }

            buffer.append(character);
            wordLength += 1;
        }

        return Strings.capitalize(buffer.extractContent().stripLeading());
    }

    public static String getHumanReadableName(Class<?> clazz) {
        return toHumanReadableName(clazz.getSimpleName());
    }

    public static <T> String getHumanReadableClassName(T object) {
        return getHumanReadableName(object.getClass());
    }

    public static String cutPackageName(String fullClassName) {
        return Strings.getLastToken(fullClassName, ".");
    }

    public static boolean isQuarkPackageName(String packageName) {
        return packageName.startsWith(Main.class.getPackageName());
    }
}
