package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import static org.junit.jupiter.api.Assertions.*;

class YesConstructorTest {
    @Test
    public void yesConstructorShouldReturnTrue() {
        YesConstructor constructor = new YesConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments());

        assertTrue((boolean) result.getValue());
    }
}