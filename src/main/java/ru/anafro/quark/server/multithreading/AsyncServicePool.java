package ru.anafro.quark.server.multithreading;

import ru.anafro.quark.server.multithreading.exceptions.MultithreadingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AsyncServicePool {
    private final List<AsyncService> services;
    private final List<Thread> pool;

    public AsyncServicePool(AsyncService... services) {
        this.services = Collections.synchronizedList(List.of(services));
        this.pool = Collections.synchronizedList(new ArrayList<>());
    }

    public void run() {
        for(var service : services) {
            pool.add(new Thread(service::run));
        }

        for(var thread : pool) {
            try {
                thread.start();
                thread.join();
            } catch(InterruptedException exception) {
                throw new MultithreadingException("%s occurred on thread joining: %s".formatted(exception.getClass().getSimpleName(), exception.getMessage()));
            }
        }
    }

    public List<AsyncService> getServices() {
        return services;
    }

    public List<Thread> getPool() {
        return pool;
    }
}
