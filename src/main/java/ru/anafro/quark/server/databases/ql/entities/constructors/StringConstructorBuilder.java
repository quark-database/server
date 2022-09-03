package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.patterns.Builder;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.anafro.quark.server.databases.ql.lexer.tokens.ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER;

public class StringConstructorBuilder implements Builder<String> {
    private String name;
    private final ArrayList<Entity> arguments = Lists.empty();

    public StringConstructorBuilder() { // create table @to lower("USERS"): columns = ...;
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

        TextBuffer constructor = new TextBuffer();

        constructor.append(CONSTRUCTOR_NAME_MARKER);
        constructor.append(name);
        constructor.append('(');
        constructor.append(Lists.join(arguments.stream().map(Entity::toInstructionForm).toList()));
        constructor.append(')');

        return constructor.extractContent();
    }
}
