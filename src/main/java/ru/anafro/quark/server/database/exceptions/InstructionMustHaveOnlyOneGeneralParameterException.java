package ru.anafro.quark.server.database.exceptions;

public class InstructionMustHaveOnlyOneGeneralParameterException extends DatabaseException {
    public InstructionMustHaveOnlyOneGeneralParameterException(String existingGeneralParameter, String newGeneralParameter) {
        super(STR."Instruction already has the general parameter \{existingGeneralParameter}, but another general parameter \{newGeneralParameter} met");
    }
}
