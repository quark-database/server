package ru.anafro.quark.server.multithreading;

import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.multithreading.exceptions.MultithreadingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the pool of threads
 * running at the same time in parallel.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class AsyncServicePool {

    /**
     * List of all services running at the same time.
     * @since Quark 1.1
     */
    private final List<AsyncService> services;

    /**
     * The pool of threads running.
     * @since Quark 1.1
     */
    private final List<Thread> pool;

    /**
     * The logger.
     * @since Quark 1.1
     */
    private final Logger logger = new Logger();

    /**
     * Creates a new asynchronous service pool.
     *
     * @param   services the services that going to run.
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public AsyncServicePool(AsyncService... services) {
        this.services = Collections.synchronizedList(List.of(services));
        this.pool = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Run the pool of services in parallel.
     *
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public void run() {
        for(var service : services) {
            pool.add(new Thread(service));
        }

        for(var thread : pool) {
            thread.start();
        }

        for(var thread : pool) {
            try {
                thread.join();
            } catch(InterruptedException exception) {
                throw new MultithreadingException("%s occurred on thread joining: %s".formatted(exception.getClass().getSimpleName(), exception.getMessage()));
            }
        }
    }

    /**
     * Returns the list of services of this pool.
     *
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @return  (see description)
     */
    public List<AsyncService> getServices() {
        return services;
    }

    /**
     * Returns the list of the threads running.
     *
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @return  (see description)
     */
    public List<Thread> getPool() {
        return pool;
    }
}
