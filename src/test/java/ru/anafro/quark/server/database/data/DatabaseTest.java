package ru.anafro.quark.server.database.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.database.data.exceptions.DatabaseExistsException;
import ru.anafro.quark.server.database.data.exceptions.DatabaseNotFoundException;
import ru.anafro.quark.server.utils.collections.Collections;
import ru.anafro.quark.server.utils.collections.Lists;

import static org.junit.jupiter.api.Assertions.*;
import static ru.anafro.quark.server.database.data.ColumnDescription.column;
import static ru.anafro.quark.server.database.data.Database.database;
import static ru.anafro.quark.server.database.data.Database.systemDatabase;
import static ru.anafro.quark.server.database.data.RecordField.field;
import static ru.anafro.quark.server.database.data.Table.table;
import static ru.anafro.quark.server.database.data.TableRecord.record;
import static ru.anafro.quark.server.utils.collections.Collections.list;

class DatabaseTest {

    @AfterEach
    public void tearDown() {
        database("Existing Database").delete();
        database("Existing Database 2").delete();
        database("Existing Database 3").delete();
        database("Existing Database 4").delete();
        database("Existing Database 5").delete();
        database("Existing Database 6").delete();
        database("Existing Database 6 (Copy)").delete();
        database("Existing Database 7").delete();
        database("Existing Database 7 (Copy)").delete();
        database("Existing Database 8").delete();
        database("Existing Database 8 (Copy)").delete();
        database("Existing Database 9").delete();
        database("Existing Database 9 (Copy)").delete();
        database("Existing Database 10").delete();
        database("Existing Database 11").delete();
        database("Existing Database 11 (Renamed)").delete();
        database("Existing Database 12").delete();
        database("Existing Database 13").delete();
        database("Existing Database 14").delete();
        database("Existing Database 15").delete();
        database("Existing Database 15 (Copy)").delete();
        database("Not-Existing Database").delete();
        database("Not-Existing Database 2").delete();
        database("Not-Existing Database 3").delete();
        database("Not-Existing Database 4").delete();
        database("Not-Existing Database 5").delete();
        database("Not-Existing Database 6").delete();
        database("Not-Existing Database 7").delete();
        database("Not-Existing Database 7 (Copy)").delete();
        database("Not-Existing Database 8").delete();
        database("Not-Existing Database 8 (Renamed)").delete();
        database("Not-Existing Database 9").delete();
        database("Not-Existing Database 9 (Copy)").delete();
    }

    @Test
    @DisplayName("Should create database when database does not exist")
    void shouldCreateDatabaseWhenDatabaseDoesntExist() {
        // Given
        String databaseName = "Not-Existing Database";

        // When
        Database.create(databaseName);

        // Then
        assertTrue(Database.exists(databaseName));
    }

    @Test
    @DisplayName("Should throw DatabaseExistsException when database exists")
    void shouldThrowDatabaseExistsExceptionWhenDatabaseExists() {
        // Given
        Database.create("Existing Database");

        // When
        try {
            Database.create("Existing Database");

            // Then
            fail();
        } catch (DatabaseExistsException ignored) {

        }
    }

    @Test
    @DisplayName("Should return database on create if doesnt exist when database exists")
    void shouldReturnDatabaseOnCreateIfDoesntExistWhenDatabaseExists() {
        // Given
        Database.create("Existing Database 2");
        String databaseName = "Existing Database 2";

        // When
        var database = Database.createIfDoesntExist(databaseName);

        // Then
        assertEquals(database.getName(), databaseName);
    }

    @Test
    @DisplayName("Should return database on create if doesn't exist when database doesn't exist")
    void shouldReturnDatabaseOnCreateIfDoesntExistWhenDatabaseDoesntExist() {
        // Given
        String databaseName = "Not-Existing Database 2";

        // When
        var database = Database.createIfDoesntExist(databaseName);

        // Then
        assertEquals(database.getName(), databaseName);
    }

    @Test
    @DisplayName("Should return system database when there are no databases created")
    void shouldReturnSystemDatabaseWhenThereAreNoDatabasesCreated() {
        // Given
        // A freshly installed Quark Server.

        // When
        var databases = Database.all();

        // Then
        assertTrue(Collections.equalsIgnoreOrder(list(systemDatabase()), databases));
    }


    @Test
    @DisplayName("Should return system database and created databases when there are some databases created")
    void shouldReturnSystemDatabaseAndCreatedDatabasesWhenThereAreSomeDatabasesCreated() {
        // Given
        Database.create("Not-Existing Database 3");
        Database.create("Not-Existing Database 4");
        Database.create("Not-Existing Database 5");
        Database.create("Not-Existing Database 6");

        // When
        var actualDatabases = Database.all();

        // Then
        var expectedDatabases = list(
                systemDatabase(),
                database("Not-Existing Database 3"),
                database("Not-Existing Database 4"),
                database("Not-Existing Database 5"),
                database("Not-Existing Database 6")
        );
        assertTrue(Collections.equalsIgnoreOrder(expectedDatabases, actualDatabases));
    }

