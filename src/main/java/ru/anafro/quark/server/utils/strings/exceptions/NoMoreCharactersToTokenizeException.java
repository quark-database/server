package ru.anafro.quark.server.utils.strings.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;

/**
 * {@link NoMoreCharactersToTokenizeException} only used in {@link ru.anafro.quark.server.utils.strings.StringTokenizer}
 * to indicate that there are no more characters to tokenize inside the tokenizer.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    ru.anafro.quark.server.utils.strings.StringTokenizer
 */
public class NoMoreCharactersToTokenizeException extends QuarkException {
    public NoMoreCharactersToTokenizeException(String stringToTokenize) {
        super("No more characters can be tokenized in string: " + stringToTokenize);
    }
}
