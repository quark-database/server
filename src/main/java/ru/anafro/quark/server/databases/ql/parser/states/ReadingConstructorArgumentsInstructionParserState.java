package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.databases.ql.lexer.InstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.LiteralInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ClosingParenthesisInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;
import ru.anafro.quark.server.utils.objects.Nulls;

public abstract class ReadingConstructorArgumentsInstructionParserState extends InstructionParserState {
    private final Instruction instruction;

    private final InstructionEntityConstructor constructor;
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

    @Override
    public void handleToken(InstructionToken token) {
        if(expectingComma) {                                            // <- TODO: Repeating code! #1
            if(token.is("comma")) {
                stopExpectingComma();
            } else if(token.is("closing parenthesis")) {
                stopExpectingComma();

                parser.getLogger().debug("Evaluating " + constructor.getSyntax() + " with arguments: ");
                for(var argument : arguments) {
                    parser.getLogger().debug(argument.getName() + " = " + Nulls.evalOrDefault(argument.getEntity(), argument.getEntity().getValue()::toString, "<null object>"));
                }

                computedEntity = constructor.eval(arguments);           // <- TODO: Repeated code! #2
                afterEntityComputation(computedEntity);
            } else {
                expectationError("comma", token.getName());
            }
        } else if(expectingOpeningParenthesis) {                        // <- TODO: Repeating code! #1
            if(token.is("opening parenthesis")) {
                stopExpectingOpeningParenthesis();
            } else {
                expectationError("opening parenthesis", token.getName());
            }
        } else if(token instanceof LiteralInstructionToken literalToken) {
            arguments.add(InstructionEntityConstructorArgument.computed(getCurrentConstructorParameterName(), literalToken.toEntity()));
            parameterIndex++;
            requireNextTokenToBeComma();
        } else if(token instanceof ConstructorNameInstructionToken constructorToken) {
            parser.switchState(new ReadingConstructorArgumentsInsideAnotherConstructorInstructionParserState(parser, this, constructorToken.getConstructor(), instruction, getCurrentConstructorParameterName()));
        } else if(token instanceof ClosingParenthesisInstructionToken) {
            computedEntity = constructor.eval(arguments);               // <- TODO: Repeated code! #2
            afterEntityComputation(computedEntity);
        } else {
            expectationError("object or comma", token.getName()); // TODO: Too ambiguous tip of expected object. Specify clearly.
        }
    }

    public String getCurrentConstructorParameterName() {
        return constructor.getParameters().parameterAt(parameterIndex).name();
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
}
