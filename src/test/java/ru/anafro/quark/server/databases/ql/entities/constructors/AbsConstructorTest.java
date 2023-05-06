package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

public class AbsConstructorTest {
    @Test
    public void absZeroShouldBeZero() {
        AbsConstructor constructor = new AbsConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity(0.0F))
        ));

        assertEquals(result, new FloatEntity(0.0F));
    }

    @Test
    public void absPositiveShouldBePositive() {
        AbsConstructor constructor = new AbsConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity(123.0F))
        ));

        assertEquals(result, new FloatEntity(123.0F));
    }

    @Test
    public void absNegativeShouldBePositive() {
        AbsConstructor constructor = new AbsConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("number", new FloatEntity(-123.0F))
        ));

        assertEquals(result, new FloatEntity(123.0F));
    }
}