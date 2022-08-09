package ru.anafro.quark.server.databases;

import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.databases.exceptions.NoSuchInstructionParameterException;
import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.views.TableView;
import ru.anafro.quark.server.networking.Server;

public abstract class Instruction {
    public final String name;
    public final String permission;
    public final InstructionParameters parameters;

    public Instruction(String name, String permission, InstructionParameter... parameters) {
        this.name = name;
        this.permission = permission;
        this.parameters = new InstructionParameters(parameters);
    }

    public abstract void action(InstructionArguments arguments, Server server, InstructionResultRecorder result);

    public InstructionResult execute(InstructionArguments arguments, Server server) {
        try {
            InstructionResultRecorder resultRecorder = new InstructionResultRecorder();

            this.action(arguments, server, resultRecorder);

            return resultRecorder.collectResult();
        } catch(QuarkException exception) {
            return new InstructionResult(InstructionExecutionStatus.SYNTAX_ERROR, exception.getMessage(), 0, TableView.empty());
        } catch(Exception exception) {
            return new InstructionResult(InstructionExecutionStatus.SERVER_ERROR, exception.getMessage(), 0, TableView.empty());
        }
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public InstructionParameters getParameters() {
        return parameters;
    }
}
