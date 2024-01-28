package ru.anafro.quark.server.language;

import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.database.views.TableView;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.exceptions.Exceptions;
import ru.anafro.quark.server.utils.strings.TextBuffer;

/**
 * Instruction is a command to Quark Server to do an action.
 * Inherit from this class to create a new instruction and register it
 * via {@code Quark.instructions().add(new YourInstruction());}
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public abstract class Instruction {

    /**
     * The name of the instruction used in Quark QL.
     *
     * @since Quark 1.1
     */
    private final String name;

    /**
     * The permission an access token needs to run this instruction.
     *
     * @see ru.anafro.quark.server.security.Token
     * @see ru.anafro.quark.server.security.TokenPermission
     * @since Quark 1.1
     */
    private final String permission;  // TODO: Change to TokenPermission

    /**
     * The parameters this instruction can receive.
     *
     * @since Quark 1.1
     */
    private final InstructionParameters parameters;

    /**
     * The short instruction description.
     *
     * @since Quark 1.1
     */
    private final String description;

    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @param name       the name of the instruction.
     * @param permission the permission an access token need to run the instruction's action.
     * @param parameters the parameters this instruction can use in action.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public Instruction(String name, String description, String permission, InstructionParameter... parameters) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.parameters = new InstructionParameters(parameters);
    }

    /**
     * Runs an action of this instruction with parameters. This method is used
     * only by Quark itself. To execute the instruction in your plugins, use
     * {@link Instruction#execute(InstructionArguments)}
     *
     * @param arguments the arguments this instruction will be run with.
     * @param result    the result recorder that will store the result of execution.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    protected abstract void performAction(InstructionArguments arguments, InstructionResultRecorder result);

    /**
     * Executes the instruction with arguments.
     *
     * @param arguments the arguments this instruction will be run with.
     * @return the result of the execution.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public InstructionResult execute(InstructionArguments arguments) {
        try {
            ensureArgumentsAreValid(arguments);
            var resultRecorder = new InstructionResultRecorder();
            this.performAction(arguments, resultRecorder);

            return resultRecorder.collectResult();
        } catch (QuarkException exception) {
            return new InstructionResult(ResponseStatus.SYNTAX_ERROR, exception.getMessage(), 0, TableView.empty());
        } catch (Exception exception) {
            Quark.error("Exception happened when running an instruction. Check out the stack trace: ");
            Quark.error(Exceptions.getTrace(exception));
            return new InstructionResult(ResponseStatus.SERVER_ERROR, exception.getMessage(), 0, TableView.empty());
        }
    }

    private void ensureArgumentsAreValid(InstructionArguments arguments) {
        arguments.stream().filter(parameters::doesntHave).findFirst().ifPresent(unexpectedArgument -> {
            throw new DatabaseException(STR."There's no parameter \{unexpectedArgument} in the instruction:\n\{getSyntax()}");
        });

        for (var parameter : parameters) {
            var parameterName = parameter.getName();

            if (arguments.doesntHave(parameterName) && parameter.isRequired()) {
                throw new DatabaseException(STR."The instruction parameter \{parameterName} is required, but you didn't provide it");
            }

            if (arguments.doesntHave(parameterName)) {
                continue;
            }

            var argument = arguments.get(parameterName);

            if (parameter.isNotWildcard() && parameter.canNotReceive(argument)) {
                var parameterType = parameter.getType();
                var argumentType = argument.getType();

                throw new DatabaseException(STR."The instruction parameter (\{parameterType}) \{parameterName} cannot receive (\{argumentType}) \{argument}.");
            }
        }
    }

    /**
     * @return the syntax of the instruction.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public String getSyntax() {
        var syntax = new TextBuffer(STR."<blue>\{name}</>");

        parameters.tryGetGeneralParameter().ifPresent(parameter -> syntax.append(" ", parameter.getSyntaxAsIfGeneral()));
        syntax.appendIf(parameters.hasAdditionalParameters(), ": ");
        syntax.appendMany(parameters.getAdditionalParameters(), InstructionParameter::getSyntaxAsIfAdditional, ", ");
        syntax.append(";");

        return syntax.getContent();
    }

    public String format(InstructionArguments arguments) {
        this.ensureArgumentsAreValid(arguments);
        var query = new TextBuffer(STR."<italic><blue>\{name}</></>");

        query.appendIf(parameters.hasGeneralParameter(), " ");
        query.append(parameters.tryGetGeneralParameter(), parameter -> parameter.formatAsIfGeneral(arguments.get(parameter)));
        query.appendIf(parameters.hasAdditionalParameters(), ": ", "\n");
        query.append(parameters.format(arguments), ";");

        return query.getContent();
    }

    /**
     * @return the name of the instruction
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public String getName() {
        return name;
    }

    /**
     * @return the permission needed to run this instruction.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @return the parameters of this instruction.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public InstructionParameters getParameters() {
        return parameters;
    }

    public String getDescription() {
        return description;
    }
}
