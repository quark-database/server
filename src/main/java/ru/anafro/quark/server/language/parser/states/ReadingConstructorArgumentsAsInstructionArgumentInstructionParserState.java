package ru.anafro.quark.server.language.parser.states;

import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArgument;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.parser.InstructionParser;

public class ReadingConstructorArgumentsAsInstructionArgumentInstructionParserState extends ReadingConstructorArgumentsInstructionParserState {
    private final String argumentName;

    public ReadingConstructorArgumentsAsInstructionArgumentInstructionParserState(InstructionParser parser, InstructionParserState previousState, EntityConstructor constructor, Instruction instruction, String argumentName) {
        super(parser, previousState, constructor, instruction);
        this.argumentName = argumentName;
    }

    @Override
    public void performAfterEntityComputationActions(Entity computedEntity) {
        logger.debug("Computation of constructor is completed. Assigning the computed value to an argument");
        parser.getArguments().add(new InstructionArgument(argumentName, computedEntity));
        parser.restoreState();
    }
}
