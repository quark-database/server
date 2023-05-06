package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.*;

import static org.junit.jupiter.api.Assertions.*;

class ConcatConstructorTest {
    @Test
    public void concatOfStringsShouldBeAConcatenatedString() {
        ConcatConstructor constructor = new ConcatConstructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("strings", new ListEntity(
                        "str",
                        new StringEntity("A"),
                        new StringEntity("B"),
                        new StringEntity("C")
                ))
        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("strings", new ListEntity(
                        "str",
                        new StringEntity("A"),
                        new StringEntity("B")
                ))
        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("strings", new ListEntity(
                        "str",
                        new StringEntity("Hello "),
                        new StringEntity(" World")
                ))
        ));

        Entity resultD = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("strings", new ListEntity(
                        "str",
                        new StringEntity(""),
                        new StringEntity(""),
                        new StringEntity(""),
                        new StringEntity(""),
                        new StringEntity(""),
                        new StringEntity(""),
                        new StringEntity("")
                ))
        ));

        Entity resultE = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("strings", new ListEntity(
                        "str",
                        new StringEntity("Single string")
                ))
        ));

        assertEquals(resultA.getValue(), "ABC");
        assertEquals(resultB.getValue(), "AB");
        assertEquals(resultC.getValue(), "Hello  World");
        assertEquals(resultD.getValue(), "");
        assertEquals(resultE.getValue(), "Single string");
    }
}