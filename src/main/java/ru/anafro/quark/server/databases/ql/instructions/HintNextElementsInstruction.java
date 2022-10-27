package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.*;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.views.TableViewHeader;
import ru.anafro.quark.server.databases.views.TableViewRow;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.integers.Integers;

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

                InstructionParameter.required("query"),
                InstructionParameter.optional("caret position", "int")
        );
    }

    @Override
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        var query = arguments.getString("query");
        var caretPosition = arguments.has("caret position") ? Integers.limit(arguments.getInteger("caret position"), 0, query.length()) : query.length() - 1;

        var lexer = new InstructionLexer();

        lexer.allowBufferTrash();
        lexer.lex(query.substring(0, caretPosition));

        result.header(new TableViewHeader("type", "title", "description", "completion"));

        try {
            for (var hint : lexer.getState().makeHints()) {
                result.appendRow(new TableViewRow(
                        hint.getType().toString().toLowerCase(),
                        hint.getTitle(),
                        hint.getDescription(),
                        hint.getCompletion()
                ));
            }
        } catch(Exception ignored) {
            // TODO: Probably there's a better way to determine if hints are not needed.
        }

        result.status(QueryExecutionStatus.OK, "Hints are collected.");
    }
}
