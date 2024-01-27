package ru.anafro.quark.server.development;

import ru.anafro.quark.server.console.Console;
import ru.anafro.quark.server.facade.Quark;

import java.time.Duration;

public class HotReloadService implements Runnable {
    @Override
    public void run() {
        Console.println("\n\n\n<salad><bold>HOT RELOAD</></>  <italic><yellow>Quark Server is rebuilt, reloading...</></>");
        try {
            Thread.sleep(Duration.ofSeconds(2));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Quark.reload();
    }
}
