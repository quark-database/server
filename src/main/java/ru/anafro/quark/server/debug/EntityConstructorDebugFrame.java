package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.debug.components.DebugFrame;
import ru.anafro.quark.server.debug.components.TextArea;
import ru.anafro.quark.server.debug.components.TextField;
import ru.anafro.quark.server.exceptions.QuarkException;

public class EntityConstructorDebugFrame extends DebugFrame {
    private TextArea outputArea;
    private TextField expressionField;

    public EntityConstructorDebugFrame() {
        super("Entity Constructor Evaluation", 600, 200);
    }

    @Override
    protected void constructInterface() {
        this.outputArea = TextArea.console(0, 0, 600, 180);
        this.expressionField = TextField.console(0, 180, 600, 20, () -> {
            var parser = Quark.server().getInstructionParser();
            var lexer = Quark.server().getInstructionLexer();

            try {
                parser.parse(lexer.lex("do nothing " + expressionField.getText() + ";"));
                var result = parser.getArguments().get("general parameter");

                outputArea.setText("(" + result.getType() + ") " + result.getValueAsString());
            } catch(QuarkException exception) {
                outputArea.setText(exception.getMessage());
            }
        });

        add(outputArea);
        add(expressionField);
    }
}
