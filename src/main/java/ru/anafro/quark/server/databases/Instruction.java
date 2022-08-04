package ru.anafro.quark.server.databases;

import ru.anafro.quark.server.QuarkException;
import ru.anafro.quark.server.databases.exceptions.NoSuchInstructionParameterException;
import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.views.TableView;
import ru.anafro.quark.server.networking.Server;

public abstract class Instruction {
    public final String name;
    public final String permission;
    public final InstructionParameter generalParameter;
    public final InstructionParameter[] parameters;

    public Instruction(String name, String permission, InstructionParameter generalArgument, InstructionParameter... arguments) {
        this.name = name;
        this.permission = permission;
        this.generalParameter = generalArgument;
        this.parameters = arguments;
    }

    public static Instruction fromString(String instruction) {
        new InstructionLexer(instruction).performLexing(); // TODO
        return null;
    }

    public InstructionParameter getParameterByName(String parameterName) {
        if(generalParameter.getName().equals(parameterName)) {
            return generalParameter;
        }

        for(var parameter : parameters) {
            if(parameter.getName().equals(parameterName)) {
                return parameter;
            }
        }

        throw new NoSuchInstructionParameterException(this, parameterName);
    }

    public void setParameterValue(String parameterName, String parameterValue) {
        getParameterByName(parameterName).setValue(parameterValue);
    }

    public abstract void action(Server server, InstructionResultRecorder result);

    public InstructionResult execute(Server server) {
        try {
            InstructionResultRecorder resultRecorder = new InstructionResultRecorder();

            this.action(server, resultRecorder);

            return resultRecorder.collectResult();
        } catch(QuarkException exception) {
            return new InstructionResult(InstructionExecutionStatus.SYNTAX_ERROR, exception.getMessage(), 0 /* TODO */, TableView.empty());
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

    public boolean hasGeneralParameter() {
        return generalParameter != null;
    }

    public InstructionParameter getGeneralParameter() {
        return generalParameter;
    }

    public InstructionParameter[] getParameters() {
        return parameters;
    }
}
