package ru.anafro.quark.server.utils.strings;


import ru.anafro.quark.server.utils.strings.exceptions.NoMoreCharactersToTokenizeException;

public class StringTokenizer {
    private final String string;
    private final char[] delimiters;
    private int currentIndex = 0;

    public StringTokenizer(String string, char... delimiters) {
        this.string = string;
        this.delimiters = delimiters;

        skipDelimitersAhead();
    }

    protected void skipDelimitersAhead() {
        while(hasCharacters()) {
            if(characterShouldBeSkipped()) {
                moveToTheNextCharacter();
            }
        }
    }

    public boolean hasTokens() {
        return hasCharacters();
    }

    public String nextToken() {
        if(!hasCharacters()) {
            throw new NoMoreCharactersToTokenizeException(string);
        }

        StringBuffer token = new StringBuffer();

        while(hasCharacters() && !characterShouldBeSkipped()) {
            token.append(getCurrentCharacter());
            moveToTheNextCharacter();
        }

        skipDelimitersAhead();

        return token.extractValue();
    }

    public String getRemaining() {
        return string.substring(currentIndex);
    }

    private boolean characterShouldBeSkipped() {
        for(char delimiter : delimiters) {
            if(getCurrentCharacter() == delimiter) {
                return true;
            }
        }

        return false;
    }

    protected boolean hasCharacters() {
        return currentIndex < string.length() - 1;
    }

    public void moveToTheNextCharacter() {
        if(!hasCharacters()) {
            throw new NoMoreCharactersToTokenizeException(string);
        }

        currentIndex++;
    }

    public char getCurrentCharacter() {
        return string.charAt(currentIndex);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public String getString() {
        return string;
    }

    public char[] getDelimiters() {
        return delimiters;
    }
}
