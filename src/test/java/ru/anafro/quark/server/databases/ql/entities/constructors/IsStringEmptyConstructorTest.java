package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;

import static org.junit.jupiter.api.Assertions.*;

class IsStringEmptyConstructorTest {
    @Test
    public void isStringEmptyOnEmptyStringShouldReturnTrue() {
        IsStringEmptyConstructor constructor = new IsStringEmptyConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string", new StringEntity(""))
        ));

        assertTrue((boolean) result.getValue());
    }

    @Test
    public void isStringEmptyOnBlankStringShouldReturnFalse() {
        IsStringEmptyConstructor constructor = new IsStringEmptyConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string", new StringEntity("        \t\t\t"))
        ));

        assertFalse((boolean) result.getValue());
    }

    @Test
    public void isStringEmptyOnNotEmptyStringShouldReturnFalse() {
        IsStringEmptyConstructor constructor = new IsStringEmptyConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("string", new StringEntity("Hello, world!"))
        ));

        assertFalse((boolean) result.getValue());
    }
}