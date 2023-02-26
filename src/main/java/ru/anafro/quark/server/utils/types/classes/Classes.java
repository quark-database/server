package ru.anafro.quark.server.utils.types.classes;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;
import ru.anafro.quark.server.utils.strings.Strings;
import ru.anafro.quark.server.utils.strings.TextBuffer;

public final class Classes {
    private Classes() {
        throw new CallingUtilityConstructorException(getClass());
    }

    public static String getHumanReadableName(Class<?> clazz) {
        var buffer = new TextBuffer();

        for(char character : clazz.getSimpleName().toCharArray()) {
            if(Character.isUpperCase(character)) {
                buffer.append(' ');
            }

            buffer.append(character);
        }

        return Strings.capitalize(buffer.extractContent().stripLeading());
    }
}
