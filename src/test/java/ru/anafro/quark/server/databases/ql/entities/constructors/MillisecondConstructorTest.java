package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.databases.ql.entities.LongEntity;

import static org.junit.jupiter.api.Assertions.*;

class MillisecondConstructorTest {
    @Test
    public void millisecondConstructorShouldReturnMillisecondsInAMillisecond() {
        MillisecondConstructor constructor = new MillisecondConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments());

        assertEquals((long) result.getValue(), 1L);
    }
}