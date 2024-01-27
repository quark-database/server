package ru.anafro.quark.server.database.language.parser.states;

import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.entities.*;
import ru.anafro.quark.server.database.language.lexer.tokens.ClosingParenthesisInstructionToken;
import ru.anafro.quark.server.database.language.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.database.language.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.database.language.lexer.tokens.LiteralInstructionToken;
import ru.anafro.quark.server.database.language.parser.InstructionParser;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArgument.computed;

public abstract class ReadingConstructorArgumentsInstructionParserState extends InstructionParserState {
    protected final Instruction instruction;

    protected final EntityConstructor constructor;
    private final InstructionEntityConstructorArguments arguments = new InstructionEntityConstructorArguments();
    private int parameterIndex = 0;
    private Entity computedEntity;
    private boolean expectingOpeningParenthesis = false;
    private boolean expectingComma = false;

    public ReadingConstructorArgumentsInstructionParserState(InstructionParser parser, InstructionParserState previousState, EntityConstructor constructor, Instruction instruction) {
        super(parser, previousState);
        this.constructor = constructor;
        this.instruction = instruction;

        requireNextTokenToBeOpeningParenthesis();
    }

    @Override
    public void handleToken(InstructionToken token) {
        if (expectingComma) {
            if (token.is("comma")) {
                logger.debug("A comma is met.");

                stopExpectingComma();
                return;
            }

            if (token.isNot("closing parenthesis")) {
                throwExpectationError("comma", token);
            }

            logger.debug(STR."Evaluating \{constructor} with arguments: ");

            for (var argument : arguments) {
                logger.debug(STR."(\{argument.getEntity().getExactTypeName()}) \{argument}");
            }

            computedEntity = constructor.eval(arguments);
            performAfterEntityComputationActions(computedEntity);
        } else if (expectingOpeningParenthesis) {
            if (token.isNot("opening parenthesis")) {
                throwExpectationError("opening parenthesis", token);
            }

            logger.debug("Stopping expecting opening parenthesis");
            stopExpectingOpeningParenthesis();
        } else if (token instanceof LiteralInstructionToken literalToken) {
            var argumentEntity = literalToken.toEntity();
            var parameter = getCurrentConstructorParameter();
            var parameterName = parameter.name();
            var parameterType = parameter.type();

            if (parameter.isVarargs()) {
                if (arguments.has(parameterName)) {
                    logger.debug("Pushing this literal to varargs");
                    arguments.getList(parameterName).add(argumentEntity);
                } else {
                    logger.debug("Creating varargs list with this literal");
                    arguments.add(parameterName, ListEntity.of(argumentEntity));
                }
            } else {
                logger.debug("Assigning this value to a parameter and switching to the next parameter");
                arguments.add(computed(parameterName, argumentEntity));
                moveToNextParameter();
            }

            logger.debug("Expecting comma next time");
            requireNextTokenToBeComma();
        } else if (token instanceof ConstructorNameInstructionToken constructorToken) {
            logger.debug("Constructor name found. Starting reading its arguments");
            requireNextTokenToBeComma();
            parser.switchState(new ReadingConstructorArgumentsInsideAnotherConstructorInstructionParserState(parser, this, constructorToken.getConstructor(), instruction, getCurrentConstructorParameterName()));
        } else if (token instanceof ClosingParenthesisInstructionToken) {
            logger.debug("')' found. Evaluating the constructor");
            computedEntity = constructor.eval(arguments);
            performAfterEntityComputationActions(computedEntity);
        } else {
            throwExpectationError("object or comma", token);
        }
    }

    public void moveToNextParameter() {
        parameterIndex++;
    }

    public String getCurrentConstructorParameterName() {
        return getCurrentConstructorParameter().name();
    }

    private InstructionEntityConstructorParameter getCurrentConstructorParameter() {
        return constructor.getParameters().parameterAt(parameterIndex);
    }

    private String getCurrentConstructorParameterType() {
        return getCurrentConstructorParameter().type();
    }

    public void requireNextTokenToBeComma() {
        this.expectingComma = true;
    }

    public void stopExpectingComma() {
        this.expectingComma = false;
    }

    public void requireNextTokenToBeOpeningParenthesis() {
        this.expectingOpeningParenthesis = true;
    }

    public void stopExpectingOpeningParenthesis() {
        this.expectingOpeningParenthesis = false;
    }

    public abstract void performAfterEntityComputationActions(Entity computedEntity);

    public Instruction getInstruction() {
        return instruction;
    }

    public Entity getComputedEntity() {
        return computedEntity;
    }

    public int getParameterIndex() {
        return parameterIndex;
    }

    public InstructionEntityConstructorArguments getArguments() {
        return arguments;
    }

    public EntityConstructor getConstructor() {
        return constructor;
    }
}
