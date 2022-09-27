package ru.anafro.quark.server.fun;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

/**
 * Greeter greets a user at the Quark's startup.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Greeter#greet()
 */
public final class Greeter {

    /**
     * This private constructor of Greeter class <strong>MUST NOT</strong> be ever
     * called, because Greeter is a utility class. Use static methods declared inside.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private Greeter() {
        throw new CallingUtilityConstructorException(getClass());
    }

    /**
     * Greets the user in the console.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static void greet() {
        System.out.println(
                """
        
                     ______     __  __     ______     ______     __  __        ______     ______     ______     __   __   ______     ______   \s
                    /\\  __ \\   /\\ \\/\\ \\   /\\  __ \\   /\\  == \\   /\\ \\/ /       /\\  ___\\   /\\  ___\\   /\\  == \\   /\\ \\ / /  /\\  ___\\   /\\  == \\  \s
                    \\ \\ \\/\\_\\  \\ \\ \\_\\ \\  \\ \\  __ \\  \\ \\  __<   \\ \\  _"-.     \\ \\___  \\  \\ \\  __\\   \\ \\  __<   \\ \\ \\'/   \\ \\  __\\   \\ \\  __<  \s
                     \\ \\___\\_\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\     \\/\\_____\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\__|    \\ \\_____\\  \\ \\_\\ \\_\\\s
                      \\/___/_/   \\/_____/   \\/_/\\/_/   \\/_/ /_/   \\/_/\\/_/      \\/_____/   \\/_____/   \\/_/ /_/   \\/_/      \\/_____/   \\/_/ /_/\s
                """
        );

        System.out.println("-".repeat(50));
        System.out.println("Quark Server 1 - Your own data storage."); // TODO: Hardcoded version.
        System.out.println(Compliments.random());
        System.out.println("-".repeat(50));
        System.out.println("Type 'help' command to list existing commands.");
        System.out.println("Enjoy your experience with Quark!");
        System.out.println();
    }
}
