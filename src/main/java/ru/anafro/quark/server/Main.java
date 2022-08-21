package ru.anafro.quark.server;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

/**
 * Main class is a starting point of Quark server. This class
 * must never be used anywhere from code, because it is being
 * handled by Java VM automatically.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Main#main(String[])
 * @see    Quark#init()
 */
public final class Main {

    /**
     * This private constructor of Main class <strong>MUST NOT</strong> be ever
     * called, because Main is a starting point.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Main#main(String[]) 
     */
    private Main() {
        throw new CallingUtilityConstructorException(getClass());
    }

    /**
     * Launches the Quark Server. This method is called automatically
     * by JVM and should never be called anywhere from code: nor your plugins or Quark itself.
     *
     * @param args the command line arguments. Ignored by Quark Server.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Main
     */
    public static void main(String[] args) {
        Quark.init();
    }
}
