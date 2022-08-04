package ru.anafro.quark.server.databases.instructions.lexer.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.instructions.lexer.states.helpers.InstructionObjectRecognizer;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.ColonInstructionToken;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.InstructionNameInstructionToken;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.SemicolonInstructionToken;
import ru.anafro.quark.server.utils.validation.Validators;

public class ReadingInstructionHeaderInstructionLexerState extends InstructionLexerState {

    public ReadingInstructionHeaderInstructionLexerState(InstructionLexer lexer) {
        super(lexer, null);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        stopSkippingLexerIgnoredCharacters();

        if(Validators.validate(currentCharacter, Validators.IS_LATIN)) {
            lexer.pushCurrentCharacterToBuffer();
        } else if(lexer.currentCharacterShouldBeIgnored() && !lexer.getBufferContent().endsWith(" ")) {
            lexer.getBuffer().append(' ');
        } else if(currentCharacter == ';' || currentCharacter == ':') {
            lexer.pushToken(new InstructionNameInstructionToken(lexer.extractBufferContent().strip()));
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.switchState(new BetweenHeaderAndParametersInstructionLexerState(lexer));
        } else if (!Validators.validate(currentCharacter, Validators.IS_LATIN)) {
            lexer.pushToken(new InstructionNameInstructionToken(lexer.extractBufferContent().strip()));
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.switchState(new InstructionObjectRecognizer().recognizeObjectAndMakeLexerState(lexer, new BetweenHeaderAndParametersInstructionLexerState(lexer), currentCharacter));
        }





//        if(currentCharacter == ':' || currentCharacter == ';') { // TODO: These characters can be in string argument. Like: 'run command "quark:custom_response status ok"'
//            String instructionHeader = lexer.extractBufferContent();
//            StringTokenizer headerTokenizer = new StringTokenizer(instructionHeader, InstructionLexer.CHARACTERS_SHOULD_BE_IGNORED);
//
//            StringBuffer instructionName = new StringBuffer();
//
//            while(headerTokenizer.hasTokens()) {
//                String word = headerTokenizer.nextToken();
//
//                if(Validators.validate(word, Validators.ALPHA)) {
//                    if(!instructionName.isEmpty()) {
//                        instructionName.append(' ');
//                    }
//
//                    instructionName.append(word);
//                } else {
//                    String remaining = headerTokenizer.getRemaining();
//
//                    // TODO
//
//                    break;
//                }
//            }
//
//            if(instructionName.isEmpty()) {
//                throw new InstructionSyntaxException(lexer.getInstruction(), "Instruction name is missing", "Did you forget to add an instruction name? E. g. in instruction: create database 'shop'; the instruction name would be 'create database'. Ensure that your instruction name contains only lowercase latin characters!", 0, 1);
//            }
//
//            lexer.pushToken(new InstructionNameInstructionToken(instructionName.extractValue()));
//
//            if(currentCharacter == ':') {
//                lexer.pushToken(new ColonInstructionToken());
//                lexer.switchState(new ReadingInstructionParametersInstructionLexerState(lexer));
//            } else {
//                lexer.pushToken(new ColonInstructionToken());
//                lexer.switchState(new LexingEndedInstructionLexerState(lexer));
//            }
//        } else {
//            lexer.pushCurrentCharacterToBuffer();
//        }
    }
}
