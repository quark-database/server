package ru.anafro.quark.server.multithreading.exceptions;

import ru.anafro.quark.server.multithreading.Service;

public class ServiceExistsException extends MultithreadingException {
    public ServiceExistsException(Service service) {
        super(STR."The service '\{service.getName()} exists in the service manager.'");
    }
}
