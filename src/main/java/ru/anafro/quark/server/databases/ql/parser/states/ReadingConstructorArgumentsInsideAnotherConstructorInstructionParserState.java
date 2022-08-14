package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;

public class ReadingConstructorArgumentsInsideAnotherConstructorInstructionParserState extends ReadingConstructorArgumentsInstructionParserState {
    private final String argumentName;

    public ReadingConstructorArgumentsInsideAnotherConstructorInstructionParserState(InstructionParser parser, ReadingConstructorArgumentsInstructionParserState previousState, InstructionEntityConstructor constructor, Instruction instruction, String argumentName) {
        super(parser, previousState, constructor, instruction);
        this.argumentName = argumentName;
    }

    @Override
    public void afterEntityComputation(InstructionEntity computedEntity) {
        ((ReadingConstructorArgumentsInstructionParserState) previousState).getArguments().add(InstructionEntityConstructorArgument.computed(argumentName, computedEntity));
        parser.restoreState();
    }
}