    @Test
    @DisplayName("Should delete not-existing database")
    void shouldDeleteNotExistingDatabase() {
        // Given
        // A freshly installed Quark Server.

        // When
        database("Not-Existing Database 7").delete();

        // Then
        assertTrue(Database.doesntExist("Not-Existing Database 7"));
    }

    @Test
    @DisplayName("Should delete existing database")
    void shouldDeleteExistingDatabase() {
        // Given
        Database.create("Existing Database 3");

        // When
        database("Existing Database 3").delete();

        // Then
        assertTrue(Database.doesntExist("Existing Database 3"));
    }

    @Test
    @DisplayName("Should return empty list of tables for new database")
    void shouldReturnEmptyListOfTablesForNewDatabase() {
        // Given
        Database.create("Existing Database 4");

        // When
        var actualTables = database("Existing Database 4").tables();

        // Then
        var expectedTables = Lists.<Table>empty();
        assertTrue(Collections.equalsIgnoreOrder(expectedTables, actualTables));
    }

    @Test
    @DisplayName("Should return list of tables")
    void shouldReturnListOfTables() {
        // Given
        Database.create("Existing Database 5");
        Table.create("Existing Database 5.First Table");
        Table.create("Existing Database 5.Second Table");
        Table.create("Existing Database 5.Third Table");
        Table.create("Existing Database 5.Fourth Table");

        // When
        var actualTables = database("Existing Database 5").tables();

        // Then
        var expectedTables = list(
                database("Existing Database 5").table("First Table"),
                database("Existing Database 5").table("Second Table"),
                database("Existing Database 5").table("Third Table"),
                database("Existing Database 5").table("Fourth Table")
        );
        assertTrue(Collections.equalsIgnoreOrder(expectedTables, actualTables));
    }


    @Test
    @DisplayName("Should copy empty database")
    public void shouldCopyEmptyDatabase() {
        // Given
        Database.create("Existing Database 6");

        // When
        database("Existing Database 6").copy("Existing Database 6 (Copy)");

        // Then
        assertTrue(Database.exists("Existing Database 6 (Copy)"));
        assertTrue(database("Existing Database 6 (Copy)").tables().isEmpty());
    }

    @Test
    @DisplayName("Should copy database with tables")
    public void shouldCopyDatabaseWithTables() {
        // Given
        Database.create("Existing Database 7");
        Table.create("Existing Database 7.A");
        Table.create("Existing Database 7.B", column("a", "str"));
        Table.create("Existing Database 7.C", column("a", "str"));
        Table.create("Existing Database 7.D", column("a", "str"), column("b", "int"));
        table("Existing Database 7.C").insert("ABC");
        table("Existing Database 7.D").insert("ABC", 123);

        // When
        database("Existing Database 7").copy("Existing Database 7 (Copy)");

        // Then
        assertTrue(Database.exists("Existing Database 7 (Copy)"));
        assertTrue(Table.exists("Existing Database 7 (Copy).A"));
        assertTrue(table("Existing Database 7 (Copy).A").columns().isEmpty());

        assertTrue(Table.exists("Existing Database 7 (Copy).B"));
        assertEquals(table("Existing Database 7 (Copy).B").columns(), list(column("a", "str")));

        assertTrue(Table.exists("Existing Database 7 (Copy).C"));
        assertEquals(table("Existing Database 7 (Copy).C").columns(), list(column("a", "str")));
        assertTrue(Collections.equalsIgnoreOrder(table("Existing Database 7 (Copy).C").all().toList(), list(record(field("a", "ABC")))));

        assertTrue(Table.exists("Existing Database 7 (Copy).D"));
        assertEquals(table("Existing Database 7 (Copy).D").columns(), list(column("a", "str"), column("b", "int")));
        assertTrue(Collections.equalsIgnoreOrder(table("Existing Database 7 (Copy).D").all().toList(), list(record(field("a", "ABC"), field("b", 123)))));
    }

    @Test
    @DisplayName("Should throw DatabaseNotFoundException on database copy which does not exist")
    public void shouldThrowDatabaseNotFoundExceptionOnDatabaseCopyWhichDoesNotExist() {
        // Given
        // A freshly installed Quark Server.

        // When
        try {
            database("Not-Existing Database 7").copy("Not-Existing Database 7 (Copy)");

            // Then
            fail();
        } catch (DatabaseNotFoundException _) {
        }
    }

    @Test
    @DisplayName("Should throw DatabaseExistsException on database copy when destination database already exists")
    public void shouldThrowDatabaseExistsExceptionOnDatabaseCopyWhenDestinationDatabaseAlreadyExists() {
        // Given
        Database.create("Existing Database 8");
        Database.create("Existing Database 8 (Copy)");

        // When
        try {
            database("Existing Database 8").copy("Existing Database 8 (Copy)");

            // Then
            fail();
        } catch (DatabaseExistsException _) {
        }
    }

