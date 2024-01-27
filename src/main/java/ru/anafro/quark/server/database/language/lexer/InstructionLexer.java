package ru.anafro.quark.server.database.language.lexer;

import ru.anafro.quark.server.database.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.database.language.lexer.exceptions.LexerStateCannotBeRestoredException;
import ru.anafro.quark.server.database.language.lexer.states.InstructionLexerState;
import ru.anafro.quark.server.database.language.lexer.states.ReadingInstructionHeaderInstructionLexerState;
import ru.anafro.quark.server.database.language.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.utils.arrays.Arrays;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;

public class InstructionLexer {
    public static final Character[] CHARACTERS_SHOULD_BE_IGNORED = {' ', '\n', '\t'};
    private final Logger logger = new Logger(this.getClass());
    private final TextBuffer buffer = new TextBuffer();
    private String instruction;
    private ArrayList<InstructionToken> tokens = Lists.empty();
    private InstructionLexerState state = new ReadingInstructionHeaderInstructionLexerState(this);
    private int currentCharacterIndex;
    private boolean allowBufferTrash = false;

    public synchronized ArrayList<InstructionToken> lex(String instruction) {
        this.instruction = instruction;
        this.tokens = Lists.empty();
        this.buffer.clear();
        this.state = new ReadingInstructionHeaderInstructionLexerState(this);
        this.currentCharacterIndex = 0;

        while (hasNextCharacter()) {
            logger.debug(instruction);
            logger.debug(STR."\{" ".repeat(getCurrentCharacterIndex())}^");
            logger.debug("");
            logger.debug(STR."Current character: '\{getCurrentCharacter()}'");

            TextBuffer stateStackBuffer = new TextBuffer("State line: ");

            InstructionLexerState stateCaret = this.state;
            while (stateCaret.hasPreviousState()) {
                stateStackBuffer.append(STR."\{stateCaret.getClass().getSimpleName().substring(0, stateCaret.getClass().getSimpleName().length() - "InstructionLexerState".length())} -> ");
                stateCaret = stateCaret.getPreviousState();
            }

            logger.debug(stateStackBuffer.extractContent());
            logger.debug(STR."\{stateCaret.getClass().getSimpleName().substring(0, stateCaret.getClass().getSimpleName().length() - "InstructionLexerState".length())}.");
            logger.debug(STR."Buffer:\t\{getBufferContent()}");

            if (tokens.isEmpty()) {
                logger.debug("Tokens: <no tokens yet>");
            } else {
                logger.debug("Tokens:");

                for (InstructionToken token : tokens) {
                    logger.debug(STR."\t\{token.getName()}: \{token.getValue()}");
                }
            }

            logger.debug("_".repeat(50)); // TODO: change to a separate method or extract "_".repeat(..) to a constant somewhere

            var currentCharacter = getCurrentCharacter();

            if (!(state.lexerIgnoredCharactersShouldBeSkipped() && currentCharacterShouldBeIgnored())) {
                state.handleCharacter(currentCharacter);
            }

            if (hasNextCharacter()) {
                moveToTheNextCharacter();
            }
        }

        if (!buffer.isEmpty() && !isBufferTrashAllowed()) {
            state.handleBufferTrash();

            throw new InstructionSyntaxException(state, instruction, "The instruction is not completed", "Complete the instruction", 0, instruction.length());
        }

        logger.debug("-- Lexing Completed --");

        return tokens;
    }

    public void pushToken(InstructionToken token) {
        tokens.add(token);
    }

    public String getInstruction() {
        return instruction;
    }

    public char getCurrentCharacter() {
        return instruction.charAt(currentCharacterIndex);
    }

    public int getCurrentCharacterIndex() {
        return currentCharacterIndex;
    }

    public void moveToTheNextCharacter() {
        this.currentCharacterIndex++;
    }


    public boolean hasNextCharacter() {
        return currentCharacterIndex < instruction.length();
    }

    public void pushCurrentCharacterToBuffer() {
        buffer.append(getCurrentCharacter());
    }

    public void switchState(InstructionLexerState state) {
        this.state = state;
    }

    public void letTheNextStateStartFromCurrentCharacter() {
        this.currentCharacterIndex--;
    }

    public void restoreState() {
        if (!state.hasPreviousState()) {
            throw new LexerStateCannotBeRestoredException(state);
        }

        this.state = state.getPreviousState();
    }

    public TextBuffer getBuffer() {
        return buffer;
    }

    public InstructionLexerState getState() {
        return state;
    }

    public String getBufferContent() {
        return buffer.getContent();
    }

    public String extractBufferContent() {
        return buffer.extractContent();
    }

    public boolean currentCharacterShouldBeIgnored() {
        return Arrays.contains(CHARACTERS_SHOULD_BE_IGNORED, getCurrentCharacter());
    }

    public Logger getLogger() {
        return logger;
    }

    public ArrayList<InstructionToken> getTokens() {
        return tokens;
    }

    public boolean isBufferTrashAllowed() {
        return allowBufferTrash;
    }

    public void allowBufferTrash() {
        this.allowBufferTrash = true;
    }
}
