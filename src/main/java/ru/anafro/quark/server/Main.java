package ru.anafro.quark.server;

import ru.anafro.quark.server.facade.Quark;

/**
 * Main class is a starting point of Quark server. This class
 * must never be used anywhere from code, because it is being
 * handled by Java VM automatically.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @see Main#main(String[])
 * @see Quark#run(String[])
 * @since Quark 1.1
 */
public final class Main {

    /**
     * This private constructor of Main class <strong>MUST NOT</strong> be ever
     * called, because Main is a starting point.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see Main#main(String[])
     * @since Quark 1.1
     */
    private Main() {
    }

    /**
     * Launches the Quark Server. This method is called automatically
     * by JVM and should never be called anywhere from code: nor your plugins or Quark itself.
     * Every command argument (except the name of the Quark Server runnable itself) will be
     * run as a Quark command <strong>before</strong> running the commands inside the
     * file "Commands/Commands To Run Before Start.qcommands".
     *
     * @param args the command line arguments. All the arguments
     *             will be run as Quark commands.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see Quark#run(String[])
     * @since Quark 1.1
     */
    public static void main(String[] args) {
        Quark.run(args);
    }
}
