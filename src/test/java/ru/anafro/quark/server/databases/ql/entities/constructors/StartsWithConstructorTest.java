package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;

import static org.junit.jupiter.api.Assertions.*;

class StartsWithConstructorTest {
    @Test
    public void anyStringShouldStartWithAnEmptyString() {
        StartsWithConstructor constructor = new StartsWithConstructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string", new StringEntity("hello world!")),
                new InstructionEntityConstructorArgument("prefix", new StringEntity(""))
        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string", new StringEntity("h")),
                new InstructionEntityConstructorArgument("prefix", new StringEntity(""))
        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string", new StringEntity("")),
                new InstructionEntityConstructorArgument("prefix", new StringEntity(""))
        ));

        assertTrue((boolean) resultA.getValue());
        assertTrue((boolean) resultB.getValue());
        assertTrue((boolean) resultC.getValue());
    }

    @Test
    public void notEmptyStringIsAPrefixOfString() {
        StartsWithConstructor constructor = new StartsWithConstructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string", new StringEntity("hello world!")),
                new InstructionEntityConstructorArgument("prefix", new StringEntity("hello"))
        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string", new StringEntity("h")),
                new InstructionEntityConstructorArgument("prefix", new StringEntity("h"))
        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string", new StringEntity("    h")),
                new InstructionEntityConstructorArgument("prefix", new StringEntity("  "))
        ));

        assertTrue((boolean) resultA.getValue());
        assertTrue((boolean) resultB.getValue());
        assertTrue((boolean) resultC.getValue());
    }

    @Test
    public void notEmptyStringIsNotAPrefixOfString() {
        StartsWithConstructor constructor = new StartsWithConstructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string", new StringEntity("hello world!")),
                new InstructionEntityConstructorArgument("prefix", new StringEntity("goodbye lol"))
        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string", new StringEntity(" h")),
                new InstructionEntityConstructorArgument("prefix", new StringEntity("h"))
        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string", new StringEntity("start end")),
                new InstructionEntityConstructorArgument("prefix", new StringEntity("tart end"))
        ));

        assertFalse((boolean) resultA.getValue());
        assertFalse((boolean) resultB.getValue());
        assertFalse((boolean) resultC.getValue());
    }
}