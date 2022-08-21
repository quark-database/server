package ru.anafro.quark.server.utils.patterns;

/**
 * Builder is an interface to standardize the builder
 * pattern look and feel. Implement this interface in
 * your builder class
 *
 * @param  <T> an object type this builder is going to build.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Builder#build()
 */
public interface Builder<T> {

    /**
     * Builds an object according the information stored
     * inside this builder.
     *
     * @return a built object.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Builder
     */
    T build();
}
