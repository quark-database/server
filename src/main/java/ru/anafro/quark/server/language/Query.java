package ru.anafro.quark.server.language;

import ru.anafro.quark.server.facade.Quark;

public record Query(Instruction instruction, InstructionArguments arguments) {

    public static Query make(String query) {
        var lexer = Quark.server().getLexer();
        var parser = Quark.server().getParser();
        var tokens = lexer.lex(query);
        var instruction = parser.parse(tokens);
        var arguments = parser.getArguments();

        return new Query(instruction, arguments);
    }

    public InstructionResult execute() {
        return instruction.execute(arguments);
    }
}
