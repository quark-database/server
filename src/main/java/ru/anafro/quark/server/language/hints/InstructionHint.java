package ru.anafro.quark.server.language.hints;

import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.language.lexer.InstructionLexer;
import ru.anafro.quark.server.utils.integers.Integers;

import java.util.List;

import static java.util.Collections.emptyList;

public record InstructionHint(InstructionHintType type, String title, String completion,
                              String description) {

    public static InstructionHint instruction(String instructionName, int charactersAlreadyTyped) {
        return new InstructionHint(
                InstructionHintType.INSTRUCTION,
                instructionName,
                instructionName.substring(Integers.limit(charactersAlreadyTyped, 0, instructionName.length() - 1)),
                Quark.instructions().getOrThrow(instructionName, STR."Cannot make a hint for instruction with name \{instructionName}").getDescription()
        );
    }

    public static InstructionHint constructor(String constructorName, int charactersAlreadyTyped) {
        var constructor = Quark.constructors().getOrThrow(constructorName, STR."Cannot make a hint for constructor with name \{constructorName}");

        return new InstructionHint(
                InstructionHintType.CONSTRUCTOR,
                constructor.getSyntax().replace("\n", "").replace("\t", ""),
                constructorName.substring(Integers.limit(charactersAlreadyTyped, 0, constructorName.length() - 1)),
                constructor.getReturnDescription().getType().getName()
        );
    }

    public static InstructionHint parameter(String instructionName, String parameterName, int charactersAlreadyTyped) {
        var instruction = Quark.instructions().getOrThrow(instructionName, STR."Cannot make a hint for parameter with name \{instructionName}");
        var parameter = instruction.getParameters().get(parameterName);

        return new InstructionHint(
                InstructionHintType.PARAMETER,
                parameterName,
                STR."\{parameterName.substring(Integers.limit(charactersAlreadyTyped, 0, parameterName.length() - 1))} = ",
                parameter.getType() + (parameter.isOptional() ? " (optional)" : "")
        );
    }

    public static List<InstructionHint> makeHints(String query, int caretPosition) {
        var lexer = new InstructionLexer();
        var limitedCaretPosition = Integers.limit(caretPosition, 0, query.length());

        lexer.allowBufferTrash();
        lexer.lex(query.substring(0, limitedCaretPosition));

        try {
            return lexer.getState().makeHints();
        } catch (Exception ignored) {
            return emptyList();
        }
    }
}
