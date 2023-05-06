package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

class YearConstructorTest {
    @Test
    public void yearConstructorShouldReturnMillisecondsInYear() {
        YearConstructor constructor = new YearConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments());

        assertEquals((long) result.getValue(), 31_536_000_000L);
    }
}