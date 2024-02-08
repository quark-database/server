package ru.anafro.quark.server.language.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.database.data.Database;
import ru.anafro.quark.server.database.data.Table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.anafro.quark.server.database.data.ColumnDescription.column;
import static ru.anafro.quark.server.database.data.ColumnModifier.modifier;
import static ru.anafro.quark.server.database.data.Database.database;
import static ru.anafro.quark.server.database.data.Table.table;
import static ru.anafro.quark.server.facade.Quark.query;
import static ru.anafro.quark.server.language.entities.RecordEntity.record;
import static ru.anafro.quark.server.utils.collections.Collections.list;

class AddModifierInstructionTest {

    @BeforeEach
    void setUp() {
        Database.create("DB");
        Table.create(
                "DB.A",
                list(
                        column("a", "str"),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("ABC", "BBB", "CCC"),
                        record("DEF", "BBB", "CCC"),
                        record("GHI", "BBB", "CCC")
                ));
    }

    @AfterEach
    void tearDown() {
        database("DB").delete();
    }

    @Test
    @DisplayName("Should add modifier")
    public void shouldAddModifier() {
        // When
        query("""
                add modifier to "DB.A":
                    column = "a",
                    modifier = @unique;
                """);

        // Then
        assertEquals(list(
                column("a", "str", modifier("unique")),
                column("b", "str"),
                column("c", "str")
        ), table("DB.A").columns());
    }
}