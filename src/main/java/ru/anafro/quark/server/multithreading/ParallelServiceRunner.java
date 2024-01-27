package ru.anafro.quark.server.multithreading;

import ru.anafro.quark.server.multithreading.exceptions.MultithreadingException;
import ru.anafro.quark.server.scheduling.Scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the pool of threads
 * running at the same time in parallel.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class ParallelServiceRunner {

    /**
     * List of all services running at the same time.
     *
     * @since Quark 1.1
     */
    private final List<Runnable> services;

    /**
     * The pool of threads running.
     *
     * @since Quark 1.1
     */
    private final List<Thread> threads;

    /**
     * Creates a new asynchronous service pool.
     *
     * @param services the services that going to run.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ParallelServiceRunner(Runnable... services) {
        this.services = Collections.synchronizedList(new ArrayList<>(List.of(services)));
        this.threads = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Run the pool of services in parallel.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public void run() {
        for (var service : services) {
            threads.add(new Thread(service));
        }

        for (var thread : threads) {
            thread.start();
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException exception) {
                throw new MultithreadingException("%s occurred on thread joining: %s".formatted(exception.getClass().getSimpleName(), exception.getMessage()));
            }
        }
    }

    public void addAll(Scheduler taskPool) {
        for (var service : taskPool) {
            services.add(service);
        }
    }
}
