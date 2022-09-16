package ru.anafro.quark.server.utils.validation;

/**
 * Implement this interface to create a validator, which can be used
 * to check values for some rules. Check out an example:
 *
 * <pre>
 * {@code
 * public class RedAppleValidator implements Validator<Apple> {
 *      @Override
 *      public boolean isValid(Apple apple) {
 *          return apple.getColor() == AppleColor.RED;
 *      }
 * }
 *
 * ...
 *
 * RedAppleValidator validator = new RedAppleValidator();
 *
 * if(validator.isValid(apple)) {
 *      anafro.say("This apple is red, I love red apples, lemme eat that! :P");
 *      anafro.eat(apple);
 * } else {
 *      anafro.say("Ew, this apple is not red! >:(");
 *      anafro.open(trashCan);
 *      anafro.throwFastidiously(apple, trashCan);
 *      anafro.close(trashCan);
 * }
 * }
 * </pre>
 *
 * @param <T> an object type this validator will receive to validate.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see     Validator<T>#isValid(T)
 */
public interface Validator<T> {
    /**
     * Validates the passed object. If object passes all the
     * rules this validator sets, {@code true} will be returned.
     * Otherwise, if object fails the validation, {@code false} will be returned.
     *
     * @param  value an object to validate
     * @return a boolean represents the validation result (see the description above).
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Validator<T>
     */
    boolean isValid(T value);
}
