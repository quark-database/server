package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;
import ru.anafro.quark.server.language.lexer.InstructionLexer;
import ru.anafro.quark.server.utils.integers.Integers;

import static ru.anafro.quark.server.language.InstructionParameter.optional;
import static ru.anafro.quark.server.language.InstructionParameter.required;

public class HintNextElementsInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public HintNextElementsInstruction() {
        super(
                "_hint next elements",

                "Hints the next elements for editor hints. Don't use.",

                "any",

                required("query"),
                optional("caret position", "int")
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var query = arguments.getString("query");
        var caretPosition = arguments.has("caret position") ? Integers.limit(arguments.getInt("caret position"), 0, query.length()) : query.length() - 1;

        var lexer = new InstructionLexer();

        lexer.allowBufferTrash();
        lexer.lex(query.substring(0, caretPosition));

        result.header("type", "title", "description", "completion");

        try {
            for (var hint : lexer.getState().makeHints()) {
                result.row(
                        hint.type().toString().toLowerCase(),
                        hint.title(),
                        hint.description(),
                        hint.completion()
                );
            }
        } catch (Exception ignored) {
            // TODO: Probably there's a better way to determine if hints are not needed.
        }

        result.ok("Hints are collected.");
    }
}
