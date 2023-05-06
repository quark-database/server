package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.*;

import static org.junit.jupiter.api.Assertions.*;

class CbrtConstructorTest {
    @Test
    public void cbrtOfPositiveNumbersShouldBeCorrect() {
        CbrtConstructor constructor = new CbrtConstructor();

        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "number", new FloatEntity(0.0F)
                )

        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "number", new FloatEntity(8.0F)
                )

        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "number", new FloatEntity(27.0F)
                )

        ));

        Entity resultD = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "number", new FloatEntity(1234.0F)
                )

        ));

        assertEquals((float) resultA.getValue(), 0, 1e-6);
        assertEquals((float) resultB.getValue(), 2, 1e-6);
        assertEquals((float) resultC.getValue(), 3, 1e-6);
        assertEquals((float) resultD.getValue(), 10.726014668827323, 1e-6);
    }

    @Test
    public void cbrtOfNegativeNumbersShouldBeCorrect() {
        CbrtConstructor constructor = new CbrtConstructor();

        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "number", new FloatEntity(-8.0F)
                )

        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "number", new FloatEntity(-27.0F)
                )

        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "number", new FloatEntity(-1234.0F)
                )

        ));

        assertEquals((float) resultA.getValue(), -2, 1e-6);
        assertEquals((float) resultB.getValue(), -3, 1e-6);
        assertEquals((float) resultC.getValue(), -10.726014668827323, 1e-6);
    }
}