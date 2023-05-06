package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;

import static org.junit.jupiter.api.Assertions.*;

class LengthConstructorTest {
    @Test
    public void lengthOfEmptyStringShouldReturnZero() {
        LengthConstructor constructor = new LengthConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to count characters in", new StringEntity(""))
        ));

        assertEquals((int) result.getValue(), 0);
    }

    @Test
    public void lengthOfNotEmptyStringShouldReturnItsLength() {
        LengthConstructor constructor = new LengthConstructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to count characters in", new StringEntity("1"))
        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to count characters in", new StringEntity("123456789"))
        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to count characters in", new StringEntity("Hello there"))
        ));

        Entity resultD = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to count characters in", new StringEntity("   hello    "))
        ));

        assertEquals((int) resultA.getValue(), 1);
        assertEquals((int) resultB.getValue(), 9);
        assertEquals((int) resultC.getValue(), 11);
        assertEquals((int) resultD.getValue(), 12);
    }
}