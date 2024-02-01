package ru.anafro.quark.server.database.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.database.data.exceptions.DatabaseExistsException;
import ru.anafro.quark.server.utils.collections.Collections;
import ru.anafro.quark.server.utils.collections.Lists;

import static org.junit.jupiter.api.Assertions.*;
import static ru.anafro.quark.server.database.data.Database.database;
import static ru.anafro.quark.server.database.data.Database.systemDatabase;
import static ru.anafro.quark.server.utils.collections.Collections.list;

class DatabaseTest {

    @AfterEach
    public void tearDown() {
        database("Existing Database").delete();
        database("Existing Database 2").delete();
        database("Existing Database 3").delete();
        database("Existing Database 4").delete();
        database("Existing Database 5").delete();
        database("Not-Existing Database").delete();
        database("Not-Existing Database 2").delete();
        database("Not-Existing Database 3").delete();
        database("Not-Existing Database 4").delete();
        database("Not-Existing Database 5").delete();
        database("Not-Existing Database 6").delete();
    }

    @Test
    void shouldCreateDatabaseWhenDatabaseDoesntExist() {
        // Given
        String databaseName = "Not-Existing Database";

        // When
        Database.create(databaseName);

        // Then
        assertTrue(Database.exists(databaseName));
    }

    @Test
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
    void shouldReturnDatabaseOnCreateIfDoesntExistWhenDatabaseDoesntExist() {
        // Given
        String databaseName = "Not-Existing Database 2";

        // When
        var database = Database.createIfDoesntExist(databaseName);

        // Then
        assertEquals(database.getName(), databaseName);
    }

    @Test
    void shouldReturnSystemDatabaseWhenThereAreNoDatabasesCreated() {
        // Given
        // A freshly installed Quark Server.

        // When
        var databases = Database.all();

        // Then
        assertTrue(Collections.equalsIgnoreOrder(list(systemDatabase()), databases));
    }


    @Test
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
    void shouldDeleteNotExistingDatabase() {
        // Given
        // A freshly installed Quark Server.

        // When
        database("Not-Existing Database 7").delete();

        // Then
        assertTrue(Database.doesntExist("Not-Existing Database 7"));
    }

    @Test
    void shouldDeleteExistingDatabase() {
        // Given
        Database.create("Existing Database 3");

        // When
        database("Existing Database 3").delete();

        // Then
        assertTrue(Database.doesntExist("Existing Database 3"));
    }

    @Test
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
}