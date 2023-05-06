package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

class PiConstructorTest {
    @Test
    public void piConstructorShouldReturnPi() {
        PiConstructor constructor = new PiConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments());

        assertEquals((float) result.getValue(), Math.PI, 1e-6);
    }
}