package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.entities.Entity;

public final class ConstructorEvaluator {
    public static Entity eval(String entityToEval) {
        Quark.server().getInstructionParser().parse(Quark.server().getInstructionLexer().lex("eval " + entityToEval + ";"));
        return Quark.server().getInstructionParser().getArguments().get("object");
    }
}
