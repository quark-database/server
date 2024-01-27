package ru.anafro.quark.server.fun;

import ru.anafro.quark.server.console.Console;
import ru.anafro.quark.server.console.commands.HelpCommand;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;

/**
 * Greeter greets a user at the Quark's startup.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see Greeter#greet()
 * @since Quark 1.1
 */
public final class Greeter {

    /**
     * This private constructor of Greeter class <strong>MUST NOT</strong> be ever
     * called, because Greeter is a utility class. Use static methods declared inside.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    private Greeter() {
        throw new UtilityClassInstantiationException(getClass());
    }

    /**
     * Greets the user in the console.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static void greet() {
        var version = Quark.version().toString();
        var compliment = Compliments.random();
        var helpCommand = new HelpCommand();
        var port = Quark.server().getPort();

        Console.println(STR.
                """
                <blue>

                                 ################################################              \s
                                   ###############################################             \s
                                    *##############################################            \s
                                      ##############################################           \s
                                        #############################################          \s
                                          ############################################         \s
                                                                       ###############         \s
                                                                        ###############        \s
                                                                         ###############       \s
                                                                          ###############      \s
                 ######                                                    ###############     \s
                  #############                                            ###############.    \s
                   ###############                                          ###############    \s
                   ,###############                                          ###############   \s
                    ###############                                           ###############  \s
                     ###############                                           ############### \s
                      ###############                                          ################\s
                       ###############                                                #########\s
                        ###############                                                       ##
                        (###############                                                       \s
                         ###############                                                       \s
                          ###############                                                      \s
                           ###########################################                         \s
                            ############################################                       \s
                            .#############################################                     \s
                             ###############################################                   \s
                              ###############################################                  \s
                               ################################################                \s
                </>

                ------------------------------------------------------------------------------------------
                Quark Server \{version} - Your own databases.
                \{compliment}
                ------------------------------------------------------------------------------------------

                The server is running at port \{port}.
                Type '\{helpCommand}' to see available commands.
                Enjoy your experience with Quark!

                """);
    }
}
