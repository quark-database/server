package ru.anafro.quark.server.language.lexer.tokens;

/**
 * This class represents a token of Quark QL instruction.
 * For example, instruction names, numbers, strings,
 * equal signs, commas are tokens. Tokens are used on instruction
 * lexing and parsing.
 * <br><br>
 * <p>
 * On lexing, all the characters in the instruction are collected
 * to instruction tokens (some characters can be ignored, such as
 * space characters). Then, on parsing, all the tokens are going
 * to be an instruction.
 * <br><br>
 * <p>
 * In plugin programming, you don't have to use these mechanisms,
 * because all the parsing and lexing stuff happen inside Quark
 * parsers and lexers. To convert your instruction string to
 * an instruction object, use the code below:
 * <pre>
 * {@code
 * var stringInstruction = "list databases;";
 * Quark.server().getInstructionParser().parse(Quark.server().getInstructionLexer().lex(stringInstruction));
 *
 * var instruction = Quark.server().getInstructionParser().getInstruction();
 * var arguments = Quark.server().getInstructionParser().getArguments();
 *
 * instruction.action(arguments); // to run.
 * }
 * </pre>
 * <p>
 * If you only need to run your instruction, just write:
 * <pre>
 * {@code
 * Quark.runInstruction("list databases;");
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public abstract class InstructionToken {

    /**
     * The name of the token.
     *
     * @since Quark 1.1
     */
    private final String name;

    /**
     * The value of the token.
     *
     * @since Quark 1.1
     */
    private final String value;

    /**
     * Creates a new token with name and the value.
     * Since {@link InstructionToken} is an abstract class,
     * you should create a separate class for a new token.
     * In {@code super();} constructor you have to pass the
     * name of the tokens this class is going to represent,
     * but you can leave the value in the constructor parameters.
     * For example,
     *
     * <pre>
     * {@code
     * public CatInstructionToken(String catName) {
     *     super("cat", catName);
     * }
     * }
     * </pre>
     *
     * @param name  the name of the instruction token.
     * @param value the value of the instruction token.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public InstructionToken(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @return the name of the instruction token.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public String getName() {
        return name;
    }

    /**
     * @return the value of the instruction token.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public String getValue() {
        return value;
    }

    /**
     * @param name the comparing name.
     * @return {@code true}, if the name of this instruction token
     * equals the name passed in the arguments.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public boolean is(String name) {
        return name.equals(this.name);
    }

    public boolean isNot(String name) {
        return !is(name);
    }

    /**
     * @return the human-readable representation of the token.
     * <strong>Can not be converted by lexing!</strong> To convert the instruction
     * token to the form that can be converted by lexing.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    public String toString() {
        return STR."\{name}: \{value}";
    }
}
