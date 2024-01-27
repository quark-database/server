package ru.anafro.quark.server.utils.strings;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.utils.collections.Collections;

import java.util.Optional;
import java.util.function.Function;

/**
 * Quark text buffers provide more functionality than Java's "StringBuilder",
 * and makes the syntax of string buffer usage less verbose, especially in terms of buffer clearing.
 * <br><br>
 * <p>
 * If you have to generate a text by some algorithm, it is better to use a TextBuilder instead of
 * appending strings to a single string. For example:
 *
 * <pre>
 * {@code
 * final var MAX = 10;
 * var buffer = new TextBuffer();
 *
 * for(int index = 1; index <= MAX; index++) {
 *      buffer.append(index);
 *
 *      if(index == MAX) {
 *          buffer.append(", ");
 *      } else {
 *          buffer.append(";");
 *      }
 * }
 *
 * var string = buffer.extractContent(); // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10;
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see TextBuffer#TextBuffer()
 * @see TextBuffer#TextBuffer(String)
 * @since Quark 1.1
 */
public class TextBuffer implements CharSequence {
    private final StringBuilder builder = new StringBuilder();
    private int tabLevel = 0;

    /**
     * Creates an empty text buffer.
     *
     * <pre>
     * {@code
     * new TextBuffer().extractContent(); // .equals("")
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#TextBuffer(String)
     * @since Quark 1.1
     */
    public TextBuffer() {
        //
    }

    /**
     * Creates a text buffer with an initial string inside.
     *
     * <pre>
     * {@code
     * new TextBuffer(string).extractContent().equals(string); // always true
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     * <br><br>
     *
     * <i>Note that you don't have to pass an empty string "" to create a text buffer
     * without content inside. Just use an empty constructor: {@link TextBuffer#TextBuffer()}.</i>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#TextBuffer()
     * @since Quark 1.1
     */
    public TextBuffer(String initialString) {
        append(initialString);
    }

    /**
     * Extracts the content from this buffer. It means that the content will be
     * returned from this method and <strong>the buffer will be cleared</strong>.
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer("hello ");
     * buffer.append("world");
     *
     * buffer.extractContent(); // hello world
     * buffer.isEmpty(); // true
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#clear()
     * @see TextBuffer#getContent()
     * @since Quark 1.1
     */
    public String extractContent() {
        String extractedValue = getContent();
        clear();

        return extractedValue;
    }

    /**
     * Clears the buffer's content.
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer("hello ");
     * buffer.append("world");
     *
     * buffer.clear();
     * buffer.isEmpty(); // true
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#isEmpty()
     * @see TextBuffer#extractContent()
     * @since Quark 1.1
     */
    public void clear() {
        builder.setLength(0);
    }

    /**
     * Appends a value to the buffer content.
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer();
     * buffer.append("anafro");
     * buffer.append(5);
     * buffer.append(true);
     * buffer.append('A');
     *
     * buffer.contentEquals("anafro5trueA"); // true
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     * <br><br>
     *
     * <i>Note that you don't have to append a new line character "\n"
     * to break the line. Just use special methods: {@link TextBuffer#appendLine(Object...)},
     * and {@link TextBuffer#nextLine()}.</i>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#isEmpty()
     * @see TextBuffer#extractContent()
     * @since Quark 1.1
     */
    @SafeVarargs
    public final <T> TextBuffer append(T... appendingValues) {
        for (var appendingValue : appendingValues) {
            builder.append(appendingValue);
        }

        return this;
    }

    public final <T> void appendMany(Iterable<T> appendValues, Function<T, String> valueToString, String delimiter) {
        append(Collections.join(appendValues, valueToString, delimiter));
    }

    public final <T> void appendMany(Iterable<T> appendValues, Function<T, String> valueToString) {
        appendMany(appendValues, valueToString, "");
    }

    @SafeVarargs
    public final <T> void appendIf(boolean condition, T... appendingValues) {
        if (condition) {
            append(appendingValues);
        }
    }

    public final <T> void append(Optional<T> optional, Function<T, String> valueToString) {
        optional.map(valueToString).ifPresent(this::append);
    }

    /**
     * Appends a value to the buffer content and breaks the line.
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer();
     * buffer.appendLine("My friends:");
     * for(var friend : List.of("Me", "Myself", "I")) {
     *     buffer.append("- ").appendLine(friend);
     * }
     *
     * // My friends:
     * // - Me
     * // - Myself
     * // - I
     * System.out.println(buffer);
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     * <br><br>
     *
     * <i>Note that you don't have to append an empty line ""
     * to break the line. Just a special method: {@link TextBuffer#nextLine()}.</i>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#nextLine()
     * @see TextBuffer#append(Object...)
     * @since Quark 1.1
     */
    @SafeVarargs
    public final <T> void appendLine(T... appendingLine) {
        append("\t".repeat(getTabLevel()));
        append(appendingLine);
        nextLine();
    }

