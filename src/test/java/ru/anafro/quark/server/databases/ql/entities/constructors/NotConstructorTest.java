package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.BooleanEntity;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

class NotConstructorTest {
    @Test
    public void notOfFalseShouldReturnTrue() {
        NotConstructor constructor = new NotConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("boolean to invert", new BooleanEntity(false))
        ));

        assertTrue((boolean) result.getValue());
    }

    @Test
    public void notOfTrueShouldReturnFalse() {
        NotConstructor constructor = new NotConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("boolean to invert", new BooleanEntity(true))
        ));

        assertFalse((boolean) result.getValue());
    }
}