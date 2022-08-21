package ru.anafro.quark.server.utils.strings;


import ru.anafro.quark.server.utils.strings.exceptions.NoMoreCharactersToTokenizeException;

/**
 * String tokenizers can be used to split the string
 * by multiple delimiters.
 *
 * <pre>
 * {@code
 * var tokenizer = new StringTokenizer("anafro;is.the,best:programmer!ever", ';', '.', ',', ':', '!');
 *
 * // Prints out "anafro", "is", "the", "best", "programmer", "ever" line by line.
 * while(tokenizer.hasTokens()) {
 *      logger.info(tokenizer.nextToken());
 * }
 * }
 * </pre>
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    StringTokenizer#StringTokenizer(String, char...)
 * @see    StringTokenizer#nextToken()
 * @see    StringTokenizer#hasTokens()
 */
public class StringTokenizer {
    private final String string;
    private final char[] delimiters;
    private int currentIndex = 0;

    /**
     * Creates a new string tokenizer that will split <code>string</code>
     * by <code>delimiters</code>.
     *
     * @param  string a string that will be split.
     * @param  delimiters the delimiters the string will be split by.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    StringTokenizer#nextToken()
     * @see    StringTokenizer#hasTokens()
     */
    public StringTokenizer(String string, char... delimiters) {
        this.string = string;
        this.delimiters = delimiters;

        skipDelimitersAhead();
    }

    /**
     * Skips the delimiters ahead the character caret.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private void skipDelimitersAhead() {
        while(hasCharacters()) {
            if(characterShouldBeSkipped()) {
                moveToTheNextCharacter();
            }
        }
    }

    /**
     * Returns <code>true</code>, if there are at least one token ahead.
     * Otherwise, if this tokenizer ran out of tokens, <code>false</code> will
     * be returned.
     *
     * @return the boolean representation of token(s) existence.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    StringTokenizer#nextToken()
     * @see    StringTokenizer
     */
    public boolean hasTokens() {
        return hasCharacters();
    }

    /**
     * Returns the next token in the string stored inside this string tokenizer.
     * If there are no more tokens, {@link NoMoreCharactersToTokenizeException} will be
     * thrown. Note that the tokenizer will move to the next token next time on the
     * next <code>nextToken()</code> invocation. To use the token in multiple places,
     * store it to a variable.
     *
     * @return the next token of this string tokenizer.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    StringTokenizer#hasTokens()
     * @see    StringTokenizer
     */
    public String nextToken() {
        if(!hasCharacters()) {
            throw new NoMoreCharactersToTokenizeException(string);
        }

        TextBuffer token = new TextBuffer();

        while(hasCharacters() && !characterShouldBeSkipped()) {
            token.append(getCurrentCharacter());
            moveToTheNextCharacter();
        }

        skipDelimitersAhead();

        return token.extractContent();
    }

    /**
     * Returns the remaining string left not tokenized.
     *
     * <pre>
     * {@code
     * var tokenizer = new StringTokenizer("good.morning.quark.users", '.');
     * tokenizer.nextToken();
     * tokenizer.nextToken();
     *
     * tokenizer.getRemaining(); // quark.users
     * }
     * </pre>
     *
     * @return the not tokenized part of string.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public String getRemaining() {
        return string.substring(currentIndex);
    }

    /**
     * Returns <code>true</code>, if current character should be skipped
     * by the tokenizer, otherwise this method will return <code>false</code>.
     *
     * @return the boolean representation of necessity of skipping the current character.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    StringTokenizer#getDelimiters()
     * @see    StringTokenizer
     */
    private boolean characterShouldBeSkipped() {
        for(char delimiter : delimiters) {
            if(getCurrentCharacter() == delimiter) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns <code>true</code> if there are characters left not tokenized
     * inside this string tokenizer. Otherwise, this method will return <code>false</code>.
     *
     * @return the boolean representation of the left character existence.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    StringTokenizer#getRemaining()
     */
    private boolean hasCharacters() {
        return currentIndex < string.length() - 1;
    }

    /**
     * Moves the tokenizer character caret to the next character.
     * A {@link NoMoreCharactersToTokenizeException} will be thrown if
     * there are no more characters to move to.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private void moveToTheNextCharacter() {
        if(!hasCharacters()) {
            throw new NoMoreCharactersToTokenizeException(string);
        }

        currentIndex++;
    }

    /**
     * Returns the current character where this string tokenizer is.
     *
     * @return the current character.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private char getCurrentCharacter() {
        return string.charAt(currentIndex);
    }

    /**
     * Returns the current character index where this string tokenizer is.
     *
     * @return the current character index.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Returns the original string that this string tokenizer is tokenizing.
     * Even after tokenization the string stays the same.
     *
     * @return the original tokenization string.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public String getString() {
        return string;
    }

    /**
     * Returns the delimiters this tokenizer delimiters by the string.
     *
     * @return the delimiters.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public char[] getDelimiters() {
        return delimiters;
    }
}
