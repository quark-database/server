package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.databases.views.TableView;

public record InstructionResult(InstructionExecutionStatus executionStatus, String message, long milliseconds,
                                TableView tableView) {
}
