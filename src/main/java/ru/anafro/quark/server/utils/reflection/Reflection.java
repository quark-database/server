package ru.anafro.quark.server.utils.reflection;

import ru.anafro.quark.server.utils.files.File;
import ru.anafro.quark.server.utils.streams.IOStreams;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.anafro.quark.server.utils.arrays.Arrays.array;
import static ru.anafro.quark.server.utils.arrays.Arrays.map;

public final class Reflection {
    private static final Map<Class<?>, Class<?>> wrappers = new HashMap<>();

    static {
        wrappers.put(Boolean.TYPE, Boolean.class);
        wrappers.put(Byte.TYPE, Byte.class);
        wrappers.put(Character.TYPE, Character.class);
        wrappers.put(Short.TYPE, Short.class);
        wrappers.put(Integer.TYPE, Integer.class);
        wrappers.put(Long.TYPE, Long.class);
        wrappers.put(Double.TYPE, Double.class);
        wrappers.put(Float.TYPE, Float.class);
        wrappers.put(Void.TYPE, Void.class);
    }

    private Reflection() {
    }

    public static Method[] getStaticMethods(Class<?> type) {
        return type.getDeclaredMethods();
    }

    public static Class<?> getWrapperType(Class<?> primitiveType) {
        if (!primitiveType.isPrimitive()) {
            return null;
        }

        return wrappers.get(primitiveType);
    }

    public static boolean areRelatives(Class<?> firstType, Class<?> secondType) {
        return firstType.isAssignableFrom(secondType) || secondType.isAssignableFrom(firstType);
    }

    public static boolean isPrimitiveType(Class<?> wrapperType, Class<?> primitiveType) {
        var wrapperTypeOfPrimitive = getWrapperType(primitiveType);
        if (wrapperTypeOfPrimitive == null) {
            return false;
        }

        return wrapperTypeOfPrimitive.equals(wrapperType);
    }

    public static boolean hasPrimitiveType(Object wrapperObject, Class<?> primitiveType) {
        return isPrimitiveType(wrapperObject.getClass(), primitiveType);
    }

    public static <T> Constructor<T> getConstructor(Class<T> type, Class<?>... parameters) {
        try {
            return type.getConstructor(parameters);
        } catch (NoSuchMethodException exception) {
            throw new ReflectionException(exception.getMessage());
        }
    }

    public static <T> T newInstance(Class<T> type, Object... arguments) {
        var argumentTypes = convertToTypeArray(arguments);

        try {
            return newInstance(type.getDeclaredConstructor(argumentTypes), arguments);
        } catch (NoSuchMethodException exception) {
            throw new ReflectionException(exception.getMessage());
        }
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... arguments) {
        try {
            return constructor.newInstance(arguments);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException exception) {
            throw new ReflectionException(exception.getMessage());
        }
    }

    public static boolean doesntHaveParameters(Method method, Class<?>... parameterTypes) {
        if (method.getParameterCount() != parameterTypes.length) {
            return true;
        }

        for (int parameterIndex = 0; parameterIndex < parameterTypes.length; parameterIndex++) {
            var desiredType = parameterTypes[parameterIndex];
            var actualType = method.getParameterTypes()[parameterIndex];

            if (!areRelatives(desiredType, actualType) && !isPrimitiveType(desiredType, actualType)) {
                return true;
            }
        }

        return false;
    }

    public static Object invoke(Object target, Method method, Object... arguments) {
        var argumentTypes = convertToTypeArray(arguments);

        if (doesntHaveParameters(method, argumentTypes)) {
            var signature = getSignature(method);
            var argumentsSignature = getArgumentSignature(argumentTypes);

            throw new ReflectionException(STR."""
                    Invalid method invocation:
                    The method \{signature} was invoked with arguments having types:
                    \{argumentsSignature}
                    """);
        }

        var upcastedArguments = map(arguments, (argument, index) -> {
            var parameterType = method.getParameterTypes()[index];

            if (hasPrimitiveType(argument, parameterType)) {
                return argument;
            }

            return parameterType.cast(argument);
        });

        try {
            return method.invoke(target, upcastedArguments);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            throw new ReflectionException(exception.getMessage());
        }
    }

    public static Object invokeStatically(Method method, Object... arguments) {
        return invoke(null, method, arguments);
    }

    public static <T> Class<? extends T> loadClass(Class<T> type, URLClassLoader classLoader, String classPath) {
        try {
            return Class.forName(classPath, true, classLoader).asSubclass(type);
        } catch (ClassNotFoundException message) {
            throw new ReflectionException(message.getMessage());
        }
    }

    public static boolean hasAnnotation(Method method, Class<? extends Annotation> annotation) {
        return method.isAnnotationPresent(annotation);
    }

    public static URLClassLoader createURLClassLoader(Class<?> invoker, File jar) {
        return new URLClassLoader(
                array(jar.getURL()),
                invoker.getClassLoader()
        );
    }

    public static String readResource(URLClassLoader classLoader, String filePath) {
        try {
            return IOStreams.readWholeString(classLoader.getResourceAsStream(filePath));
        } catch (IOException exception) {
            throw new ReflectionException(exception.getMessage());
        }
    }

    public static String getSignature(Method method) {
        var modifiers = getModifiersSignature(method);
        var parameters = getParametersSignature(method);
        var exceptions = getThrowsSignature(method);
        var annotations = getAnnotationsSignature(method);
        var returnType = method.getReturnType().getSimpleName();
        var name = method.getName();

        return STR."\{annotations} \{modifiers} \{returnType} \{name}(\{parameters}) \{exceptions}";
    }

    public static String getArgumentSignature(Class<?>... argumentTypes) {
        return Stream.of(argumentTypes).map(Class::getSimpleName).collect(Collectors.joining(", "));
    }

    private static Class<?>[] convertToTypeArray(Object... objects) {
        return Stream.of(objects).map(Object::getClass).toArray(Class[]::new);
    }

    private static String getAnnotationsSignature(Method method) {
        var annotations = method.getAnnotations();

        return Stream.of(annotations).map(annotation -> STR."@\{annotation.getClass().getSimpleName()}").collect(Collectors.joining("\n"));
    }

    private static String getModifiersSignature(Method method) {
        return Stream.of(method.getModifiers()).map(Modifier::toString).collect(Collectors.joining(" "));
    }

    private static String getParametersSignature(Method method) {
        return Stream.of(method.getParameters()).map(Reflection::getParameterSignature).collect(Collectors.joining(", "));
    }

    private static String getParameterSignature(Parameter parameter) {
        var type = parameter.getType().getSimpleName();
        var name = parameter.getName();

        return STR."\{type} \{name}";
    }

    private static String getThrowsSignature(Method method) {
        var exceptionTypes = method.getExceptionTypes();

        if (exceptionTypes.length == 0) {
            return "";
        }

        return STR."throws \{Stream.of(exceptionTypes).map(Class::getSimpleName).collect(Collectors.joining(", "))}";
    }

    public static boolean instanceOf(Object object, Class<?> type) {
        return type.isInstance(object);
    }
}
