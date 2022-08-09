package ru.anafro.quark.server.databases.exceptions;

public class InstructionMustHaveOnlyOneGeneralParameterException extends DatabaseException {
    public InstructionMustHaveOnlyOneGeneralParameterException(String existingGeneralParameter, String newGeneralParameter) {
        super("Instruction already has the general parameter %s, but another general parameter %s met");
    }
}
