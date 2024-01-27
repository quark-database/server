package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.database.language.Expressions;
import ru.anafro.quark.server.debug.components.Debugger;
import ru.anafro.quark.server.debug.components.TextArea;
import ru.anafro.quark.server.debug.components.TextField;
import ru.anafro.quark.server.exceptions.QuarkException;

public class EntityConstructorDebugger extends Debugger {
    private final TextArea outputArea;
    private final TextField expressionField;

    public EntityConstructorDebugger() {
        super("Entity Constructor Evaluation", "constructors", 600, 200);

        this.outputArea = TextArea.console(0, 0, 600, 180);
        this.expressionField = TextField.console(0, 180, 600, 20, () -> {
            try {
                var evaluatedEntity = Expressions.eval(getExpression());
                outputArea.setText(STR."(\{evaluatedEntity.getExactTypeName()}) \{evaluatedEntity.toInstructionForm()}");
            } catch (QuarkException exception) {
                outputArea.setText(exception.getMessage());
            }
        });

        add(outputArea);
        add(expressionField);
    }

    private String getExpression() {
        return expressionField.getText();
    }
}
