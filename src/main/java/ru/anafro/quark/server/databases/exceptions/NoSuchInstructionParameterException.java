package ru.anafro.quark.server.databases.exceptions;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionParameter;

import java.util.Arrays;

public class NoSuchInstructionParameterException extends QueryException {
    public NoSuchInstructionParameterException(Instruction instruction, String parameterName) {
        super("There is no parameter %s in instruction '%s'. Try a general parameter %s or additional parameters: %s"
                .formatted(
                        parameterName,
                        instruction.getName(),
                        instruction.hasGeneralParameter() ? instruction.getGeneralParameter().getName() : "[No general parameter]",
                        String.join(", ", Arrays.stream(instruction.getParameters()).map(InstructionParameter::getName).toArray(String[]::new))
                )
        ); // TODO: Change "try following" to "did you mean X?"
    }
}
