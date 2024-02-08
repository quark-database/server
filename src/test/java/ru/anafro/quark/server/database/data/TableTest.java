package ru.anafro.quark.server.database.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.database.data.exceptions.*;
import ru.anafro.quark.server.database.views.TableViewHeader;
import ru.anafro.quark.server.utils.collections.Collections;
import ru.anafro.quark.server.utils.collections.Iterators;

import static org.junit.jupiter.api.Assertions.*;
import static ru.anafro.quark.server.database.data.ColumnDescription.column;
import static ru.anafro.quark.server.database.data.ColumnModifier.modifier;
import static ru.anafro.quark.server.database.data.Database.database;
import static ru.anafro.quark.server.database.data.ExpressionTableRecordSelector.selector;
import static ru.anafro.quark.server.database.data.RecordFieldGenerator.generator;
import static ru.anafro.quark.server.database.data.RecordIterationLimiter.limiter;
import static ru.anafro.quark.server.database.data.Table.systemTable;
import static ru.anafro.quark.server.database.data.Table.table;
import static ru.anafro.quark.server.database.data.TableRecordChanger.changer;
import static ru.anafro.quark.server.database.data.TableRecordFinder.finder;
import static ru.anafro.quark.server.language.entities.RecordEntity.record;
import static ru.anafro.quark.server.utils.collections.Collections.list;

class TableTest {
    @BeforeEach
    public void setUp() {
        Database.create("Existing Database");
    }

    @AfterEach
    public void tearDown() {
        database("Existing Database").delete();
    }

    @Test
    @DisplayName("Should throw TableNotFoundException on getting not existing table")
    public void shouldThrowTableNotFoundExceptionOnGettingNotExistingTable() {
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
        // When
        var doesntExist = Table.doesntExist("Existing Database.X");

        // Then
        assertTrue(doesntExist);
    }

    @Test
    @DisplayName("Should return false for existing table on doesntExist()")
    public void shouldReturnFalseForExistingTableOnDoesntExist() {
        // Given
        Table.create("Existing Database.A", column("a", "str"));

        // When
        var doesntExist = Table.doesntExist("Existing Database.A");

        // Then
        assertFalse(doesntExist);
    }

    @Test
    @DisplayName("Should throw TableNotFoundException on ensure exists on not existing table")
    public void shouldThrowTableNotFoundExceptionOnEnsureExistsOnNotExistingTable() {
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
        Table.create("Existing Database.A", column("a", "str"));

        // When
        Table.deleteIfExists("Existing Database.A");

        // Then
        assertTrue(Table.doesntExist("Existing Database.A"));
    }

    @Test
    @DisplayName("Should do nothing on deleteIfExists on not existing table")
    public void shouldDoNothingOnDeleteIfExistsOnNotExistingTable() {
        // When
        Table.deleteIfExists("Existing Database.A");

        // Then
        assertTrue(Table.doesntExist("Existing Database.A"));
    }

