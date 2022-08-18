package ru.anafro.quark.server.databases.ql.lexer;

import ru.anafro.quark.server.databases.ql.lexer.exceptions.LexerStateCannotBeRestoredException;
import ru.anafro.quark.server.databases.ql.lexer.states.InstructionLexerState;
import ru.anafro.quark.server.databases.ql.lexer.states.ReadingInstructionHeaderInstructionLexerState;
import ru.anafro.quark.server.databases.ql.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.utils.arrays.Arrays;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;

public class InstructionLexer {
     private String instruction;
     private final Logger logger = new Logger(this.getClass());
     private ArrayList<InstructionToken> tokens = new ArrayList<>();
     private final TextBuffer buffer = new TextBuffer();
     private InstructionLexerState state = new ReadingInstructionHeaderInstructionLexerState(this);
     private int currentCharacterIndex;
     public static Character[] CHARACTERS_SHOULD_BE_IGNORED = {' ', '\n', '\t'};

     public ArrayList<InstructionToken> lex(String instruction) {
          this.instruction = instruction;
          this.tokens = new ArrayList<>();
          this.buffer.clear();
          this.state = new ReadingInstructionHeaderInstructionLexerState(this);
          this.currentCharacterIndex = 0;

          while(hasNextCharacter()) {
               logger.debug(instruction);
               logger.debug(" ".repeat(getCurrentCharacterIndex()) + "^");
               logger.debug("");
               logger.debug("Current character: '" + getCurrentCharacter() + "'");

               TextBuffer stateStackBuffer = new TextBuffer("State line: ");

               InstructionLexerState stateCaret = this.state;
               while(stateCaret.hasPreviousState()) {
                    stateStackBuffer.append(stateCaret.getClass().getSimpleName().substring(0, stateCaret.getClass().getSimpleName().length() - "InstructionLexerState".length()) + " -> ");
                    stateCaret = stateCaret.getPreviousState();
               }

               logger.debug(stateStackBuffer.extractContent());
               logger.debug(stateCaret.getClass().getSimpleName().substring(0, stateCaret.getClass().getSimpleName().length() - "InstructionLexerState".length()) + ".");
               logger.debug("Buffer:\t" + getBufferContent());

               if(tokens.isEmpty()) {
                    logger.debug("Tokens: <no tokens yet>");
               } else {
                    logger.debug("Tokens:");

                    for(InstructionToken token : tokens) {
                         logger.debug("\t" + token.getName() + ": " + token.getValue());
                    }
               }

               logger.debug("_".repeat(50)); // TODO: change to a separate method or extract "_".repeat(..) to a constant somewhere

               if(!(state.lexerIgnoredCharactersShouldBeSkipped() && currentCharacterShouldBeIgnored())) {
                    state.handleCharacter(getCurrentCharacter());
               }

               if(hasNextCharacter()) {
                    moveToTheNextCharacter();
               }
          }

          System.out.println("-- Lexing Complete --");
          System.out.println("Buffer: " + buffer.getContent());

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
          if(!state.hasPreviousState()) {
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
}
