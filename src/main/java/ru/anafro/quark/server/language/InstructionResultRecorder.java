package ru.anafro.quark.server.language;

import ru.anafro.quark.server.database.exceptions.HeaderWasNotSetInInstructionResultRecorderException;
import ru.anafro.quark.server.database.views.TableView;
import ru.anafro.quark.server.database.views.TableViewHeader;
import ru.anafro.quark.server.database.views.TableViewRecorder;
import ru.anafro.quark.server.database.views.TableViewRow;

public class InstructionResultRecorder {
    private final long startTimeMilliseconds;
    private ResponseStatus responseStatus;
    private String message;
    private TableViewRecorder viewRecorder;

    public InstructionResultRecorder() {
        this.responseStatus = ResponseStatus.OK;
        this.message = "[No message]";
        this.startTimeMilliseconds = System.currentTimeMillis();
        this.viewRecorder = null;
    }

    public void status(ResponseStatus status, String message) {
        this.responseStatus = status;
        this.message = message;
    }

    public void status(ResponseStatus status) {
        this.responseStatus = status;
    }

    public void ok(String message) {
        status(ResponseStatus.OK, message);
    }

    public void serverError(String message) {
        status(ResponseStatus.SERVER_ERROR, message);
    }

    public void message(String message) {
        this.message = message;
    }

    public void header(String... columnNames) {
        header(new TableViewHeader(columnNames));
    }

    public void header(TableViewHeader header) {
        this.viewRecorder = new TableViewRecorder(header);
    }

    public boolean isTableViewHeaderSet() {
        return viewRecorder != null;
    }

    public void row(TableViewRow row) {
        if (!isTableViewHeaderSet()) {
            throw new HeaderWasNotSetInInstructionResultRecorderException();
        }

        viewRecorder.appendRow(row);
    }

    public void row(Object... cells) {
        row(new TableViewRow(cells));
    }

    public InstructionResult collectResult() {
        return new InstructionResult(responseStatus, message, System.currentTimeMillis() - startTimeMilliseconds, isTableViewHeaderSet() ? viewRecorder.collectView() : TableView.empty());
    }
}
