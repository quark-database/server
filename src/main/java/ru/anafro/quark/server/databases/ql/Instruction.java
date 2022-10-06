package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.views.TableView;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.utils.strings.TextBuffer;

/**
 * Instruction is a command to Quark Server to do an action.
 * Inherit from this class to create a new instruction and register it
 * via {@code Quark.instructions().add(new YourInstruction();}
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public abstract class Instruction {

    /**
     * The name of the instruction used in Quark QL.
     * @since Quark 1.1
     */
    public final String name;

    /**
     * The permission an access token needs to run this instruction.
     * @since Quark 1.1
     * @see ru.anafro.quark.server.security.Token
     * @see ru.anafro.quark.server.security.TokenPermission
     */
    public final String permission;  // TODO: Change to TokenPermission

    /**
     * The parameters this instruction can receive.
     * @since Quark 1.1
     */
    public final InstructionParameters parameters;

    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @param name the name of the instruction.
     * @param permission the permission an access token need to run the instruction's action.
     * @param parameters the parameters this instruction can use in action.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public Instruction(String name, String permission, InstructionParameter... parameters) {
        this.name = name;
        this.permission = permission;
        this.parameters = new InstructionParameters(parameters);
    }

    /**
     * Runs an action of this instruction with parameters. This method is used
     * only by Quark itself. To execute the instruction in your plugins, use
     * {@link Instruction#execute(InstructionArguments)}
     *
     * @param arguments the arguments this instruction will be run with.
     * @param server the server
     * @param result the result recorder that will store the result of execution.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     *
     * @deprecated The {@code server} argument is going to be removed. Use {@code Quark.server()}
     *             in this method instead.
     */
    @Deprecated
    public abstract void action(InstructionArguments arguments, Server server, InstructionResultRecorder result);

    /**
     * Executes the instruction with arguments.
     *
     * @param arguments the arguments this instruction will be run with.
     * @return the result of the execution.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public InstructionResult execute(InstructionArguments arguments) {
        for(var argument : arguments) {
            if(!parameters.has(argument.name())) {
                throw new DatabaseException("There's no instruction parameter %s. Follow this instruction syntax: %s".formatted(argument.name(), getSyntax()));
            }
        }

        for(var parameter : parameters) {
            if(parameter.isRequired() && !arguments.has(parameter.getName())) {
                throw new DatabaseException("The instruction parameter %s is required, but you didn't provide it".formatted(parameter.getName()));
            }

            if(arguments.has(parameter.getName()) && !parameter.isWildcard() && !arguments.get(parameter.getName()).getExactTypeName().equals(parameter.getType())) {
                throw new DatabaseException("The instruction parameter %s has type %s, but you passed value %s to it, which type is %s"
                        .formatted(
                                parameter.getName(),
                                parameter.getType(),
                                arguments.get(parameter.getName()).toInstructionForm(),
                                arguments.get(parameter.getName()).getType()
                        )
                );
            }
        }

        try {
            InstructionResultRecorder resultRecorder = new InstructionResultRecorder();

            this.action(arguments, Quark.server(), resultRecorder);

            return resultRecorder.collectResult();
        } catch(QuarkException exception) {
            return new InstructionResult(QueryExecutionStatus.SYNTAX_ERROR, exception.getMessage(), 0, TableView.empty());
        } catch(Exception exception) {
            Quark.logger().error("Exception happened when running an instruction. Check out the stack trace: ");
            Quark.logger().error(Exceptions.getTraceAsString(exception));
            return new InstructionResult(QueryExecutionStatus.SERVER_ERROR, exception.getMessage(), 0, TableView.empty());
        }
    }

    /**
     * @return the syntax of the instruction.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public String getSyntax() {
        TextBuffer syntax = new TextBuffer();

        syntax.append(name);

        if(parameters.hasGeneralParameter()) {
            syntax.append(" <" + parameters.getGeneralParameter().getName() + " is " + parameters.getGeneralParameter().getType() + ">");
        }

        if(parameters.hasAdditionalParameters()) {
            syntax.append(": ");

            var additionalParameters = parameters.getAdditionalParameters().toList();
            for(int index = 0; index < parameters.getAdditionalParameterCount(); index++) {
                var additionalParameter = additionalParameters.get(index);
                syntax.append(additionalParameter.getName() + " = (" + additionalParameter.getType() + ")");

                if(index != additionalParameters.size() - 1) {
                    syntax.append(", ");
                }
            }
        }

        syntax.append(";");

        return syntax.extractContent();
    }

    /**
     * @return the name of the instruction
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public String getName() {
        return name;
    }

    /**
     * @return the permission needed to run this instruction.
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @return the parameters of this instruction.
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public InstructionParameters getParameters() {
        return parameters;
    }
}
