package ru.anafro.quark.server.utils.containers;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.List;

public final class Lists {
    private Lists() {
        throw new CallingUtilityConstructorException(getClass());
    }

    public static <T> String join(List<? extends T> collection, String separator) {
        TextBuffer joinedContainer = new TextBuffer();

        for(int index = 0; index < collection.size(); index++) {
            joinedContainer.append(collection.get(index));

            if(index != collection.size() - 1) {
                joinedContainer.append(separator);
            }
        }

        return joinedContainer.extractContent();
    }

    public static <T> String join(List<? extends T> collection) {
        return join(collection, ", ");
    }
}
