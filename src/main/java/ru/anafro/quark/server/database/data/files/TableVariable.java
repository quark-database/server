package ru.anafro.quark.server.database.data.files;

import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.exceptions.VariableFileWrongLinesCountException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.NullEntity;
import ru.anafro.quark.server.utils.files.File;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static ru.anafro.quark.server.utils.objects.Nulls.byDefault;

public class TableVariable {
    public static final String EXTENSION = ".qvariable";
    private final Table table;
    private final String name;
    private final File file;

    public TableVariable(Table table, String name) {
        this.table = table;
        this.name = name;
        this.file = table.getDirectory().getFile(VariableDirectory.NAME, name + EXTENSION);
    }

    public <T extends Entity> Optional<T> get() {
        if (file.doesntExist()) {
            return Optional.empty();
        }

        var lines = file.readLines();

        if (lines.size() != 2) {
            throw new VariableFileWrongLinesCountException(this, lines.size());
        }

        var typeName = lines.get(0);
        var entityExpression = lines.get(1);
        var variableType = Quark.type(typeName);

        @SuppressWarnings("unchecked")
        var variableContent = (T) variableType.makeEntity(entityExpression);

        return Optional.of(variableContent);
    }

    public boolean isEmpty() {
        return get().isEmpty();
    }

    public void set(Object newValue) {
        set(Entity.wrap(newValue));
    }

    public <T extends Entity> void set(T newValue) {
        var typeName = byDefault(newValue, Entity::getTypeName, "null");
        var entityExpression = byDefault(newValue, Entity::toInstructionForm, new NullEntity(typeName));

        file.write(STR."""
                \{typeName}
                \{entityExpression}""");
    }

    public <T extends Entity> void update(Function<T, T> changer) {
        get().ifPresent(value -> this.set(changer.apply((T) value)));
    }

    public Table getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public void delete() {
        file.delete();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableVariable that = (TableVariable) o;
        return Objects.equals(table, that.table) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, name);
    }
}
