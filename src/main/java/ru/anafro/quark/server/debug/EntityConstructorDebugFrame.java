package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
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
            try {
                var evaluatedEntity = ConstructorEvaluator.eval(expressionField.getText());
                outputArea.setText("(" + evaluatedEntity.getExactTypeName() + ") " + evaluatedEntity.toInstructionForm());
            } catch(QuarkException exception) {
                outputArea.setText(exception.getMessage());
            }
        });

        add(outputArea);
        add(expressionField);
    }
}
