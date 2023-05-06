package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

public class AsinConstructorTest {
    @Test
    public void asinZeroShouldBeZero() {
        AsinConstructor constructor = new AsinConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("number", new FloatEntity(0.0F))

        ));

        assertEquals((float) result.getValue(), 0, 1e-6);
    }

    @Test
    public void asinOneHalfShouldBePiOverSix() {
        AsinConstructor constructor = new AsinConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("number", new FloatEntity(0.5F))

        ));

        assertEquals((float) result.getValue(), Math.PI / 6, 1e-6);
    }

    @Test
    public void asinOneShouldBeZero() {
        AsinConstructor constructor = new AsinConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("number", new FloatEntity(1.0F))

        ));

        assertEquals((float) result.getValue(), Math.PI / 2, 1e-6);
    }
}