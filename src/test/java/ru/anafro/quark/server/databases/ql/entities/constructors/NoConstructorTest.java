package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

public class NoConstructorTest {
    @Test
    public void noConstructorShouldReturnFalse() {
        NoConstructor constructor = new NoConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments());

        assertFalse((boolean) result.getValue());
    }
}