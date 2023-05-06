package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

class CopySignConstructorTest {
    @Test
    public void copyNumberOnPositiveNumberFromPositiveNumberShouldReturnAPositiveNumber() {
        CopySignConstructor constructor = new CopySignConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("magnitude", new FloatEntity(1234.0F)),
                new InstructionEntityConstructorArgument("sign", new FloatEntity(5678.0F))
        ));

        assertEquals((float) result.getValue(), 1234.0F, 1e-6);
    }

    @Test
    public void copyNumberOnNegativeNumberFromPositiveNumberShouldReturnAPositiveNumber() {
        CopySignConstructor constructor = new CopySignConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("magnitude", new FloatEntity(-1234.0F)),
                new InstructionEntityConstructorArgument("sign", new FloatEntity(5678.0F))
        ));

        assertEquals((float) result.getValue(), 1234.0F, 1e-6);
    }

    @Test
    public void copyNumberOnPositiveNumberFromNegativeNumberShouldReturnANegativeNumber() {
        CopySignConstructor constructor = new CopySignConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("magnitude", new FloatEntity(1234.0F)),
                new InstructionEntityConstructorArgument("sign", new FloatEntity(-5678.0F))
        ));

        assertEquals((float) result.getValue(), -1234.0F, 1e-6);
    }

    @Test
    public void copyNumberOnNegativeNumberFromNegativeNumberShouldReturnANegativeNumber() {
        CopySignConstructor constructor = new CopySignConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("magnitude", new FloatEntity(-1234.0F)),
                new InstructionEntityConstructorArgument("sign", new FloatEntity(-5678.0F))
        ));

        assertEquals((float) result.getValue(), -1234.0F, 1e-6);
    }
}