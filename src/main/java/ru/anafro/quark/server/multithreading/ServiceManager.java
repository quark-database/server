package ru.anafro.quark.server.multithreading;

import ru.anafro.quark.server.multithreading.exceptions.ServiceExistsException;
import ru.anafro.quark.server.scheduling.ServiceLoader;
import ru.anafro.quark.server.utils.collections.Maps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the pool of threads
 * running at the same time in parallel.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class ServiceManager {

    private final Map<Service, Thread> services;

    /**
     * Creates a new asynchronous service pool.
     *
     * @param services the services that going to run.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ServiceManager(Service... services) {
        this.services = Collections.synchronizedMap(new HashMap<>());

        for (var service : services) {
            add(service);
        }
    }

    /**
     * Run the pool of services in parallel.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public void run() {
        Maps.forEach(services, (service, thread) -> {
            thread.start();
        });

        Maps.forEach(services, (service, thread) -> {
            Threads.join(thread);
        });
    }

    public void runAsynchronously() {
        Threads.spawn(this::run);
    }

    public void add(Service service) {
        if (services.containsKey(service)) {
            throw new ServiceExistsException(this, service);
        }

        this.services.put(service, Threads.make(service.getName(), service::start));
    }

    private void start(Service service) {
        services.get(service).start();
    }

    public void restart(Service service) {
        service.stop();
        add(service);
        start(service);
    }

    public void addAll(ServiceLoader serviceLoader) {
        for (var service : serviceLoader) {
            add(service);
        }
    }
}