    /**
     * Breaks the buffer content's line.
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer();
     * buffer.append("ana");
     * buffer.nextLine();
     * buffer.append("fro");
     *
     * //ana
     * //fro
     * System.out.print(buffer.extractContent());
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     * <br><br>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#appendLine(Object...)
     * @see TextBuffer#append(Object...)
     * @since Quark 1.1
     */
    public void nextLine() {
        append('\n');
    }

    /**
     * Returns the content of this buffer <strong>without</strong> clearing it.
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer();
     * buffer.append("ana");
     * buffer.nextLine();
     * buffer.append("fro");
     *
     * //ana
     * //fro
     * System.out.print(buffer.extractContent());
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     * <br><br>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#appendLine(Object...)
     * @see TextBuffer#append(Object...)
     * @since Quark 1.1
     */
    public String getContent() {
        return builder.toString();
    }

    /**
     * Returns the content of this buffer <strong>without</strong> clearing it.
     * The same way as {@code getContent()} would do.
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer();
     * buffer.append(4).append(1 + 1);
     * buffer.toString(); // 42
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     * <br><br>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#getContent()
     * @see TextBuffer#append(Object...)
     * @since Quark 1.1
     */
    @NotNull
    @Override
    public String toString() {
        return getContent();
    }

    @Override
    public int length() {
        return builder.length();
    }

    @Override
    public char charAt(int index) {
        return builder.charAt(index);
    }

    /**
     * Returns {@code true} if builder's content is empty, otherwise {@code false}.
     * <br><br>
     * <p>
     * To know how to use text buffers, read 's documentation.
     * <br><br>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#getContent()
     * @see TextBuffer#append(Object...)
     * @since Quark 1.1
     */
    public boolean isEmpty() {
        return builder.isEmpty();
    }

    @NotNull
    @Override
    public CharSequence subSequence(int start, int end) {
        return builder.subSequence(start, end);
    }

    /**
     * Returns the current tab level of this text buffer.
     * Tab level is an amount of tabs ({@code "\t"}) that will be appended
     * before the string passed to {@link TextBuffer#appendLine(Object...)} method.
     * <br><br>
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer();
     * buffer.increaseTabLevel();
     * buffer.increaseTabLevel();
     * buffer.increaseTabLevel();
     * buffer.getTabLevel(); // 3
     *
     * buffer.decreaseTabLevel();
     * buffer.getTabLevel(); // 2
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     * <br><br>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#getContent()
     * @see TextBuffer#append(Object...)
     * @since Quark 1.1
     */
    public int getTabLevel() {
        return tabLevel;
    }

    /**
     * Sets the current tab level of this text buffer.
     * Tab level is an amount of tabs ({@code "\t"}) that will be appended
     * before the string passed to {@link TextBuffer#appendLine(Object...)} method.
     * Buffer's tab level cannot be less than zero, so if a less value passed,
     * it will be increased to 0.
     * <br><br>
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer();
     * buffer.setTabLevel(1);
     * buffer.getTabLevel();       // 1
     * buffer.appendLine("hello"); // Appended line: \thello
     *
     * buffer.setTabLevel(3);
     * buffer.getTabLevel();       // 3
     * buffer.appendLine("world"); // Appended line: \t\t\tworld
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     * <br><br>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#getContent()
     * @see TextBuffer#append(Object...)
     * @since Quark 1.1
     */
    public void setTabLevel(int tabLevel) {
        if (tabLevel < 0) {
            tabLevel = 0;
        }

        this.tabLevel = tabLevel;
    }

    /**
     * Increases the current tab level of this text buffer.
     * Tab level is an amount of tabs ({@code "\t"}) that will be appended
     * before the string passed to {@link TextBuffer#appendLine(Object...)} method.
     * Invoking this method is an equivalent of calling {@code setTabLevel(getTabLevel() + 1)}.
     * <br><br>
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer();
     * buffer.appendLine("hello");   // hello
     * buffer.increaseTabLevel();
     * buffer.appendLine("world");   // \tworld
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     * <br><br>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#getContent()
     * @see TextBuffer#append(Object...)
     * @since Quark 1.1
     */
    public void increaseTabLevel() {
        setTabLevel(tabLevel + 1);
    }

    /**
     * Sets the current tab level back to zero.
     * Tab level is an amount of tabs ({@code "\t"}) that will be appended
     * before the string passed to {@link TextBuffer#appendLine(Object...)} method.
     * <br><br>
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer();
     * buffer.setTabLevel(random.nextInt(1000));
     * buffer.resetTabLevel();
     * buffer.getTabLevel(); // 0
     * }
     * </pre>
     * <p>
     * To know how to use text buffers, read 's documentation.
     * <br><br>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#getContent()
     * @see TextBuffer#append(Object...)
     * @since Quark 1.1
     */
    public void resetTabLevel() {
        setTabLevel(0);
    }

    /**
     * Returns {@code true} if builder's content is not empty, otherwise {@code false}.
     * <br><br>
     * <p>
     * To know how to use text buffers, read 's documentation.
     * <br><br>
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see TextBuffer#getContent()
     * @see TextBuffer#append(Object...)
     * @since Quark 1.1
     */
    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
