package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;

import static ru.anafro.quark.server.language.InstructionParameter.general;
import static ru.anafro.quark.server.language.InstructionParameter.required;

public class VarInstruction extends Instruction {

    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public VarInstruction() {
        super("var", "Defines a variable", "var", general("name", "str"), required("value", "?"));
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var variableName = arguments.getString("name");
        var variableValue = arguments.get("value");

        Quark.setVariable(variableName, variableValue);
        result.ok("The variable is set.");
    }
}
