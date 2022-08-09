package ru.anafro.quark.server.fun;

public final class Greeter {
    private Greeter() {
        //
    }

    public static void greet() {
        System.out.println(
                """
        
                     ______     __  __     ______     ______     __  __        ______     ______     ______     __   __   ______     ______   \s
                    /\\  __ \\   /\\ \\/\\ \\   /\\  __ \\   /\\  == \\   /\\ \\/ /       /\\  ___\\   /\\  ___\\   /\\  == \\   /\\ \\ / /  /\\  ___\\   /\\  == \\  \s
                    \\ \\ \\/\\_\\  \\ \\ \\_\\ \\  \\ \\  __ \\  \\ \\  __<   \\ \\  _"-.     \\ \\___  \\  \\ \\  __\\   \\ \\  __<   \\ \\ \\'/   \\ \\  __\\   \\ \\  __<  \s
                     \\ \\___\\_\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\     \\/\\_____\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\__|    \\ \\_____\\  \\ \\_\\ \\_\\\s
                      \\/___/_/   \\/_____/   \\/_/\\/_/   \\/_/ /_/   \\/_/\\/_/      \\/_____/   \\/_____/   \\/_/ /_/   \\/_/      \\/_____/   \\/_/ /_/\s
                """
        );

        System.out.println("-".repeat(50));
        System.out.println("Quark Server 1 - Your own data storage.");
        System.out.println(Compliments.random());
        System.out.println("-".repeat(50));
        System.out.println("Type 'help' command to list existing commands.");
        System.out.println("Enjoy your experience with Quark!");
        System.out.println();
    }
}
