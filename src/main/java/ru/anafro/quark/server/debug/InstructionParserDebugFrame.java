package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.debug.components.DebugFrame;
import ru.anafro.quark.server.debug.components.TextArea;
import ru.anafro.quark.server.debug.components.TextField;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.utils.strings.English;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class InstructionParserDebugFrame extends DebugFrame {
    private TextArea parserOutputArea;
    private TextField instructionField;

    public InstructionParserDebugFrame() {
        super("Instruction Parser", "parser", 800, 600);
    }

    @Override
    protected void constructInterface() {
        this.parserOutputArea = TextArea.console(0, 0, 800, 580);
        this.instructionField = TextField.console(0, 580, 800, 20, () -> {
            var lexer = server.getInstructionLexer();
            var parser = server.getInstructionParser();

            try {
                parser.parse(lexer.lex(instructionField.getText()));
                var instruction = parser.getInstruction();
                var buffer = new TextBuffer();

                buffer.appendLine("Instruction name: " + instruction.getName());
                buffer.appendLine("Instruction permission: " + instruction.getPermission());
                buffer.appendLine("Instruction parameters: ");

                buffer.increaseTabLevel();
                for(var parameter : instruction.getParameters()) {                        // TODO: VVV Repeating isOptional string representation twice. Create English.appendArticle(String).
                    buffer.appendLine("%s %s parameter %s with type %s %s".formatted(
                            English.articleFor(parameter.isOptional() ? "optional" : "required"),
                            parameter.isOptional() ? "optional" : "required", quoted(parameter.getName()),
                            parser.getArguments().get(parameter.getName()).getType(),
                            parser.getArguments().has(parameter.getName()) ? "= " + parser.getArguments().get(parameter.getName()).toInstructionForm() : "is unset")
                    );
                }
                buffer.resetTabLevel();

                parserOutputArea.setText(buffer.extractContent());
            } catch(QuarkException exception) {
                parserOutputArea.setText(exception.getMessage());
            }
        });

        add(parserOutputArea);
        add(instructionField);
    }
}