    @Test
    @DisplayName("Should throw TableExistsException on creating a table which already exists")
    public void shouldThrowTableExistsExceptionOnCreatingATableWhichAlreadyExists() {
        // Given
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
    @DisplayName("Should throw ColumnNotFoundException on rename not-existing column")
    public void shouldThrowColumnNotFoundExceptionOnRenameNotExistingColumn() {
        // Given
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
        try {
            table("Existing Database.A").renameColumn("x", "y");

            // Then
            fail();
        } catch (ColumnNotFoundException _) {
        }
    }

    @Test
    @DisplayName("Should throw ColumnExistsException on rename where table already has column with such new name")
    public void shouldThrowColumnExistsExceptionOnRenameWhereTableAlreadyHasColumnWithSuchNewName() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("ABC", "ABC", "ABC"),
                        record("DEF", "DEF", "DEF"),
                        record("GHI", "GHI", "GHI")
                ));

        // When
        try {
            table("Existing Database.A").renameColumn("a", "c");

            // Then
            fail();
        } catch (ColumnExistsException _) {
        }
    }

    @Test
    @DisplayName("Should do nothing on ensureExists on existing table")
    public void shouldDoNothingOnEnsureExistsOnExistingTable() {
        // Given
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

    @Test
    @DisplayName("Should add column")
    public void shouldAddColumn() {
        // Given
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
        table("Existing Database.A").addColumn(column("d", "str"), generator("@concat(:a, \"XYZ\")"));

        // Then
        assertEquals(list(
                column("a", "str"),
                column("b", "int"),
                column("c", "long"),
                column("d", "str")
        ), table("Existing Database.A").columns());
        assertTrue(table("Existing Database.A").all().same(
                record("ABC", 123, 456L, "ABCXYZ"),
                record("DEF", 123, 456L, "DEFXYZ"),
                record("GHI", 123, 456L, "GHIXYZ")
        ));
    }

    @Test
    @DisplayName("Should throw ColumnExistsException on addColumn when column with such name already exists")
    public void shouldThrowColumnExistsExceptionOnAddColumnWhenColumnWithSuchNameAlreadyExists() {
        // Given
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
        try {
            table("Existing Database.A").addColumn(column("c", "str"), generator("@concat(:a, \"XYZ\")"));

            // Then
            fail();
        } catch (ColumnExistsException _) {
        }
    }

    @Test
    @DisplayName("Should add column with generating modifier without generator specified")
    public void shouldAddColumnWithGeneratingModifierWithoutGeneratorSpecified() {
        // Given
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
        table("Existing Database.A").addColumn(
                column("d", "int", modifier("incrementing"))
        );

        // Then
        assertEquals(list(
                column("a", "str"),
                column("b", "int"),
                column("c", "long"),
                column("d", "int", modifier("incrementing"))
        ), table("Existing Database.A").columns());
        assertTrue(table("Existing Database.A").all().same(
                record("ABC", 123, 456L, 1),
                record("DEF", 123, 456L, 2),
                record("GHI", 123, 456L, 3)
        ));
    }

    @Test
    @DisplayName("Should throw BadGeneratorException on addColumn with generator that does not return type of the column")
    public void shouldThrowBadGeneratorExceptionOnAddColumnWithGeneratorThatDoesNotReturnTypeOfTheColumn() {
        // Given
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
        try {
            table("Existing Database.A").addColumn(column("d", "date"), generator("@concat(:a, \"XYZ\")"));

            // Then
            fail();
        } catch (BadGeneratorException _) {
        }
    }

    @Test
    @DisplayName("Should add column with generator not returning exact column type, but type that can be casted to column type")
    public void shouldAddColumnWithGeneratorNotReturningExactColumnTypeButTypeThatCanBeCastedToColumnType() {
        // Given
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
        table("Existing Database.A").addColumn(column("d", "int"), generator("\"123\""));


        // Then
        assertEquals(list(
                column("a", "str"),
                column("b", "int"),
                column("c", "long"),
                column("d", "int")
        ), table("Existing Database.A").columns());
        assertTrue(table("Existing Database.A").all().same(
                record("ABC", 123, 456L, 123),
                record("DEF", 123, 456L, 123),
                record("GHI", 123, 456L, 123)
        ));
    }

    @Test
    @DisplayName("Should throw NeedGeneratorException on adding column with no generating modifier and not providing a generator")
    public void shouldThrowNeedGeneratorExceptionOnAddingColumnWithNoGeneratingModifierAndNotProvidingAGenerator() {
        // Given
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
        try {
            table("Existing Database.A").addColumn(column("d", "date"));

            // Then
            fail();
        } catch (NeedGeneratorException _) {
        }
    }

    @Test
    @DisplayName("Should count")
    public void shouldCount() {
        // Given
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
        var actualCount = table("Existing Database.A").count(selector("@string contains(:tag, \"ME\")"));

        // Then
        assertEquals(4, actualCount);
    }

    @Test
    @DisplayName("Should throw ColumnNotFoundException on addModifier on not-existing column")
    public void shouldThrowColumnNotFoundExceptionOnAddModifierOnNotExistingColumn() {
        // Given
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
        try {
            table("Existing Database.A").addModifier("x", modifier("incrementing"));

            // Then
            fail();
        } catch (ColumnNotFoundException _) {
        }
    }

    @Test
    @DisplayName("Should add modifier")
    public void shouldAddModifier() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str"),
                        column("b", "str")
                ),
                list(
                        record("hello", "hi"),
                        record("what's up", "i'm good"),
                        record("what you doing?", "unit testing")
                ));

        // When
        table("Existing Database.A").addModifier("a", modifier("unique"));

        // Then
        assertEquals(list(
                column("a", "str", modifier("unique")),
                column("b", "str")
        ), table("Existing Database.A").columns());
    }

    @Test
    @DisplayName("Should throw ModifierExistsException on adding modifier to column, which already has a modifier with same name")
    public void shouldThrowModifierExistsExceptionOnAddingModifierToColumnWhichAlreadyHasAModifierWithSameName() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str")
                ),
                list(
                        record("hello", "hi"),
                        record("what's up", "i'm good"),
                        record("what you doing?", "unit testing")
                ));

        // When
        try {
            table("Existing Database.A").addModifier("a", modifier("unique"));

            // Then
            fail();
        } catch (ModifierExistsException _) {
        }
    }

    @Test
    @DisplayName("Should swap columns")
    public void shouldSwapColumns() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action")
                ));

        // When
        table("Existing Database.A").swapColumns("a", "c");

        // Then
        assertEquals(list(
                column("c", "str"),
                column("b", "str"),
                column("a", "str", modifier("unique"))
        ), table("Existing Database.A").columns());
        assertTrue(table("Existing Database.A").all().same(
                record("greeting", "hi", "hello"),
                record("mood", "i'm good", "what's up"),
                record("action", "unit testing", "what you doing?")
        ));
    }

    @Test
    @DisplayName("Should throw ColumnNotFoundException when if swapping not existing columns")
    public void shouldThrowColumnNotFoundExceptionWhenIfSwappingNotExistingColumns() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action")
                ));

        // When
        try {
            table("Existing Database.A").swapColumns("a", "x");

            // Then
            fail("Swapping two columns somehow succeeded: the first exists, the second doesn't");
        } catch (ColumnNotFoundException _) {
        }

        // When
        try {
            table("Existing Database.A").swapColumns("x", "b");

            // Then
            fail("Swapping two columns somehow succeeded: the first doesn't exist, the second does");
        } catch (ColumnNotFoundException _) {
        }

        // When
        try {
            table("Existing Database.A").swapColumns("x", "y");

            // Then
            fail("Swapping two columns somehow succeeded: both column don't exist");
        } catch (ColumnNotFoundException _) {
        }
    }

    @Test
    @DisplayName("Should throw ColumnNotFoundException on table change when column of changer does not exist")
    public void shouldThrowColumnNotFoundExceptionOnTableChangeWhenColumnOfChangerDoesNotExist() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action")
                ));

        // When
        try {
            table("Existing Database.A").change(selector("@yes()"), changer("x", "@concat(:a, :c)"));

            // Then
            fail();
        } catch (ColumnNotFoundException _) {
        }
    }

    @Test
    @DisplayName("Should change records")
    public void shouldChangeRecords() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action")
                ));

        // When
        table("Existing Database.A").change(selector("@greater(@length(:c), 4)"), changer("a", "@concat(:a, \" \", :c)"));

        // Then
        assertTrue(table("Existing Database.A").all().same(
                record("hello greeting", "hi", "greeting"),
                record("what's up", "i'm good", "mood"),
                record("what you doing? action", "unit testing", "action")
        ));
    }

    @Test
    @DisplayName("Should throw ColumnNotFoundException on not-existing column deletion")
    public void shouldThrowColumnNotFoundExceptionOnNotExistingColumnDeletion() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action")
                ));

        // When
        try {
            table("Existing Database.A").deleteColumn("x");

            // Then
            fail();
        } catch (ColumnNotFoundException _) {
        }
    }

    @Test
    @DisplayName("Should delete existing column")
    public void shouldDeleteExistingColumn() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action")
                ));

        // When
        table("Existing Database.A").deleteColumn("b");

        // Then
        assertEquals(list(
                column("a", "str", modifier("unique")),
                column("c", "str")
        ), table("Existing Database.A").columns());
        assertTrue(table("Existing Database.A").all().same(
                record("hello", "greeting"),
                record("what's up", "mood"),
                record("what you doing?", "action")
        ));
    }

    @Test
    @DisplayName("Should delete records")
    public void shouldDeleteRecords() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action")
                ));

        // When
        table("Existing Database.A").delete(selector("@greater(@length(:c), 4)"));


        // Then
        assertTrue(table("Existing Database.A").all().same(
                record("what's up", "i'm good", "mood")
        ));
    }

    @Test
    @DisplayName("Should delete records with limiter")
    public void shouldDeleteRecordsWithLimiter() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action"),
                        record("bye", "have a good one", "farewell")
                ));

        // When
        table("Existing Database.A").delete(selector("@greater(@length(:c), 4)"), limiter(1, 1));

        // Then
        assertTrue(table("Existing Database.A").all().same(
                record("hello", "hi", "greeting"),
                record("what's up", "i'm good", "mood"),
                record("bye", "have a good one", "farewell")
        ));
    }

    @Test
    @DisplayName("Should delete variable")
    public void shouldDeleteVariable() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action"),
                        record("bye", "have a good one", "farewell")
                ));
        table("Existing Database.A").setVariable("v", 123);

        // When
        table("Existing Database.A").deleteVariable("v");

        // Then
        assertTrue(table("Existing Database.A").getVariable("v").isEmpty());
    }

    @Test
    @DisplayName("Should rename column")
    public void shouldRenameColumn() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action"),
                        record("bye", "have a good one", "farewell")
                ));

        // When
        table("Existing Database.A").renameColumn("b", "x");

        // Then
        assertEquals(list(
                column("a", "str", modifier("unique")),
                column("x", "str"),
                column("c", "str")
        ), table("Existing Database.A").columns());
    }

    @Test
    @DisplayName("Should exclude record by unique field")
    public void shouldExcludeRecordByUniqueField() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action"),
                        record("bye", "have a good one", "farewell")
                ));

        // When
        table("Existing Database.A").exclude(finder("a", "hello"));

        // Then
        assertTrue(table("Existing Database.A").all().same(
                record("what's up", "i'm good", "mood"),
                record("what you doing?", "unit testing", "action"),
                record("bye", "have a good one", "farewell")
        ));
    }

    @Test
    @DisplayName("Should throw BadFinderException on exclude by not unique field")
    public void shouldThrowBadFinderExceptionOnExcludeByNotUniqueField() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action"),
                        record("bye", "have a good one", "farewell")
                ));

        // When
        try {
            table("Existing Database.A").exclude(finder("b", "hello"));

            // Then
            fail();
        } catch (BadFinderException _) {
        }
    }

    @Test
    @DisplayName("Should throw ColumnNotFoundException on exclude by not existing column")
    public void shouldThrowColumnNotFoundExceptionOnExcludeByNotExistingColumn() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action"),
                        record("bye", "have a good one", "farewell")
                ));

        // When
        try {
            table("Existing Database.A").exclude(finder("x", "xxx"));

            // Then
            fail();
        } catch (ColumnNotFoundException _) {
        }
    }

    @Test
    @DisplayName("Should find existing record")
    public void shouldFindExistingRecord() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action"),
                        record("bye", "have a good one", "farewell")
                ));

        // When
        var foundRecord = table("Existing Database.A").find(finder("a", "hello"));


        // Then
        assertTrue(foundRecord.isPresent());
        assertEquals(record("hello", "hi", "greeting"), foundRecord.get());
    }

    @Test
    @DisplayName("Should not find not existing record")
    public void shouldNotFindNotExistingRecord() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action"),
                        record("bye", "have a good one", "farewell")
                ));

        // When
        var foundRecord = table("Existing Database.A").find(finder("a", "xxx"));

        // Then
        assertTrue(foundRecord.isEmpty());
    }

    @Test
    @DisplayName("Should throw ColumnNotFoundException on find by not existing column")
    public void shouldThrowColumnNotFoundExceptionOnFindByNotExistingColumn() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action"),
                        record("bye", "have a good one", "farewell")
                ));

        // When
        try {
            table("Existing Database.A").find(finder("x", "xxx"));

            // Then
            fail();
        } catch (ColumnNotFoundException _) {
        }
    }

    @Test
    @DisplayName("Should throw BadFinderException on find by not unique field")
    public void shouldThrowBadFinderExceptionOnFindByNotUniqueField() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action"),
                        record("bye", "have a good one", "farewell")
                ));

        // When
        try {
            table("Existing Database.A").find(finder("b", "hello"));

            // Then
            fail();
        } catch (BadFinderException _) {
        }
    }

    @Test
    @DisplayName("Should throw ColumnNotFoundException on reorderColumns with not-existing columns")
    public void shouldThrowColumnNotFoundExceptionOnReorderColumnsWithNotExistingColumns() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action"),
                        record("bye", "have a good one", "farewell")
                ));

        // When
        try {
            table("Existing Database.A").reorderColumns(list("a", "b", "c", "x"));

            // Then
            fail();
        } catch (ColumnNotFoundException _) {
        }
    }

    @Test
    @DisplayName("Should throw IncompleteColumnOrderException on reorderColumns with missing column")
    public void shouldThrowIncompleteColumnOrderExceptionOnReorderColumnsWithMissingColumn() {
        // Given
        Table.create(
                "Existing Database.A",
                list(
                        column("a", "str", modifier("unique")),
                        column("b", "str"),
                        column("c", "str")
                ),
                list(
                        record("hello", "hi", "greeting"),
                        record("what's up", "i'm good", "mood"),
                        record("what you doing?", "unit testing", "action"),
                        record("bye", "have a good one", "farewell")
                ));

        // When
        try {
            table("Existing Database.A").reorderColumns(list("a", "b"));

            // Then
            fail();
        } catch (IncompleteColumnOrderException _) {
        }
    }
}