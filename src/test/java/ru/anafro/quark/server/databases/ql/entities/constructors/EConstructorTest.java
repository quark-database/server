package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

class EConstructorTest {
    @Test
    public void eConstructorShouldReturnE() {
        EConstructor constructor = new EConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments());

        assertEquals((float) result.getValue(), Math.E, 1e-6);
    }
}