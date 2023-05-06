package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

public class Atan2ConstructorTest {
    @Test
    public void twoPositiveEqualCoordinatesShouldReturnPiOverFour() {
        Atan2Constructor constructor = new Atan2Constructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(5.0F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(5.0F))

        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(10.0F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(10.0F))

        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(123123.0F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(123123.0F))

        ));

        Entity resultD = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(0.0001F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(0.0001F))

        ));

        assertEquals((float) resultA.getValue(), Math.PI / 4, 1e-6);
        assertEquals((float) resultB.getValue(), Math.PI / 4, 1e-6);
        assertEquals((float) resultC.getValue(), Math.PI / 4, 1e-6);
        assertEquals((float) resultD.getValue(), Math.PI / 4, 1e-6);
    }

    @Test
    public void firstNegativeSecondPositiveAbsEqualsCoordinatesShouldReturn3PiOverFour() {
        Atan2Constructor constructor = new Atan2Constructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(-5.0F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(5.0F))

        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(-10.0F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(10.0F))

        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(-123123.0F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(123123.0F))

        ));

        Entity resultD = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(-0.0001F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(0.0001F))

        ));

        assertEquals((float) resultA.getValue(), -Math.PI / 4, 1e-6);
        assertEquals((float) resultB.getValue(), -Math.PI / 4, 1e-6);
        assertEquals((float) resultC.getValue(), -Math.PI / 4, 1e-6);
        assertEquals((float) resultD.getValue(), -Math.PI / 4, 1e-6);
    }

    @Test
    public void twoNegativeEqualCoordinatesShouldReturnMinus3PiOverFour() {
        Atan2Constructor constructor = new Atan2Constructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(-5.0F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(-5.0F))

        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(-10.0F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(-10.0F))

        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(-123123.0F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(-123123.0F))

        ));

        Entity resultD = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(-0.0001F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(-0.0001F))

        ));

        assertEquals((float) resultA.getValue(), -3 * Math.PI / 4, 1e-6);
        assertEquals((float) resultB.getValue(), -3 * Math.PI / 4, 1e-6);
        assertEquals((float) resultC.getValue(), -3 * Math.PI / 4, 1e-6);
        assertEquals((float) resultD.getValue(), -3 * Math.PI / 4, 1e-6);
    }

    @Test
    public void firstPositiveSecondNegativeAbsEqualsCoordinatesShouldReturnThreePiOverFour() {
        Atan2Constructor constructor = new Atan2Constructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(5.0F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(-5.0F))

        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(10.0F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(-10.0F))

        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(123123.0F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(-123123.0F))

        ));

        Entity resultD = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("y", new FloatEntity(0.0001F)),
                new InstructionEntityConstructorArgument("x", new FloatEntity(-0.0001F))

        ));

        assertEquals((float) resultA.getValue(), 3 * Math.PI / 4, 1e-6);
        assertEquals((float) resultB.getValue(), 3 * Math.PI / 4, 1e-6);
        assertEquals((float) resultC.getValue(), 3 * Math.PI / 4, 1e-6);
        assertEquals((float) resultD.getValue(), 3 * Math.PI / 4, 1e-6);
    }
}