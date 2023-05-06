package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;

import static org.junit.jupiter.api.Assertions.*;

public class CapitalizeConstructorTest {
    @Test
    public void shouldReturnCapitalized() {
        CapitalizeConstructor constructor = new CapitalizeConstructor();
        Entity resultA = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "string to capitalize", new StringEntity("a")
                )

        ));

        Entity resultB = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "string to capitalize", new StringEntity("hello")
                )

        ));

        Entity resultC = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "string to capitalize", new StringEntity("hELlO wORLd")
                )

        ));

        Entity resultD = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "string to capitalize", new StringEntity("what a WONDERFUL day, isn't it?")
                )

        ));

        Entity resultE = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "string to capitalize", new StringEntity("3 MILLION BIRDS Singing In My Soul")
                )

        ));

        Entity resultF = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "string to capitalize", new StringEntity("I AM CALM")
                )

        ));

        Entity resultG = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "string to capitalize", new StringEntity("whispering...")
                )

        ));

        Entity resultH = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "string to capitalize", new StringEntity("whispering even more...")
                )

        ));

        Entity resultI = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "string to capitalize", new StringEntity("...sh-h-h-h...")
                )

        ));

        Entity resultJ = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "string to capitalize", new StringEntity("?do you even understand?")
                )

        ));

        Entity resultK = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "string to capitalize", new StringEntity("Pass me that plate with spaghetti, please")
                )

        ));

        assertEquals(resultA.getValue(), "A");
        assertEquals(resultB.getValue(), "Hello");
        assertEquals(resultC.getValue(), "Hello world");
        assertEquals(resultD.getValue(), "What a wonderful day, isn't it?");
        assertEquals(resultE.getValue(), "3 million birds singing in my soul");
        assertEquals(resultF.getValue(), "I am calm");
        assertEquals(resultG.getValue(), "Whispering...");
        assertEquals(resultH.getValue(), "Whispering even more...");
        assertEquals(resultI.getValue(), "...sh-h-h-h...");
        assertEquals(resultJ.getValue(), "?do you even understand?");
        assertEquals(resultK.getValue(), "Pass me that plate with spaghetti, please");
    }

    @Test
    public void emptyStringCapitalizedShouldBeAnEmptyString() {
        CapitalizeConstructor constructor = new CapitalizeConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(

                new InstructionEntityConstructorArgument(
                        "string to capitalize", new StringEntity("")
                )

        ));

        assertEquals(result.getValue(), "");
    }
}