package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.databases.exceptions.HeaderWasNotSetInInstructionResultRecorderException;
import ru.anafro.quark.server.databases.views.TableView;
import ru.anafro.quark.server.databases.views.TableViewHeader;
import ru.anafro.quark.server.databases.views.TableViewRecorder;
import ru.anafro.quark.server.databases.views.TableViewRow;

public class InstructionResultRecorder {
    private InstructionExecutionStatus executionStatus;
    private String message;
    private long startTimeMilliseconds;
    private TableViewRecorder viewRecorder;

    public InstructionResultRecorder() {
        this.executionStatus = InstructionExecutionStatus.OK;
        this.message = "[No message]";
        this.startTimeMilliseconds = System.currentTimeMillis();
        this.viewRecorder = null;
    }

    public void status(InstructionExecutionStatus status, String message) {
        this.executionStatus = status;
        this.message = message;
    }

    public void status(InstructionExecutionStatus status) {
        this.executionStatus = status;
    }

    public void message(String message) {
        this.message = message;
    }

    public void header(TableViewHeader header) {
        this.viewRecorder = new TableViewRecorder(header);
    }

    public boolean isTableViewHeaderSet() {
        return viewRecorder != null;
    }

    public void appendRow(TableViewRow row) {
        if(!isTableViewHeaderSet()) {
            throw new HeaderWasNotSetInInstructionResultRecorderException();
        }

        viewRecorder.appendRow(row);
    }

    public InstructionResult collectResult() {
        return new InstructionResult(executionStatus, message, System.currentTimeMillis() - startTimeMilliseconds, isTableViewHeaderSet() ? viewRecorder.collectView() : TableView.empty());
    }
}
