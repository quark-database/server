package ru.anafro.quark.server.fun;

import ru.anafro.quark.server.utils.arrays.Arrays;

/**
 * Compliments are loved by <s>almost</s> everyone! Why don't we
 * say compliments to users? Quark dealt with this too! You
 * can use the compliments inside to remind users how cool they are.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see Compliments#random()
 * @since Quark 1.1
 */
public class Compliments {
    private static final String[] COMPLIMENTS = {
            "Lie down, chill out, make yourself at home.",
            "Beep-beep. Booting up.",
            "Have a pleasant journey with us!",
            "Fasten your seatbelt, because our place is about to take off! Thanks for traveling with Quark!",
            "Quark (not quack) is here. Totally not sponsored by ducks.",
            "Hi! Gosh, you look so handsome today! ;)",
            "Keep smiling!",
            "Your smile is contagious.",
            "You have the best laugh.",
            "You light up the room.",
            "You have a great sense of humor.",
            "If cartoon bluebirds were real, a couple of 'em would be sitting on your shoulders singing right now.",
            "You're like sunshine on a rainy day.",
            "You bring out the best in other people.",
            "Colors seem brighter when you're around.",
            "You're more fun than a ball pit filled with candy.",
            "Jokes are funnier when you tell them.",
            "You're a candle in the darkness.",
            "You're more fun than bubble wrap.",
            "You're like a breath of fresh air.",
            "You're someone's reason to smile.",
            "Is that your picture next to \"charming\" in the dictionary?",
            "You are so brave.",
            "You are beautiful on the inside and outside.",
            "You're a great listener.",
            "You're inspiring.",
            "When you make up your mind, nothing stands in your way.",
            "You're a smart cookie.",
            "Your perspective is refreshing.",
            "Your ability to recall random factoids at just the right times is impressive.",
            "You have the best ideas.",
            "Everyone gets knocked down sometimes; only people like you get back up again and keep going.",
            "I am so proud of you, and I hope you are too!",
            "You are making a difference.",
            "You deserve a hug right now.",
            "You're a great example to others.",
            "Actions speak louder than words, and yours tell an incredible story.",
    };

    /**
     * Returns a random compliment.
     *
     * @return a compliment.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static String random() {
        return Arrays.pick(COMPLIMENTS);
    }
}
