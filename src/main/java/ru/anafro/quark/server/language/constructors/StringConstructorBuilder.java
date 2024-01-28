package ru.anafro.quark.server.language.constructors;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.language.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.patterns.Builder;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StringConstructorBuilder implements Builder<String> {
    private final ArrayList<Entity> arguments = Lists.empty();
    private String name;

    public StringConstructorBuilder() {
        //
    }

    public StringConstructorBuilder name(String name) {
        this.name = name;
        return this;
    }

    public StringConstructorBuilder arguments(List<? extends Entity> arguments) {
        this.arguments.addAll(arguments);
        return this;
    }

    public StringConstructorBuilder arguments(InstructionEntityConstructorArguments arguments) {
        this.arguments.addAll(arguments.toList());
        return this;
    }

    public StringConstructorBuilder argument(Entity argument) {
        arguments.add(argument);
        return this;
    }

    @Override
    public String build() {
        Objects.requireNonNull(name, "StringConstructorBuilder.build(...) is called, but before you need to set the name via the .name(...) method.");

        var constructor = new TextBuffer();

        constructor.append(ConstructorNameInstructionToken.CONSTRUCTOR_PREFIX);
        constructor.append(name);
        constructor.append('(');
        constructor.append(Lists.join(arguments, Entity::toInstructionForm));
        constructor.append(')');

        return constructor.getContent();
    }

    public String format() {
        return STR."<yellow>\{ConstructorNameInstructionToken.CONSTRUCTOR_PREFIX}\{name}</>(\{Lists.join(arguments, Entity::format)})";
    }
}
