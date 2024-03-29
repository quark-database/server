package ru.anafro.quark.server.language.constructors.mapping.types;

import ru.anafro.quark.server.language.entities.EntityConstructor;

@SuppressWarnings("unused")
public final class Conditions {

    private Conditions() {
    }

    @EntityConstructor.Meta(name = "if")
    public static Object $if(boolean checkingCondition, Object ifTrue, Object ifFalse) {
        return checkingCondition ? ifTrue : ifFalse;
    }
}
