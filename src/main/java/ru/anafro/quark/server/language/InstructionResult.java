package ru.anafro.quark.server.language;

import ru.anafro.quark.server.database.views.TableView;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public record InstructionResult(ResponseStatus responseStatus, String message, long milliseconds, TableView tableView) {
    @Override
    public String toString() {
        TextBuffer result = new TextBuffer();

        result.appendLine(STR."Execution status: \{responseStatus.name()}");
        result.appendLine(STR."Message: \{quoted(message)}");
        result.appendLine(STR."Execution time: \{milliseconds} milliseconds");

        if (!tableView.isEmpty()) {
            result.appendLine(tableView.toString());
        }

        return result.extractContent();
    }
}
