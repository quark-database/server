package ru.anafro.quark.server.language.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.database.data.Database;
import ru.anafro.quark.server.database.data.Table;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.anafro.quark.server.database.data.ColumnDescription.column;
import static ru.anafro.quark.server.database.data.Database.database;
import static ru.anafro.quark.server.database.data.Table.table;
import static ru.anafro.quark.server.facade.Quark.query;
import static ru.anafro.quark.server.language.entities.RecordEntity.record;
import static ru.anafro.quark.server.utils.collections.Collections.list;

class ChangeInInstructionTest {

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
                        record("CHANGE ME", "BBB", "CCC"),
                        record("Just chilling", "BBB", "CCC"),
                        record("CHANGE ME", "BBB", "CCC")
                ));
    }

    @AfterEach
    void tearDown() {
        database("DB").delete();
    }

    @Test
    @DisplayName("Should change records")
    public void shouldChangeRecords() {
        // When
        query("""
                change in "DB.A":
                    selector = @selector("@equals(:a, \\"CHANGE ME\\")"),
                    changer = @changer("a", "@concat(:a, \\", AND NOW IM CHANGED\\")");
                """);

        // Then
        assertTrue(table("DB.A").all().same(
                record("CHANGE ME, AND NOW IM CHANGED", "BBB", "CCC"),
                record("Just chilling", "BBB", "CCC"),
                record("CHANGE ME, AND NOW IM CHANGED", "BBB", "CCC")
        ));
    }
}