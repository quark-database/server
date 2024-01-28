package ru.anafro.quark.server.database.language.entities.constructors.mapping;

import ru.anafro.quark.server.database.language.entities.*;
import ru.anafro.quark.server.database.language.types.EntityType;
import ru.anafro.quark.server.utils.reflection.Reflection;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Stream;

import static ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorReturnDescription.returns;
import static ru.anafro.quark.server.utils.objects.Nulls.byDefault;

public final class ConstructorClassMapper {
    private ConstructorClassMapper() {
    }

    public static EntityConstructor[] map(Class<?> type) {
        return Stream.of(Reflection.getStaticMethods(type)).map(method -> {
            var meta = getMeta(method);
            var constructorName = byDefault(meta, EntityConstructor.Meta::name, EntityConstructor.convertCamelCaseToConstructorCase(method.getName()));
            var returnType = EntityType.fromClass(method.getReturnType());
            var parameters = Stream.of(method.getParameters()).map(parameter -> {
                var isOptional = byDefault(parameter.getAnnotation(EntityConstructor.Optional.class), EntityConstructor.Optional::set, false);

                return new InstructionEntityConstructorParameter(EntityConstructor.convertCamelCaseToConstructorCase(parameter.getName()), byDefault(EntityType.fromClass(parameter.getType()), EntityType::getName, "null"), !isOptional);
            }).toList();

            if (returnType == null || parameters.stream().anyMatch(parameter -> Objects.equals(parameter.type(), "null"))) {
                return null;
            }

            return new EntityConstructor(constructorName, returns(constructorName, returnType), parameters.toArray(InstructionEntityConstructorParameter[]::new)) {

                @Override
                protected Entity invoke(InstructionEntityConstructorArguments arguments) {
                    var parameters1 = getParameters();

                    for (var parameter : parameters1) {
                        if (parameter.isRequired() && arguments.doesntHave(parameter.name())) {
                            arguments.add(parameter.name(), new NullEntity(parameter.type()));
                        }
                    }

                    var nativeArguments = arguments.getArgumentsAsList().stream().map(argument -> argument.getEntity().getValue()).toArray();

                    return Entity.wrap(Reflection.invokeStatically(method, nativeArguments));
                }
            };
        }).filter(Objects::nonNull).toArray(EntityConstructor[]::new);
    }

    private static EntityConstructor.Meta getMeta(Method method) {
        return method.getAnnotation(EntityConstructor.Meta.class);
    }
}
