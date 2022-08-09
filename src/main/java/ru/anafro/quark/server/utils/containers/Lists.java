package ru.anafro.quark.server.utils.containers;

import ru.anafro.quark.server.utils.strings.StringBuffer;

import java.util.Collection;
import java.util.List;

public final class Lists {
    private Lists() {
        // Preventing creating utility class instance.
    }

    public static <T> String join(List<? extends T> collection, String separator) {
        StringBuffer joinedContainer = new StringBuffer();

        for(int index = 0; index < collection.size(); index++) {
            joinedContainer.append(collection.get(index));

            if(index != collection.size() - 1) {
                joinedContainer.append(separator);
            }
        }

        return joinedContainer.extractValue();
    }

    public static <T> String join(List<? extends T> collection) {
        return join(collection, ", ");
    }
}
