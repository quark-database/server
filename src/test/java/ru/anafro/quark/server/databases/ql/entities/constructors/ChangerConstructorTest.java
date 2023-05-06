package ru.anafro.quark.server.databases.ql.entities.constructors;

import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.databases.data.TableRecordChanger;
import ru.anafro.quark.server.databases.ql.entities.*;

import static org.junit.jupiter.api.Assertions.*;

class ChangerConstructorTest {
    @Test
    public void changerConstructorShouldReturnChanger() {
        ChangerConstructor constructor = new ChangerConstructor();
        Entity result = constructor.invoke(new InstructionEntityConstructorArguments(
                new InstructionEntityConstructorArgument("column name to change", new StringEntity("name")),
                new InstructionEntityConstructorArgument("changer expression", new StringEntity("@uppercase(:name)"))
        ));

        assertEquals(result, new ChangerEntity(new TableRecordChanger("name", "@uppercase(:name)")));
    }
}