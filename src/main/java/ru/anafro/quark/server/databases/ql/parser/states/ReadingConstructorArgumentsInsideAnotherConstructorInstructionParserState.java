package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;
import ru.anafro.quark.server.utils.objects.Nulls;

public class ReadingConstructorArgumentsInsideAnotherConstructorInstructionParserState extends ReadingConstructorArgumentsInstructionParserState {
    private final String argumentName;

    public ReadingConstructorArgumentsInsideAnotherConstructorInstructionParserState(InstructionParser parser, ReadingConstructorArgumentsInstructionParserState previousState, EntityConstructor constructor, Instruction instruction, String argumentName) {
        super(parser, previousState, constructor, instruction);
        this.argumentName = argumentName;
    }

    @Override
    public void afterEntityComputation(Entity computedEntity) {
        var arguments = ((ReadingConstructorArgumentsInstructionParserState) previousState).getArguments();
        var constructor = ((ReadingConstructorArgumentsInstructionParserState) previousState).getConstructor();
        logger.debug("Constructor computation inside another constructor is completed.");
        logger.debug("Argument name: " + argumentName);
        logger.debug("Has the constructor a varargs argument? " + (constructor.getParameters().hasVarargs() ? "Yes" : "No"));
        logger.debug("Is current parameter a varargs argument? " + (Nulls.evalOrDefault(constructor.getParameters().getParameter(argumentName), InstructionEntityConstructorParameter::isVarargs, false) ? "Yes" : "No"));
        logger.debug("Instruction syntax: " + constructor.getSyntax());

        if(constructor.getParameters().hasVarargs() && constructor.getParameters().getParameter(argumentName).isVarargs()) {
            if(arguments.has(argumentName)) {
                logger.debug("Constructor has varargs, current parameter is varargs, and arguments already have an instructor parameter");
                var list = arguments.<ListEntity>get(argumentName);

                if(!list.getTypeOfValuesInside().equals(computedEntity.getExactTypeName())) {
                    computedEntity = Quark.types().get(list.getTypeOfValuesInside()).cast(computedEntity);
                }

                list.add(computedEntity);
            } else {
                logger.debug("Constructor has varargs, current parameter is varargs, but arguments does not have an instructor parameter. Creating a list for varargs with a computed entity");
                arguments.add(new InstructionEntityConstructorArgument(argumentName, new ListEntity(computedEntity.getExactTypeName(), computedEntity)));
            }
        } else {
            logger.debug("Current parameter is not varargs. Just assigning it to a regular parameter");
            arguments.add(InstructionEntityConstructorArgument.computed(argumentName, computedEntity));
        }

        logger.debug("Restoring the state");
        parser.restoreState();
    }
}
