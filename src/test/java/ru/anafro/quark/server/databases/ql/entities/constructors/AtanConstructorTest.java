package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

public class AtanConstructorTest {
    @Test
    public void atanZeroShouldReturnZero() {
        AtanConstructor constructor = new AtanConstructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("number", new FloatEntity(0.0F))

        ));

        assertEquals((float) resultA.getValue(), 0, 1e-6);
    }

    @Test
    public void atanOneShouldReturnPiOver4() {
        AtanConstructor constructor = new AtanConstructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("number", new FloatEntity(1.0F))

        ));

        assertEquals((float) resultA.getValue(), Math.PI / 4, 1e-6);
    }

    @Test
    public void atanSqrt3Over3ShouldReturnPiOver3() {
        AtanConstructor constructor = new AtanConstructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("number", new FloatEntity((float) (Math.sqrt(3) / 3)))

        ));

        assertEquals((float) resultA.getValue(), Math.PI / 6, 1e-6);
    }

    @Test
    public void atanSqrt3ShouldReturnPiOver3() {
        AtanConstructor constructor = new AtanConstructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("number", new FloatEntity((float) Math.sqrt(3)))

        ));

        assertEquals((float) resultA.getValue(), Math.PI / 3, 1e-6);
    }

    @Test
    public void atanInfinityShouldReturnPiOver2() {
        AtanConstructor constructor = new AtanConstructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("number", new FloatEntity(Float.POSITIVE_INFINITY))

        ));

        assertEquals((float) resultA.getValue(), Math.PI / 2, 1e-6);
    }
}