package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.debug.components.Debugger;
import ru.anafro.quark.server.debug.components.TextArea;
import ru.anafro.quark.server.debug.components.TextField;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.exceptions.Exceptions;
import ru.anafro.quark.server.utils.strings.English;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import static ru.anafro.quark.server.utils.objects.Nulls.byDefault;
import static ru.anafro.quark.server.utils.objects.Nulls.nullByDefault;

public class InstructionParserDebugger extends Debugger {
    private final TextArea parserOutputArea;
    private final TextField queryField;

    public InstructionParserDebugger() {
        super("Instruction Parser", "parser", 800, 600);

        this.parserOutputArea = TextArea.console(0, 0, 800, 580);
        this.queryField = TextField.console(0, 580, 800, 20, () -> {
            var server = Quark.server();
            var lexer = server.getLexer();
            var parser = server.getParser();
            var query = getQuery();

            try {
                var tokens = lexer.lex(query);
                var instruction = parser.parse(tokens);
                var parameters = instruction.getParameters();
                var debugOutput = new TextBuffer();

                debugOutput.appendLine(STR."""
                        Instruction name:        \{instruction.getName()}
                        Instruction permission:  \{instruction.getPermission()}
                        Instruction parameters:
                        """);

                debugOutput.increaseTabLevel();
                for (var parameter : parameters) {
                    var arguments = parser.getArguments();
                    var optionality = English.withArticle(parameter.isOptional() ? "optional" : "required");
                    var parameterName = parameter.getName();
                    var parameterTypeName = parameter.getType();
                    var argument = arguments.has(parameterName) ? arguments.get(parameterName) : null;
                    var argumentTypeName = byDefault(argument, Entity::getTypeName, "(Argument was not provided)");
                    var argumentCode = nullByDefault(argument, Entity::toInstructionForm);

                    debugOutput.appendLine(STR."""
                            \{optionality} \{parameterTypeName} parameter '\{parameterName}' = \{argumentTypeName} (\{argumentCode})
                            """);
                }
                debugOutput.resetTabLevel();

                parserOutputArea.setText(debugOutput.extractContent());
            } catch (QuarkException exception) {
                parserOutputArea.setText(Exceptions.format(exception));
            }
        });

        add(parserOutputArea);
        add(queryField);
    }

    private String getQuery() {
        return queryField.getText();
    }
}
