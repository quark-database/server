package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.ListEntity;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;

public class ReadingConstructorArgumentsInsideAnotherConstructorInstructionParserState extends ReadingConstructorArgumentsInstructionParserState {
    private final String argumentName;

    public ReadingConstructorArgumentsInsideAnotherConstructorInstructionParserState(InstructionParser parser, ReadingConstructorArgumentsInstructionParserState previousState, InstructionEntityConstructor constructor, Instruction instruction, String argumentName) {
        super(parser, previousState, constructor, instruction);
        this.argumentName = argumentName;
    }

    @Override
    public void afterEntityComputation(InstructionEntity computedEntity) {
        var arguments = ((ReadingConstructorArgumentsInstructionParserState) previousState).getArguments();

        if(constructor.getParameters().hasVarargs() && constructor.getParameters().getParameter(argumentName).isVarargs()) {
            if(arguments.has(argumentName)) {
                arguments.get(argumentName).as(ListEntity.class).add(computedEntity);
            } else {
                arguments.add(new InstructionEntityConstructorArgument(argumentName, new ListEntity(computedEntity.getType(), computedEntity)));
            }
        } else {
            arguments.add(InstructionEntityConstructorArgument.computed(argumentName, computedEntity));
        }

        parser.restoreState();
    }
}
