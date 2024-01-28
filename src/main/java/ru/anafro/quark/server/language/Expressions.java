package ru.anafro.quark.server.language;

import ru.anafro.quark.server.language.entities.Entity;

public final class Expressions {
    public static <T extends Entity> T eval(Class<T> type, String entityToEval) {
        var query = Query.make(STR."eval \{entityToEval};");
        return query.arguments().get(type, "entity");
    }

    public static Entity eval(String entityToEval) {
        return eval(Entity.class, entityToEval);
    }
}
