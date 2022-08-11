package ru.anafro.quark.server.databases.instructions.parser.states;

import ru.anafro.quark.server.databases.InstructionArgument;
import ru.anafro.quark.server.databases.instructions.entities.StringEntity;
import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.*;
import ru.anafro.quark.server.databases.instructions.parser.InstructionParser;
import ru.anafro.quark.server.utils.strings.Converter;

public class AfterInstructionNameInstructionParserState extends InstructionParserState {
    public AfterInstructionNameInstructionParserState(InstructionParser parser) {
        super(parser);
    }

    @Override
    public void handleToken(InstructionToken token) {
        if(token instanceof ColonInstructionToken colonToken) {
            if(parser.getInstruction().getParameters().hasGeneralParameter()) {
                expectationError("object", colonToken.getName());
            }

            parser.switchState(new ExpectingParameterNameInstructionParserState(parser));
        } else if(token instanceof StringLiteralInstructionToken stringToken) {
            // TODO: add checks for general parameter (it must be)

            parser.getArguments().add(new InstructionArgument(parser.getInstruction().getParameters().getGeneralParameter().getName(), new StringEntity(stringToken.getValue())));
        } else if(token instanceof FloatLiteralInstructionToken floatToken) {
            parser.getArguments().add(new InstructionArgument(parser.getInstruction().getParameters().getGeneralParameter().getName(), new FloatEntity(Converter.toFloat(floatToken.getValue()))));
        } else if(token instanceof IntegerLiteralInstructionToken integerEntity) {
            parser.getArguments().add(new InstructionArgument(parser.getInstruction().getParameters().getGeneralParameter().getName(), new IntegerEntity(Converter.toInteger(integerEntity.getValue())))); // TODO: ew
        } else if(token instanceof ConstructorNameInstructionToken constructorToken) {
            parser.switchState(new ReadingConstructorArgumentsInstructionParserState(parser, parser.getInstruction().getParameters().getGeneralParameter()));
        } else {
            expectationError("colon or object", token.getName());
        }
    }
}
