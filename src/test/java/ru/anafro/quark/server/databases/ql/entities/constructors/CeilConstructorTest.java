package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

class CeilConstructorTest {
    @Test
    public void ceilZeroShouldBeZero() {
        CeilConstructor constructor = new CeilConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity(0.0F))
        ));

        assertEquals((float) result.getValue(), 0.0F, 1e-6);
    }

    @Test
    public void ceilOfTensMoreThanFiveShouldBeRoundedUp() {
        CeilConstructor constructor = new CeilConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity(0.6F))
        ));

        assertEquals((float) result.getValue(), 1.0F, 1e-6);
    }

    @Test
    public void ceilOfTensLessThanFiveShouldBeRoundedUp() {
        CeilConstructor constructor = new CeilConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity(0.2F))
        ));

        assertEquals((float) result.getValue(), 1.0F, 1e-6);
    }

    @Test
    public void ceilOfNegativeTensMoreThanFiveShouldBeRoundedUp() {
        CeilConstructor constructor = new CeilConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity(-0.6F))
        ));

        assertEquals((float) result.getValue(), 0.0F, 1e-6);
    }

    @Test
    public void ceilOfNegativeTensLessThanFiveShouldBeRoundedUp() {
        CeilConstructor constructor = new CeilConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity(-0.2F))
        ));

        assertEquals((float) result.getValue(), 0.0F, 1e-6);
    }
}