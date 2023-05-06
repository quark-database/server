package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

class CosConstructorTest {
    @Test
    public void cosZeroShouldBeOne() {
        CosConstructor constructor = new CosConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity(0.0F))
        ));

        assertEquals((float) result.getValue(), 1.0F, 1e-6);
    }

    @Test
    public void cosPiOverSixShouldBeSqrtThreeOverTwo() {
        CosConstructor constructor = new CosConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity((float) (Math.PI / 6)))
        ));

        assertEquals((float) result.getValue(), Math.sqrt(3) / 2, 1e-6);
    }

    @Test
    public void cosPiOverFourShouldBeSqrtTwoOverTwo() {
        CosConstructor constructor = new CosConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity((float) (Math.PI / 4)))
        ));

        assertEquals((float) result.getValue(), Math.sqrt(2) / 2, 1e-6);
    }

    @Test
    public void cosPiOverThreeShouldBeOneHalf() {
        CosConstructor constructor = new CosConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity((float) (Math.PI / 3)))
        ));

        assertEquals((float) result.getValue(), 1.0F / 2.0F, 1e-6);
    }

    @Test
    public void cosPiOverTwoShouldBeZero() {
        CosConstructor constructor = new CosConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity((float) (Math.PI / 2)))
        ));

        assertEquals((float) result.getValue(), 0.0F, 1e-6);
    }

    @Test
    public void cosPiShouldBeMinusOne() {
        CosConstructor constructor = new CosConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity((float) Math.PI))
        ));

        assertEquals((float) result.getValue(), -1.0F, 1e-6);
    }
}