package ru.anafro.quark.server.utils.strings;

/**
 * Quark text buffers provide more functionality than Java's "StringBuilder",
 * and makes the syntax of string buffer usage less verbose, especially in terms of buffer clearing.
 * <br><br>
 *
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
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    TextBuffer#TextBuffer() 
 * @see    TextBuffer#TextBuffer(String) 
 */
public class TextBuffer {
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#TextBuffer(String)
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * <i>Note that you don't have to pass an empty string "" to create a text buffer
     * without content inside. Just use an empty constructor: {@link TextBuffer#TextBuffer()}.</i>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#TextBuffer()
     */
    public TextBuffer(String initialString) {
        append(initialString);
    }

    /**
     * Compares the string inside this text buffer with the string
     * passed to the arguments of this method. If the buffer content and
     * the string are equal, <code>true</code> will be returned. Otherwise,
     * if the content and string are not equals, this method will return
     * <code>false</code>.
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer();
     * buffer.append("a");
     * buffer.append("na");
     * buffer.append("fro");
     *
     * buffer.contentEquals("anafro"); // true
     * }
     * </pre>
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * <i>Note that you don't have to pass an empty string "" to check the buffer's emptiness
     * Just use a special method: {@link TextBuffer#isEmpty()}.</i>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#TextBuffer()
     */
    public boolean valueEquals(String value) {
        return getContent().equals(value);
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#clear()
     * @see    TextBuffer#getContent()
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#isEmpty()
     * @see    TextBuffer#extractContent()
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * <i>Note that you don't have to append a new line character "\n"
     * to break the line. Just use special methods: {@link TextBuffer#appendLine(Object)},
     * and {@link TextBuffer#nextLine()}.</i>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#isEmpty()
     * @see    TextBuffer#extractContent()
     */
    public <T> TextBuffer append(T appendingValue) {
        builder.append(appendingValue);
        return this;
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * <i>Note that you don't have to append an empty line ""
     * to break the line. Just a special method: {@link TextBuffer#nextLine()}.</i>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#nextLine()
     * @see    TextBuffer#append(Object)
     */
    public <T> TextBuffer appendLine(T appendingLine) {
        append("\t".repeat(getTabLevel()));
        append(appendingLine);
        return nextLine();
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#appendLine(Object)
     * @see    TextBuffer#append(Object)
     */
    public TextBuffer nextLine() {
        return append('\n');
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#appendLine(Object)
     * @see    TextBuffer#append(Object)
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#getContent()
     * @see    TextBuffer#append(Object)
     */
    @Override
    public String toString() {
        return getContent();
    }

    /**
     * Returns the Java Core's {@code StringBuilder} used in this text buffer.
     * Use it if you need specific functionality.
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#getContent()
     * @see    TextBuffer#append(Object)
     */
    public StringBuilder getBuilder() {
        return builder;
    }

    /**
     * Returns {@code true} if builder's content is empty, otherwise {@code false}.
     * <br><br>
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#getContent()
     * @see    TextBuffer#append(Object)
     */
    public boolean isEmpty() {
        return builder.isEmpty();
    }

    /**
     * Returns the current tab level of this text buffer.
     * Tab level is an amount of tabs ({@code "\t"}) that will be appended
     * before the string passed to {@link TextBuffer#appendLine(Object)} method.
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#getContent()
     * @see    TextBuffer#append(Object)
     */
    public int getTabLevel() {
        return tabLevel;
    }

    /**
     * Sets the current tab level of this text buffer.
     * Tab level is an amount of tabs ({@code "\t"}) that will be appended
     * before the string passed to {@link TextBuffer#appendLine(Object)} method.
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#getContent()
     * @see    TextBuffer#append(Object)
     */
    public void setTabLevel(int tabLevel) {
        if(tabLevel < 0) {
            tabLevel = 0;
        }

        this.tabLevel = tabLevel;
    }

    /**
     * Increases the current tab level of this text buffer.
     * Tab level is an amount of tabs ({@code "\t"}) that will be appended
     * before the string passed to {@link TextBuffer#appendLine(Object)} method.
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#getContent()
     * @see    TextBuffer#append(Object)
     */
    public void increaseTabLevel() {
        setTabLevel(tabLevel + 1);
    }

    /**
     * Decreases the current tab level of this text buffer.
     * Tab level is an amount of tabs ({@code "\t"}) that will be appended
     * before the string passed to {@link TextBuffer#appendLine(Object)} method.
     * Invoking this method is an equivalent of calling {@code setTabLevel(getTabLevel() - 1)}.
     * Buffer's tab level cannot be less than zero, so if the buffer's tab level already zero
     * and {@code decreaseTabLevel()} is called, nothing will happen.
     * <br><br>
     *
     * <pre>
     * {@code
     * var buffer = new TextBuffer();
     * buffer.setTabLevel(3);
     * while(buffer.getTabLevel() != 0) {
     *     buffer.appendLine(buffer.getTabLevel());
     *     buffer.decreaseTabLevel();
     * }
     *
     * buffer.getContent();
     * //           3
     * //       2
     * //   1
     * }
     * </pre>
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#getContent()
     * @see    TextBuffer#append(Object)
     */
    public void decreaseTabLevel() {
        setTabLevel(tabLevel - 1);
    }

    /**
     * Sets the current tab level back to zero.
     * Tab level is an amount of tabs ({@code "\t"}) that will be appended
     * before the string passed to {@link TextBuffer#appendLine(Object)} method.
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
     *
     * To know how to use text buffers, read {@link TextBuffer}'s documentation.
     * <br><br>
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    TextBuffer
     * @see    TextBuffer#getContent()
     * @see    TextBuffer#append(Object)
     */
    public void resetTabLevel() {
        setTabLevel(0);
    }
}
