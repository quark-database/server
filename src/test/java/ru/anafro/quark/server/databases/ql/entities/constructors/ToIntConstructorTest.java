package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.exceptions.BadInstructionEntityConstructorArgumentTypeException;

import static org.junit.jupiter.api.Assertions.*;

class ToIntConstructorTest {
    @Test
    public void toIntOfStringShouldReturnIntValueOfString() {
        ToIntConstructor constructor = new ToIntConstructor();

        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to convert to integer", new StringEntity("0"))
        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to convert to integer", new StringEntity("1"))
        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to convert to integer", new StringEntity("1234"))
        ));

        Entity resultD = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to convert to integer", new StringEntity("2147483647"))
        ));

        Entity resultE = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to convert to integer", new StringEntity("-1"))
        ));

        Entity resultF = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to convert to integer", new StringEntity("-123456789"))
        ));

        assertEquals((int) resultA.getValue(), 0);
        assertEquals((int) resultB.getValue(), 1);
        assertEquals((int) resultC.getValue(), 1234);
        assertEquals((int) resultD.getValue(), 2147483647);
        assertEquals((int) resultE.getValue(), -1);
        assertEquals((int) resultF.getValue(), -123456789);
    }

    @Test
    public void toIntOfMalformedStringShouldThrowBadInstructionEntityConstructorArgumentTypeException() {
        assertThrows(BadInstructionEntityConstructorArgumentTypeException.class, () -> {
            ToIntConstructor constructor = new ToIntConstructor();

            constructor.invoke(new InstructionEntityConstructorArguments(
                    new InstructionEntityConstructorArgument("string to convert to integer", new StringEntity("Definitely not an integer"))
            ));
        });

        assertThrows(BadInstructionEntityConstructorArgumentTypeException.class, () -> {
            ToIntConstructor constructor = new ToIntConstructor();

            constructor.invoke(new InstructionEntityConstructorArguments(
                    new InstructionEntityConstructorArgument("string to convert to integer", new StringEntity("Definitely not an integer, or not? 123"))
            ));
        });

        assertThrows(BadInstructionEntityConstructorArgumentTypeException.class, () -> {
            ToIntConstructor constructor = new ToIntConstructor();

            constructor.invoke(new InstructionEntityConstructorArguments(
                    new InstructionEntityConstructorArgument("string to convert to integer", new StringEntity(""))
            ));
        });
    }

    @Test
    public void toIntOfMalformedStringWithDefaultValueShouldReturnDefaultValue() {
        ToIntConstructor constructor = new ToIntConstructor();

        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to convert to integer", new StringEntity("Definitely not an integer")),
                new InstructionEntityConstructorArgument("default integer value if conversation fails", new IntegerEntity(42))
        ));

        assertEquals((int) resultA.getValue(), 42);
    }

    @Test
    public void toIntStringWithDefaultValueShouldReturnIntValueOfString() {
        ToIntConstructor constructor = new ToIntConstructor();

        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to convert to integer", new StringEntity("68234")),
                new InstructionEntityConstructorArgument("default integer value if conversation fails", new IntegerEntity(42))
        ));

        assertEquals((int) resultA.getValue(), 68234);
    }
}