package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class EvalCommand extends Command {
    public EvalCommand() {
        super(new UniqueList<>("eval", "evaluate", "compute", "calculate", "calc"), "Evaluates an expression", "Evaluates a Quark QL entity", new CommandParameter("expression", "An expression", "An expression to evaluate"));
    }

    @Override
    public void action(CommandArguments arguments) {
        var result = ConstructorEvaluator.eval(arguments.get("expression"));
        logger.info("An expression result: (" + result.getExactTypeName() + ") " + result.getValue());
    }
}
