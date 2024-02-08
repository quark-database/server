package ru.anafro.quark.server.multithreading;

import ru.anafro.quark.server.multithreading.exceptions.ThreadInterruptionException;
import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;
import ru.anafro.quark.server.utils.time.TimeSpan;

import java.util.function.Consumer;

/**
 * Threads class contains methods to handle threads.
 * Use static methods inside.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public final class Threads {

    /**
     * The utility constructor. It <strong>MUST NOT</strong> be ever called.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    private Threads() {
        throw new UtilityClassInstantiationException(getClass());
    }

    /**
     * Freezes the current thread without throwing {@link InterruptedException}.
     *
     * @param delay the delay to freeze the current thread on.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public synchronized static void sleepFor(TimeSpan delay) {
        try {
            Thread.sleep(delay.getMilliseconds());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized static void repeatWithInterval(Consumer<TimeSpan> action, TimeSpan duration, TimeSpan interval) {
        var timeLeft = duration.copy();

        while (timeLeft.isNotInstant()) {
            action.accept(timeLeft);
            Threads.sleepFor(interval);
            timeLeft.subtract(interval);
        }
    }

    public static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException exception) {
            throw new ThreadInterruptionException(thread, exception);
        }
    }

    public static void spawn(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static Thread make(String name, Runnable runnable) {
        var thread = new Thread(runnable);
        thread.setName(name);

        return thread;
    }
}
