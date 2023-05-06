package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;

import static org.junit.jupiter.api.Assertions.*;

class ReverseStringConstructorTest {
    @Test
    public void reverseStringOfEmptyStringShouldReturnAnEmptyString() {
        ReverseStringConstructor constructor = new ReverseStringConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to reverse", new StringEntity(""))
        ));

        assertEquals(result.getValue(), "");
    }

    @Test
    public void reverseStringOfEvenLengthStringShouldReturnAReversedString() {
        ReverseStringConstructor constructor = new ReverseStringConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to reverse", new StringEntity("123456"))
        ));

        assertEquals(result.getValue(), "654321");
    }

    @Test
    public void reverseStringOfOddLengthStringShouldReturnAReversedString() {
        ReverseStringConstructor constructor = new ReverseStringConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string to reverse", new StringEntity("12345"))
        ));

        assertEquals(result.getValue(), "54321");
    }
}