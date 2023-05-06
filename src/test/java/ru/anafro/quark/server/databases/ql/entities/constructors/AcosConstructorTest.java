package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

public class AcosConstructorTest {
    @Test
    public void acosZeroShouldBePiDividedByTwo() {
        AcosConstructor constructor = new AcosConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity(0))
        ));

        assertEquals((float) result.getValue(), Math.PI / 2, 1e-6);
    }

    @Test
    public void acosOneShouldBeZero() {
        AcosConstructor constructor = new AcosConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity(1))
        ));

        assertEquals((float) result.getValue(), 0, 1e-6);
    }

    @Test
    public void acosOneHalfShouldBePiOverThree() {
        AcosConstructor constructor = new AcosConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity(0.5F))
        ));

        assertEquals((float) result.getValue(), Math.PI / 3, 1e-6);
    }
}