    @Test
    @DisplayName("Should clone database on copy scheme of empty database")
    public void shouldCloneDatabaseOnCopySchemeOfEmptyDatabase() {
        // Given
        Database.create("Existing Database 9");

        // When
        database("Existing Database 9").copyScheme("Existing Database 9 (Copy)");

        // Then
        assertTrue(Database.exists("Existing Database 9 (Copy)"));
    }

    @Test
    @DisplayName("Should delete all tables on database clear")
    public void shouldDeleteAllTablesOnDatabaseClear() {
        // Given
        Database.create("Existing Database 10");
        Table.create("Existing Database 10.A");
        Table.create("Existing Database 10.B", column("a", "str"));
        Table.create("Existing Database 10.C", column("a", "str"));
        table("Existing Database 10.C").insert("ABC");
        table("Existing Database 10.C").insert("DEF");
        table("Existing Database 10.C").insert("GHI");

        // When
        database("Existing Database 10").clear();

        // Then
        assertTrue(database("Existing Database 10").tables().isEmpty());
    }

    @Test
    @DisplayName("Should rename existing table")
    public void shouldRenameExistingTable() {
        // Given
        Database.create("Existing Database 11");

        // When
        database("Existing Database 11").rename("Existing Database 11 (Renamed)");

        // Then
        assertTrue(Database.doesntExist("Existing Database 11"));
        assertTrue(Database.exists("Existing Database 11 (Renamed)"));
    }

    @Test
    @DisplayName("Should throw DatabaseNotFoundException on not-existing database rename")
    public void shouldThrowDatabaseNotFoundExceptionOnNotExistingDatabaseRename() {
        // When
        try {
            database("Not-Existing Database 8").rename("Not-Existing Database 8 (Renamed)");

            // Then
            fail();
        } catch (DatabaseNotFoundException _) {
        }
    }

    @Test
    @DisplayName("Should return same hash code for same databases")
    public void shouldReturnSameHashCodeForSameDatabases() {
        // Given
        Database.create("Existing Database 12");
        var firstDatabase = database("Existing Database 12");
        var secondDatabase = database("Existing Database 12");

        // When
        var firstDatabaseHashcode = firstDatabase.hashCode();
        var secondDatabaseHashcode = secondDatabase.hashCode();

        // Then
        assertEquals(firstDatabaseHashcode, secondDatabaseHashcode);
    }

    @Test
    @DisplayName("Should return different hash codes for different databases")
    public void shouldReturnDifferentHashCodesForDifferentDatabases() {
        // Given
        Database.create("Existing Database 13");
        Database.create("Existing Database 14");
        var firstDatabase = database("Existing Database 13");
        var secondDatabase = database("Existing Database 14");

        // When
        var firstDatabaseHashcode = firstDatabase.hashCode();
        var secondDatabaseHashcode = secondDatabase.hashCode();

        // Then
        assertNotEquals(firstDatabaseHashcode, secondDatabaseHashcode);
    }

    @Test
    @DisplayName("Should copy scheme of database with tables, but clear the tables")
    public void shouldCopySchemeOfDatabaseWithTablesButClearTheTables() {
        // Given
        Database.create("Existing Database 15");
        Table.create("Existing Database 15.A");
        Table.create("Existing Database 15.B", column("a", "str"));
        Table.create("Existing Database 15.C", column("a", "str"));
        Table.create("Existing Database 15.D", column("a", "str"), column("b", "int"));
        table("Existing Database 15.C").insert("ABC");
        table("Existing Database 15.D").insert("ABC", 123);

        // When
        database("Existing Database 15").copyScheme("Existing Database 15 (Copy)");

        // Then
        assertTrue(Database.exists("Existing Database 15 (Copy)"));
        assertTrue(Table.exists("Existing Database 15 (Copy).A"));
        assertTrue(table("Existing Database 15 (Copy).A").columns().isEmpty());

        assertTrue(Table.exists("Existing Database 15 (Copy).B"));
        assertEquals(table("Existing Database 15 (Copy).B").columns(), list(column("a", "str")));

        assertTrue(Table.exists("Existing Database 15 (Copy).C"));
        assertEquals(table("Existing Database 15 (Copy).C").columns(), list(column("a", "str")));
        assertTrue(table("Existing Database 15 (Copy).C").all().toList().isEmpty());

        assertTrue(Table.exists("Existing Database 15 (Copy).D"));
        assertEquals(table("Existing Database 15 (Copy).D").columns(), list(column("a", "str"), column("b", "int")));
        assertTrue(table("Existing Database 15 (Copy).D").all().toList().isEmpty());
    }

    @Test
    @DisplayName("Should throw DatabaseNotFoundException on not-existing database scheme copy")
    public void shouldThrowDatabaseNotFoundExceptionOnNotExistingDatabaseSchemeCopy() {
        // When
        try {
            database("Not-Existing Database 9").copyScheme("Not-Existing Database 9 (Copy)");

            // Then
            fail();
        } catch (DatabaseNotFoundException _) {
        }
    }
}