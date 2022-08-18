package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.LiteralInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ClosingParenthesisInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;
import ru.anafro.quark.server.utils.objects.Nulls;

public abstract class ReadingConstructorArgumentsInstructionParserState extends InstructionParserState {
    protected final Instruction instruction;

    protected final InstructionEntityConstructor constructor;
    private final InstructionEntityConstructorArguments arguments = new InstructionEntityConstructorArguments();
    private int parameterIndex = 0;
    private InstructionEntity computedEntity;
    private boolean expectingOpeningParenthesis = false;
    private boolean expectingComma = false;

    public ReadingConstructorArgumentsInstructionParserState(InstructionParser parser, InstructionParserState previousState, InstructionEntityConstructor constructor, Instruction instruction) {
        super(parser, previousState);
        this.constructor = constructor;
        this.instruction = instruction;

        requireNextTokenToBeOpeningParenthesis();
    }

    // TODO: Rewrite this, because it's too hard to read. Probably split to multiple states
    @Override
    public void handleToken(InstructionToken token) {
        if(expectingComma) {                                            // <- TODO: Repeating code! #1
            if(token.is("comma")) {
                logger.debug("Stopping expecting comma, because this token is comma");
                stopExpectingComma();
            } else if(token.is("closing parenthesis")) {
                logger.debug("Evaluating " + constructor.getSyntax() + " with arguments: ");
                for(var argument : arguments) {
                    logger.debug(argument.getName() + " = " + Nulls.evalOrDefault(argument.getEntity(), InstructionEntity::getValueAsString, "<null object>"));
                }

                computedEntity = constructor.eval(arguments);           // <- TODO: Repeated code! #2
                afterEntityComputation(computedEntity);
            } else {
                throwExcectationError("comma", token.getName());
            }
        } else if(expectingOpeningParenthesis) {                        // <- TODO: Repeating code! #1
            if(token.is("opening parenthesis")) {
                logger.debug("Stopping expecting opening parenthesis");
                stopExpectingOpeningParenthesis();
            } else {
                throwExcectationError("opening parenthesis", token.getName());
            }
        } else if(token instanceof LiteralInstructionToken literalToken) {
            if(getCurrentConstructorParameter().isVarargs()) {
                if(arguments.has(getCurrentConstructorParameterName())) {
                    logger.debug("Pushing this literal to varargs");
                    arguments.<ListEntity>get(getCurrentConstructorParameterName()).add(literalToken.toEntity());
                } else {
                    logger.debug("Creating varargs list with this literal");
                    arguments.add(new InstructionEntityConstructorArgument(getCurrentConstructorParameterName(), new ListEntity(getCurrentConstructorParameter().type(), literalToken.toEntity())));
                }
            } else {
                logger.debug("Assigning this value to a parameter and switching to the next parameter");
                arguments.add(InstructionEntityConstructorArgument.computed(getCurrentConstructorParameterName(), literalToken.toEntity()));
                parameterIndex++;
            }

            logger.debug("Expecting comma next time");
            requireNextTokenToBeComma();
        } else if(token instanceof ConstructorNameInstructionToken constructorToken) {
            logger.debug("Constructor name found. Starting reading its arguments");
            requireNextTokenToBeComma();
            parser.switchState(new ReadingConstructorArgumentsInsideAnotherConstructorInstructionParserState(parser, this, constructorToken.getConstructor(), instruction, getCurrentConstructorParameterName()));
        } else if(token instanceof ClosingParenthesisInstructionToken) {
            logger.debug("')' found. Evaluating the constructor");
            computedEntity = constructor.eval(arguments);               // <- TODO: Repeated code! #2
            afterEntityComputation(computedEntity);
        } else {
            throwExcectationError("object or comma", token.getName()); // TODO: Too ambiguous tip of expected object. Specify clearly.
        }
    }

    public String getCurrentConstructorParameterName() {
        return getCurrentConstructorParameter().name();
    }

    private InstructionEntityConstructorParameter getCurrentConstructorParameter() {
        return constructor.getParameters().parameterAt(parameterIndex);
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

    public abstract void afterEntityComputation(InstructionEntity computedEntity);

    public Instruction getInstruction() {
        return instruction;
    }

    public InstructionEntity getComputedEntity() {
        return computedEntity;
    }

    public int getParameterIndex() {
        return parameterIndex;
    }

    public InstructionEntityConstructorArguments getArguments() {
        return arguments;
    }

    public InstructionEntityConstructor getConstructor() {
        return constructor;
    }
}
