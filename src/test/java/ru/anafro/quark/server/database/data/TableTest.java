package ru.anafro.quark.server.database.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.database.data.exceptions.TableExistsException;
import ru.anafro.quark.server.database.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.database.views.TableViewHeader;
import ru.anafro.quark.server.utils.collections.Collections;
import ru.anafro.quark.server.utils.collections.Iterators;

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

    @Test
    @DisplayName("Should do nothing on ensureExists on existing table")
    public void shouldDoNothingOnEnsureExistsOnExistingTable() {
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

        // When & Then
        assertDoesNotThrow(() -> Table.ensureExists("Existing Database.A"));
    }

    @Test
    @DisplayName("Should select with selector and limiter")
    public void shouldSelectWithSelectorAndLimiter() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("tag", "str")
                ),
                list(
                        record("not that"),
                        record("neither that"),
                        record("SELECT ME"),
                        record("AND ME"),
                        record("i don't want to be selected"),
                        record("PLEASE ME"),
                        record("I BEG YOU, SELECT ME")
                ));

        // When
        var actualSelection = table("Existing Database.A").select(record -> record.getString("tag").contains("ME"), new RecordIterationLimiter(1, 2));

        // Then
        assertTrue(actualSelection.same(
                record("AND ME"),
                record("PLEASE ME")
        ));
    }

    @Test
    @DisplayName("Should create TableViewHeader for a table without columns")
    public void shouldCreateTableViewHeaderForATableWithoutColumns() {
        // Given
        Database.create("Existing Database");
        Table.create("Existing Database.A");

        // When
        var actualHeader = table("Existing Database.A").createViewHeader();

        // Then
        assertEquals(new TableViewHeader(), actualHeader);
    }

    @Test
    @DisplayName("Should create TableViewHeader for a table with columns")
    public void shouldCreateTableViewHeaderForATableWithColumns() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));

        // When
        var actualHeader = table("Existing Database.A").createViewHeader();

        // Then
        assertEquals(new TableViewHeader("a", "b", "c"), actualHeader);
    }

    @Test
    @DisplayName("Should copy table")
    public void shouldCopyTable() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));

        // When
        table("Existing Database.A").copy("Existing Database.B");

        // Then
        assertTrue(Table.exists("Existing Database.A"));
        assertTrue(Table.exists("Existing Database.B"));
        assertEquals(list(column("a", "str"), column("b", "int"), column("c", "long")), table("Existing Database.B").columns());
        assertTrue(table("Existing Database.B").all().same(
                record("ABC", 123, 456L),
                record("DEF", 123, 456L),
                record("GHI", 123, 456L)
        ));
    }

    @Test
    @DisplayName("Should throw TableExistsException on copy when destination table already exists")
    public void shouldThrowTableExistsExceptionOnCopyWhenDestinationTableAlreadyExists() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));
        Table.create(
                "Existing Database.B",
                list(
                        column("d", "str"),
                        column("e", "int"),
                        column("f", "long")
                ),
                list(
                        record("XYZ", 456, 789L),
                        record("ZYX", 456, 789L),
                        record("XZY", 456, 789L)
                ));


        // When
        try {
            table("Existing Database.A").copy("Existing Database.B");

            // Then
            fail();
        } catch (TableExistsException _) {
        }
    }

    @Test
    @DisplayName("Should copy table scheme")
    public void shouldCopyTableScheme() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));

        // When
        table("Existing Database.A").copyScheme("Existing Database.B");

        // Then
        assertTrue(Table.exists("Existing Database.A"));
        assertTrue(Table.exists("Existing Database.B"));
        assertEquals(list(column("a", "str"), column("b", "int"), column("c", "long")), table("Existing Database.B").columns());
        assertEquals(0, table("Existing Database.B").all().count());
    }

    @Test
    @DisplayName("Should throw TableExistsException on copy scheme when destination table already exists")
    public void shouldThrowTableExistsExceptionOnCopySchemeWhenDestinationTableAlreadyExists() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));
        Table.create(
                "Existing Database.B",
                list(
                        column("d", "str"),
                        column("e", "int"),
                        column("f", "long")
                ),
                list(
                        record("XYZ", 456, 789L),
                        record("ZYX", 456, 789L),
                        record("XZY", 456, 789L)
                ));


        // When
        try {
            table("Existing Database.A").copyScheme("Existing Database.B");

            // Then
            fail();
        } catch (TableExistsException _) {
        }
    }

    @Test
    @DisplayName("Should return column of table")
    public void shouldReturnColumnOfTable() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));

        // When
        var actualColumn = table("Existing Database.A").getColumn("a");

        // Then
        assertEquals(column("a", "str"), actualColumn.orElseThrow());
    }

    @Test
    @DisplayName("Should return empty optional on getting not-existing column")
    public void shouldReturnEmptyOptionalOnGettingNotExistingColumn() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));

        // When
        var column = table("Existing Database.A").getColumn("x");

        // Then
        assertTrue(column.isEmpty());
    }

    @Test
    @DisplayName("Should return true on hasColumn when column exists")
    public void shouldReturnTrueOnHasColumnWhenColumnExists() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));

        // When
        var exists = table("Existing Database.A").hasColumn("a");

        // Then
        assertTrue(exists);
    }

    @Test
    @DisplayName("Should return false on hasColumn when column doesn't exists")
    public void shouldReturnFalseOnHasColumnWhenColumnDoesntExists() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));

        // When
        var exists = table("Existing Database.A").hasColumn("x");

        // Then
        assertFalse(exists);
    }

    @Test
    @DisplayName("Should return false on doesntHaveColumn when column exists")
    public void shouldReturnFalseOnDoesntHaveColumnWhenColumnExists() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));

        // When
        var doesntHave = table("Existing Database.A").doesntHaveColumn("a");

        // Then
        assertFalse(doesntHave);
    }

    @Test
    @DisplayName("Should return true on doesntHaveColumn when column doesn't exists")
    public void shouldReturnTrueOnDoesntHaveColumnWhenColumnDoesntExists() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));

        // When
        var doesntHave = table("Existing Database.A").doesntHaveColumn("x");

        // Then
        assertTrue(doesntHave);
    }

    @Test
    @DisplayName("Should return false on canNotUse when column exists")
    public void shouldReturnFalseOnCanNotUseWhenColumnExists() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));
        var changer = new TableRecordChanger("a", "@upper(:a)");

        // When
        var canNotUse = table("Existing Database.A").canNotUse(changer);

        // Then
        assertFalse(canNotUse);
    }

    @Test
    @DisplayName("Should return true on canNotUse when column doesn't exist")
    public void shouldReturnTrueOnCanNotUseWhenColumnDoesntExist() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));
        var changer = new TableRecordChanger("x", "@upper(:x)");

        // When
        var canNotUse = table("Existing Database.A").canNotUse(changer);

        // Then
        assertTrue(canNotUse);
    }

    @Test
    @DisplayName("Should return same hash code for same tables")
    public void shouldReturnSameHashCodeForSameTables() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));
        var firstTable = table("Existing Database.A");
        var secondTable = table("Existing Database.A");

        // When
        var firstHashCode = firstTable.hashCode();
        var secondHashCode = secondTable.hashCode();

        // Then
        assertEquals(firstHashCode, secondHashCode);
    }

    @Test
    @DisplayName("Should return different hash code for different tables")
    public void shouldReturnDifferentHashCodeForDifferentTables() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));
        Table.create(
                "Existing Database.B",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));
        var firstTable = table("Existing Database.A");
        var secondTable = table("Existing Database.B");

        // When
        var firstHashCode = firstTable.hashCode();
        var secondHashCode = secondTable.hashCode();

        // Then
        assertNotEquals(firstHashCode, secondHashCode);
    }

    @Test
    @DisplayName("Should return iterator of all records")
    public void shouldReturnIteratorOfAllRecords() {
        // Given
        Database.create("Existing Database");
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "int"),
                        column("c", "long")
                ),
                list(
                        record("ABC", 123, 456L),
                        record("DEF", 123, 456L),
                        record("GHI", 123, 456L)
                ));

        // When
        var actualIterator = table("Existing Database.A").iterator();

        // Then
        assertTrue(Iterators.equals(table("Existing Database.A").all().iterator(), actualIterator));
    }
}