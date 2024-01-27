package ru.anafro.quark.server.database.language.parser.states;

import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.EntityConstructor;
import ru.anafro.quark.server.database.language.entities.ListEntity;
import ru.anafro.quark.server.database.language.parser.InstructionParser;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.utils.strings.English;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArgument.computed;

public class ReadingConstructorArgumentsInsideAnotherConstructorInstructionParserState extends ReadingConstructorArgumentsInstructionParserState {
    private final String argumentName;

    public ReadingConstructorArgumentsInsideAnotherConstructorInstructionParserState(InstructionParser parser, ReadingConstructorArgumentsInstructionParserState previousState, EntityConstructor constructor, Instruction instruction, String argumentName) {
        super(parser, previousState, constructor, instruction);
        this.argumentName = argumentName;
    }

    @Override
    public void performAfterEntityComputationActions(Entity computedEntity) {
        var previousState = this.getPreviousState();
        var constructor = previousState.getConstructor();
        var arguments = previousState.getArguments();
        var parameters = constructor.getParameters();
        var argument = parameters.get(argumentName);

        if (parameters.hasVarargs() && argument.isVarargs()) {
            logger.debug("Constructor has varargs, current parameter is varargs");
            var varargs = arguments.getOrAdd(ListEntity.class, argumentName, ListEntity.empty(computedEntity.getExactTypeName()));

            if (varargs.elementsDoesntHaveType(computedEntity.getExactTypeName())) {
                throw new QuarkException(STR."\{constructor}\ncannot have \{English.withArticle(computedEntity.getExactTypeName())} \{computedEntity} as \{English.withArticle(constructor.getReturnType().getName())} varargs element.");
            }

            varargs.add(computedEntity);
        } else {
            logger.debug("Current parameter is not varargs. Just assigning it to a regular parameter");
            arguments.add(computed(argumentName, computedEntity));
            previousState.moveToNextParameter();
        }

        logger.debug("Restoring the state");
        parser.restoreState();
    }

    @Override
    public ReadingConstructorArgumentsInstructionParserState getPreviousState() {
        return (ReadingConstructorArgumentsInstructionParserState) super.getPreviousState();
    }
}
