package ru.anafro.quark.server.utils.exceptions;

/**
 * {@link CallingUtilityConstructorException} is only used as the single line
 * in utility classes when there are only static methods inside. See an example below:
 *
 * <pre>
 * {@code
 * public final class DogHugger {
 *     private DogHugger() { // Even this class is private, we could create instances inside the class.
 *         throw new CallingUtilityConstructorException(getClass()); // This line prevents it.
 *     }
 *
 *     public static void hug(User you, Dog dog) {
 *         you.sayTo(dog, "Who is a good boy?");
 *         you.grab(dog);
 *         you.wait(10, TimeUnits.SECONDS);
 *         you.release();
 *     }
 * }
 * }
 * </pre>
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class CallingUtilityConstructorException extends UtilityException {

    /**
     * Creates an exception instance.
     *
     * @param utilityClass a type of utility class.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public CallingUtilityConstructorException(Class<?> utilityClass) {
        super("new " + utilityClass.getSimpleName() + "(...) call is illegal, because " + utilityClass.getName() + " is a utility class. Call static methods inside instead.");
    }
}
