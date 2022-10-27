package ru.anafro.quark.server.databases.ql.hints;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.utils.integers.Integers;

public class InstructionHint {
    private final InstructionHintType type;
    private final String title;
    private final String completion;
    private final String description;

    public InstructionHint(InstructionHintType type, String title, String completion, String description) {
        this.type = type;
        this.title = title;
        this.completion = completion;
        this.description = description;
    }

    public static InstructionHint instruction(String instructionName, int charactersAlreadyTyped) {
        return new InstructionHint(
                InstructionHintType.INSTRUCTION,
                instructionName,
                instructionName.substring(Integers.limit(charactersAlreadyTyped, 0, instructionName.length() - 1)),
                Quark.instructions().getOrThrow(instructionName, "Cannot make a hint for instruction with name " + instructionName).getDescription()
        );
    }

    public static InstructionHint constructor(String constructorName, int charactersAlreadyTyped) {
        var constructor = Quark.constructors().getOrThrow(constructorName, "Cannot make a hint for constructor with name " + constructorName);

        return new InstructionHint(
                InstructionHintType.CONSTRUCTOR,
                constructor.getSyntax().replace("\n", "").replace("\t", ""),
                constructorName.substring(Integers.limit(charactersAlreadyTyped, 0, constructorName.length() - 1)),
                constructor.getReturnDescription().getType().getName()
        );
    }

    public static InstructionHint parameter(String instructionName, String parameterName, int charactersAlreadyTyped) {
        var instruction = Quark.instructions().getOrThrow(instructionName, "Cannot make a hint for parameter with name " + instructionName);
        var parameter = instruction.getParameters().get(parameterName);

        return new InstructionHint(
                InstructionHintType.PARAMETER,
                parameterName,
                parameterName.substring(Integers.limit(charactersAlreadyTyped, 0, parameterName.length() - 1)) + " = ",
                parameter.getType() + (parameter.isOptional() ? " (optional)" : "")
        );
    }

    public InstructionHintType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getCompletion() {
        return completion;
    }

    public String getDescription() {
        return description;
    }
}
