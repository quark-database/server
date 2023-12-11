package ru.anafro.quark.server.multithreading;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;
import ru.anafro.quark.server.utils.exceptions.UtilityException;
import ru.anafro.quark.server.utils.time.TimeSpan;

/**
 * Threads class contains methods to handle threads.
 * Use static methods inside.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public final class Threads {

    /**
     * The utility constructor. It <strong>MUST NOT</strong> be ever called.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private Threads() {
        throw new CallingUtilityConstructorException(getClass());
    }

    /**
     * Freezes the current thread without throwing {@link InterruptedException}.
     *
     * @param seconds the amount of seconds to freeze the current thread on.
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public synchronized static void freezeFor(TimeSpan delay) {
        if(seconds < 0) {
            throw new UtilityException("Threads.freezeFor(seconds) must receive a positive value, not %f.".formatted(seconds));
        }

        try {
            Thread.sleep(delay.getMilliseconds());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
