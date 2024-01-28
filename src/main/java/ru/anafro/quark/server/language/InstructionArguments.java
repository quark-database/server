package ru.anafro.quark.server.language;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.*;
import ru.anafro.quark.server.database.data.exceptions.ColumnNotFoundException;
import ru.anafro.quark.server.database.data.exceptions.DatabaseNotFoundException;
import ru.anafro.quark.server.database.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.language.entities.*;
import ru.anafro.quark.server.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class InstructionArguments implements Iterable<InstructionArgument> {
    private final ArrayList<InstructionArgument> arguments = Lists.empty();

    public InstructionArguments(InstructionArgument... arguments) {
        for (var argument : arguments) {
            add(argument);
        }
    }

    public void add(InstructionArgument argument) {
        if (has(argument.name())) {
            throw new InstructionSyntaxException(this, "You already set the %s's value".formatted(argument.name()), STR."Please, remove the repeating setting of argument \{argument.name()}");
        } else {
            arguments.add(argument);
        }
    }

    public boolean has(String argumentName) {
        return getArgument(argumentName) != null;
    }

    public boolean doesntHave(String argumentName) {
        return !has(argumentName);
    }

    public <T extends Entity> T get(Class<T> type, String argumentName) {
        if (doesntHave(argumentName)) {
            throw new InstructionSyntaxException(this, "Argument %s is requested, but wasn't provided".formatted(quoted(argumentName)), "Please, add this argument when you use the instruction");
        }

        return type.cast(getArgument(argumentName).value());
    }

    public Entity get(String argumentName) {
        return get(Entity.class, argumentName);
    }

    public Entity get(InstructionParameter parameter) {
        return get(parameter.getName());
    }

    private <T extends Entity> Optional<T> tryGet(Class<T> type, String argumentName) {
        if (doesntHave(argumentName)) {
            return Optional.empty();
        }

        return Optional.of(get(type, argumentName));
    }

    public String getString(String argumentName) {
        return this.get(StringEntity.class, argumentName).getValue();
    }

    public int getInt(String argumentName) {
        return this.get(IntegerEntity.class, argumentName).getValue();
    }

    public Optional<Integer> tryGetInt(String argumentName) {
        return this.tryGet(IntegerEntity.class, argumentName).map(IntegerEntity::getValue);
    }

    public long getLong(String argumentName) {
        return this.get(LongEntity.class, argumentName).getValue();
    }

    public <T extends Entity> List<T> getList(Class<T> elementsType, String argumentName) {
        return this.get(ListEntity.class, argumentName).tryGetValueAsListOf(elementsType).orElseThrow();
    }

    public List<Entity> getList(String argumentName) {
        return getList(Entity.class, argumentName);
    }

    public TableRecordFinder getFinder(String argumentName) {
        return this.get(FinderEntity.class, argumentName).getFinder();
    }

    public ColumnModifierEntity getModifier(String argumentName) {
        return this.get(ColumnModifierEntity.class, argumentName);
    }

    public Optional<RecordFieldGenerator> tryGetGenerator(String argumentName) {
        return this.tryGet(GeneratorEntity.class, argumentName).map(GeneratorEntity::getGenerator);
    }

    public Optional<TableRecordSelector> tryGetSelector(String argumentName) {
        return this.tryGet(SelectorEntity.class, argumentName).map(SelectorEntity::getSelector);
    }

    public TableName getTableName(String argumentName) {
        return new TableName(getString(argumentName));
    }

    public <T extends Entity> Optional<List<T>> tryGetList(Class<T> elementsType, String argumentName) {
        if (doesntHave(argumentName)) {
            return Optional.empty();
        }

        return Optional.of(getList(elementsType, argumentName));
    }

    public Database getDatabase(String argumentName) {
        var databaseName = getString(argumentName);

        if (Database.doesntExist(databaseName)) {
            throw new DatabaseNotFoundException(databaseName);
        }

        return Database.byName(databaseName);
    }

    public Table getTable(String argumentName) {
        var tableName = getString(argumentName);

        if (Table.doesntExist(tableName)) {
            throw new TableNotFoundException(tableName);
        }

        return Table.byName(tableName);
    }

    public Table getTable() {
        return getTable("table");
    }

    public ColumnDescription getColumnDescription(String argumentName) {
        return this.get(ColumnEntity.class, argumentName).getColumnDescription();
    }

    public ColumnDescription getColumn(Table table, String argumentName) {
        var columnName = getString(argumentName);
        return table.getColumn(columnName).orElseThrow(() -> new ColumnNotFoundException(table, columnName));
    }

    public InstructionArgument getArgument(String argumentName) {
        for (var argument : arguments) {
            if (argument.name().equals(argumentName)) {
                return argument;
            }
        }

        return null;
    }

    @NotNull
    @Override
    public Iterator<InstructionArgument> iterator() {
        return arguments.iterator();
    }

    public TableRecordSelector getSelector(String argumentName) {
        return this.get(SelectorEntity.class, argumentName).getSelector();
    }

    public TableRecordSelector getSelector() {
        return getSelector("selector");
    }

    public TableRecordChanger getChanger(String argumentName) {
        return this.get(ChangerEntity.class, argumentName).getChanger();
    }

    public TableRecordChanger getChanger() {
        return getChanger("changer");
    }

    public TableRecord getRecord(Table table) {
        return new TableRecord(table, new ListEntity("any", this.get(RecordEntity.class, "record").getValues()));
    }

    public Stream<InstructionArgument> stream() {
        return arguments.stream();
    }
}
