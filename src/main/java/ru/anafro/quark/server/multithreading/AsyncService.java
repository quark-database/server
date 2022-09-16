package ru.anafro.quark.server.multithreading;

/**
 * Async services are used in {@link AsyncServicePool}.
 * Implement this interface inside your classes that will be
 * used in parallel.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public interface AsyncService extends Runnable {

    /**
     * Runs the service. Not that invoking this method outside
     * the {@link AsyncServicePool} will not run the service
     * on background. Instead, create a new async service pool,
     * add this service to the created pool and run the pool.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    void run();
}
