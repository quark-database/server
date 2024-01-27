package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.database.language.Expressions;
import ru.anafro.quark.server.exceptions.QuarkException;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class EvalCommand extends Command {
    public EvalCommand() {
        super(list("eval", "evaluate", "compute", "calculate", "calc"), "Evaluates an lambda", "Evaluates a Quark QL entity", new CommandParameter("expression", "An expression", "An expression to evaluate"));
    }

    @Override
    public void action(CommandArguments arguments) {
        try {
            var result = Expressions.eval(arguments.get("expression"));
            logger.info(STR."<blue>\{result.getExactTypeName()}</>: <salad>\{result.format()}</>");
        } catch (QuarkException exception) {
            logger.error(exception.getMessage());
        }
    }
}
