package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.*;

import static org.junit.jupiter.api.Assertions.*;

class NullConstructorTest {
    @Test
    public void nullConstructorOfAnyTypeShouldReturnNullEntity() {
        NullConstructor constructor = new NullConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments());

        assertEquals(result, new NullEntity("any"));
    }

    @Test
    public void nullConstructorOfTypeShouldReturnTypedNullEntity() {
        NullConstructor constructor = new NullConstructor();

        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("type of null", new StringEntity("str"))
        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("type of null", new StringEntity("int"))
        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("type of null", new StringEntity("long"))
        ));

        Entity resultD = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("type of null", new StringEntity("float"))
        ));

        assertEquals(resultA, new NullEntity("str"));
        assertEquals(resultB, new NullEntity("int"));
        assertEquals(resultC, new NullEntity("long"));
        assertEquals(resultD, new NullEntity("float"));
    }
}