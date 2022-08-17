package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.views.TableView;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.strings.TextBuffer;

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
        for(var argument : arguments) {
            if(!parameters.has(argument.name())) {
                throw new DatabaseException("There's no instruction parameter %s. Follow this instruction syntax: %s".formatted(argument.name(), getSyntax()));
            }
        }

        for(var parameter : parameters) {
            if(parameter.isRequired() && !arguments.has(parameter.getName())) {
                throw new DatabaseException("The instruction parameter %s is required, but you didn't provide it".formatted(parameter.getName()));
            }

            if(arguments.has(parameter.getName()) && !parameter.isWildcard() && !arguments.get(parameter.getName()).getType().equals(parameter.getType())) {
                throw new DatabaseException("The instruction parameter %s has type %s, but you passed value %s to it, which type is %s".formatted(parameter.getName(), parameter.getType(), arguments.get(parameter.getName()).getValueAsString(), arguments.get(parameter.getName()).getType()));
            }
        }

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

    public String getSyntax() {
        TextBuffer syntax = new TextBuffer();

        syntax.append(name);

        if(parameters.hasGeneralParameter()) {
            syntax.append(" <" + parameters.getGeneralParameter().getName() + " is " + parameters.getGeneralParameter().getType() + ">");
        }

        if(parameters.hasAdditionalParameters()) {
            syntax.append(":");

            var additionalParameters = parameters.getAdditionalParameters().toList();
            for(int index = 0; index < parameters.getAdditionalParameterCount(); index++) {
                var additionalParameter = additionalParameters.get(index);
                syntax.append(additionalParameter.getName() + " = (" + additionalParameter.getType() + ")");

                if(index != additionalParameters.size() - 1) {
                    syntax.append(", ");
                }
            }

            parameters.getAdditionalParameters().forEachOrdered(parameter -> syntax.append(" <" + parameter.getName() + " is " + parameter.getType()));
        }

        syntax.append(";");

        return syntax.extractContent();
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
