package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionParameter;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;
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
                "hint next elements",

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
        var parser = new InstructionParser();
    }
}
