package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.*;

import static org.junit.jupiter.api.Assertions.*;

public class AndConstructorTest {
    @Test
    public void falseAndFalseShouldBeFalse() {
        AndConstructor constructor = new AndConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("booleans", new ListEntity(
                        "boolean",
                        new BooleanEntity(false),
                        new BooleanEntity(false)
                ))

        ));

        assertEquals(result, new BooleanEntity(false));
    }

    @Test
    public void falseAndTrueShouldBeFalse() {
        AndConstructor constructor = new AndConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("booleans", new ListEntity(
                        "boolean",
                        new BooleanEntity(false),
                        new BooleanEntity(true)
                ))

        ));

        assertEquals(result, new BooleanEntity(false));
    }

    @Test
    public void trueAndFalseShouldBeFalse() {
        AndConstructor constructor = new AndConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("booleans", new ListEntity(
                        "boolean",
                        new BooleanEntity(true),
                        new BooleanEntity(false)
                ))

        ));

        assertEquals(result, new BooleanEntity(false));
    }

    @Test
    public void trueAndTrueShouldBeTrue() {
        AndConstructor constructor = new AndConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument("booleans", new ListEntity(
                        "boolean",
                        new BooleanEntity(true),
                        new BooleanEntity(true)
                ))

        ));

        assertEquals(result, new BooleanEntity(true));
    }
}