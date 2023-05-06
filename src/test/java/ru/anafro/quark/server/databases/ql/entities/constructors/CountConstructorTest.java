package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.*;

import static org.junit.jupiter.api.Assertions.*;

class CountConstructorTest {
    @Test
    public void countOfEmptyListShouldBeZero() {
        CountConstructor constructor = new CountConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("list to count elements", new ListEntity("str"))
        ));

        assertEquals((int) result.getValue(), 0);
    }

    @Test
    public void countOfOneElementListShouldBeOne() {
        CountConstructor constructor = new CountConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("list to count elements", new ListEntity("str", new StringEntity("1")))
        ));

        assertEquals((int) result.getValue(), 1);
    }

    @Test
    public void countOfMultiElementListShouldReturnTheSizeOfList() {
        CountConstructor constructor = new CountConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("list to count elements", new ListEntity("str",
                        new StringEntity("1"),
                        new StringEntity("2"),
                        new StringEntity("3"),
                        new StringEntity("4"),
                        new StringEntity("5"),
                        new StringEntity("6"),
                        new StringEntity("7"),
                        new StringEntity("8"),
                        new StringEntity("9")
                ))
        ));

        assertEquals((int) result.getValue(), 9);
    }
}