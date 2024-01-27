package ru.anafro.quark.server.database.exceptions;

public class HeaderWasNotSetInInstructionResultRecorderException extends DatabaseException {
    public HeaderWasNotSetInInstructionResultRecorderException() {
        super("Instruction is trying to add a row to the execution result, but there is no table view header in it. Did you forget to add 'result.tableViewHeader(...)'?");
    }
}
