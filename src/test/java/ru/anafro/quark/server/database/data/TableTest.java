package ru.anafro.quark.server.database.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.database.data.exceptions.TableExistsException;
import ru.anafro.quark.server.database.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.utils.collections.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static ru.anafro.quark.server.database.data.ColumnDescription.column;
import static ru.anafro.quark.server.database.data.Database.database;
import static ru.anafro.quark.server.database.data.Table.systemTable;
import static ru.anafro.quark.server.database.data.Table.table;
import static ru.anafro.quark.server.language.entities.RecordEntity.record;
import static ru.anafro.quark.server.utils.collections.Collections.list;

class TableTest {
    @AfterEach
    public void tearDown() {
        database("Existing Database").delete();
    }

    @Test
    @DisplayName("Should throw TableNotFoundException on getting not existing table")
    public void shouldThrowTableNotFoundExceptionOnGettingNotExistingTable() {
        // Given
        Database.create("Existing Database");

        // When
        try {
            table("Existing Database.X");

            // Then
            fail();
        } catch (TableNotFoundException _) {
        }
    }

    @Test
    @DisplayName("Should return a system table")
    public void shouldReturnASystemTable() {
        // When
        var table = systemTable("Tokens");

        // Then
        assertTrue(table.isSystem());
    }

    @Test
    @DisplayName("Should return true for not existing table on doesntExist()")
    public void shouldReturnTrueForNotExistingTableOnDoesntExist() {
        // Given
        Database.create("Existing Database");

        // When
        var doesntExist = Table.doesntExist("Existing Database.X");

        // Then
        assertTrue(doesntExist);
    }

    @Test
    @DisplayName("Should return false for existing table on doesntExist()")
    public void shouldReturnFalseForExistingTableOnDoesntExist() {
        // Given
        Database.create("Existing Database");
        Table.create("Existing Database.A", column("a", "str"));

        // When
        var doesntExist = Table.doesntExist("Existing Database.A");

        // Then
        assertFalse(doesntExist);
    }

    @Test
    @DisplayName("Should throw TableNotFoundException on ensure exists on not existing table")
    public void shouldThrowTableNotFoundExceptionOnEnsureExistsOnNotExistingTable() {
        // Given
        Database.create("Existing Database");

        // When
        try {
            Table.ensureExists("Existing Database.X");

            // Then
            fail();
        } catch (TableNotFoundException _) {
        }
    }

    @Test
    @DisplayName("Should delete existing table on deleteIfExists")
    public void shouldDeleteExistingTableOnDeleteIfExists() {
        // Given
        Database.create("Existing Database");
        Table.create("Existing Database.A", column("a", "str"));

        // When
        Table.deleteIfExists("Existing Database.A");

        // Then
        assertTrue(Table.doesntExist("Existing Database.A"));
    }

    @Test
    @DisplayName("Should do nothing on deleteIfExists on not existing table")
    public void shouldDoNothingOnDeleteIfExistsOnNotExistingTable() {
        // Given
        Database.create("Existing Database");

        // When
        Table.deleteIfExists("Existing Database.A");

        // Then
        assertTrue(Table.doesntExist("Existing Database.A"));
    }

    @Test
    @DisplayName("Should throw TableExistsException on creating a table which already exists")
    public void shouldThrowTableExistsExceptionOnCreatingATableWhichAlreadyExists() {
        // Given
        Database.create("Existing Database");
        Table.create("Existing Database.A", column("a", "str"));

        // When
        try {
            Table.create("Existing Database.A", column("b", "int"));

            // Then
            fail();
        } catch (TableExistsException _) {
        }
    }

    @Test
    @DisplayName("Should create table with records")
    public void shouldCreateTableWithRecords() {
        // Given
        Database.create("Existing Database");


        // When
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str")
                ),
                list(
                        record("ABC"),
                        record("DEF"),
                        record("GHI")
                ));

        // Then
        assertTrue(Table.exists("Existing Database.A"));
        assertTrue(table("Existing Database.A").all().same(
                record("ABC"),
                record("DEF"),
                record("GHI")
        ));
    }

    @Test
    @DisplayName("Should return empty list of variables on a freshly created table")
    public void shouldReturnEmptyListOfVariablesOnAFreshlyCreatedTable() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str")
                ),
                list(
                        record("ABC"),
                        record("DEF"),
                        record("GHI")
                ));

        // When
        var actualVariables = table("Existing Database.A").variables();

        // Then
        assertTrue(actualVariables.isEmpty());
    }

    @Test
    @DisplayName("Should return list of variables")
    public void shouldReturnListOfVariables() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str")
                ),
                list(
                        record("ABC"),
                        record("DEF"),
                        record("GHI")
                ));
        table("Existing Database.A").setVariable("A", 123);
        table("Existing Database.A").setVariable("B", "Hello");

        // When
        var actualVariables = table("Existing Database.A").variables();

        // Then
        assertTrue(Collections.equalsIgnoreOrder(list(
                table("Existing Database.A").getVariable("A"),
                table("Existing Database.A").getVariable("B")
        ), actualVariables));
    }

    @Test
    @DisplayName("Should rename table")
    public void shouldRenameTable() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str")
                ),
                list(
                        record("ABC"),
                        record("DEF"),
                        record("GHI")
                ));

        // When
        table("Existing Database.A").rename("B");

        // Then
        assertTrue(Table.doesntExist("Existing Database.A"));
        assertTrue(Table.exists("Existing Database.B"));
        assertEquals(list(column("a", "str")), table("Existing Database.B").columns());
        assertTrue(table("Existing Database.B").all().same(
                record("ABC"),
                record("DEF"),
                record("GHI")
        ));
    }

    
}