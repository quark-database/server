package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.databases.views.TableView;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public record InstructionResult(InstructionExecutionStatus executionStatus, String message, long milliseconds, TableView tableView) {
    @Override
    public String toString() {
        TextBuffer result = new TextBuffer();

        result.appendLine("Execution status: " + executionStatus.name());
        result.appendLine("Message: " + quoted(message));
        result.appendLine("Execution time: " + milliseconds + " milliseconds");

        if(!tableView.isEmpty()) {
            result.appendLine(tableView.toString());
        }

        return result.extractContent();
    }
}
