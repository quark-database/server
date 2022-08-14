package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;

public class ReadingConstructorArgumentsAsInstructionArgumentInstructionParserState extends ReadingConstructorArgumentsInstructionParserState {
    private final String argumentName;

    public ReadingConstructorArgumentsAsInstructionArgumentInstructionParserState(InstructionParser parser, InstructionParserState previousState, InstructionEntityConstructor constructor, Instruction instruction, String argumentName) {
        super(parser, previousState, constructor, instruction);
        this.argumentName = argumentName;
    }

    @Override
    public void afterEntityComputation(InstructionEntity computedEntity) {
        parser.getArguments().add(new InstructionArgument(argumentName, computedEntity));
        parser.restoreState();
    }
}
