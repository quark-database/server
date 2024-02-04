package ru.anafro.quark.server.console;

import ru.anafro.quark.server.multithreading.Threads;
import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;
import ru.anafro.quark.server.utils.time.TimeSpan;
import ru.anafro.quark.server.utils.types.Booleans;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import static ru.anafro.quark.server.utils.time.TimeSpan.seconds;

public final class Console {
    private static final PrintStream stream = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private static final Scanner scanner = new Scanner(System.in);

    private Console() {
        throw new UtilityClassInstantiationException(getClass());
    }

    public static void println(String message) {
        synchronized (stream) {
            stream.println(paint(message));
        }
    }

    public static void print(String message) {
        synchronized (stream) {
            stream.print(paint(message));
        }
    }

    public static void breakLine() {
        println("");
    }

    public static void clear() {
        println("\n".repeat(200));
    }

    public static String prompt(String promptMessage, Predicate<String> condition) {
        String prompt;

        do {
            Console.print(STR."\{promptMessage} > <gray>");
            prompt = scanner.nextLine();
            Console.print("</>");
        } while (!Objects.isNull(condition) && !condition.test(prompt));

        return prompt;
    }

    public static String prompt(String promptMessage) {
        return prompt(promptMessage, null);
    }

    public static boolean promptBoolean(String promptMessage) {
        return Booleans.createFromString(prompt(STR."\{promptMessage} <yellow>(y / n)</>", Booleans::canBeCreatedFromString));
    }

    public static void sleep(String message, TimeSpan delay) {
        breakLine();

        var lastMessage = new AtomicReference<>("");

        Threads.repeatWithInterval(timeLeft -> {
            var currentMessage = paint(STR."\{message} <gray>(\{timeLeft} left)</>");
            printErasable(currentMessage + " ".repeat(Math.max(0, lastMessage.get().length() - currentMessage.length())));
            lastMessage.set(currentMessage);
        }, delay, seconds(1));

        println(" ".repeat(lastMessage.get().length()));
    }

    private static void erase(int characters) {
        print("\b".repeat(characters));
    }

    private static String paint(String message) {
        var coloredMessage = message;

        for (var color : ConsoleColor.values()) {
            coloredMessage = coloredMessage.replace(STR."<\{color.name().toLowerCase()}>", color.getAnsi());
        }

        return coloredMessage.replace("</>", ConsoleColor.RESET.getAnsi());
    }

    private static void printErasable(String message) {
        print(message);
        erase(message.length());
    }

    public static boolean hasNextLine() {
        return scanner.hasNextLine();
    }
}

