package ru.anafro.quark.server.utils.strings;

public class TextBuffer {
    private final StringBuilder builder = new StringBuilder();
    private int tabLevel = 0;

    public TextBuffer() {
        //
    }

    public TextBuffer(String initialString) {
        append(initialString);
    }

    public boolean valueEquals(String value) {
        return getContent().equals(value);
    }

    public String extractContent() {
        String extractedValue = getContent();
        clear();

        return extractedValue;
    }

    public void clear() {
        builder.setLength(0);
    }

    public <T> TextBuffer append(T appendingValue) {
        builder.append(appendingValue);
        return this;
    }

    public <T> TextBuffer appendLine(T appendingLine) {
        append("\t".repeat(getTabLevel()));
        append(appendingLine);
        return nextLine();
    }

    public TextBuffer nextLine() {
        return append('\n');
    }

    public String getContent() {
        return builder.toString();
    }

    @Override
    public String toString() {
        return getContent();
    }

    public StringBuilder getBuilder() {
        return builder;
    }

    public boolean isEmpty() {
        return builder.isEmpty();
    }

    public int getTabLevel() {
        return tabLevel;
    }

    public void setTabLevel(int tabLevel) {
        this.tabLevel = tabLevel;
    }

    public void increaseTabLevel() {
        setTabLevel(tabLevel + 1);
    }

    public void decreaseTabLevel() {
        setTabLevel(tabLevel - 1);
    }

    public void resetTabLevel() {
        setTabLevel(0);
    }
}