package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.facade.Quark;

import static ru.anafro.quark.server.console.CommandParameter.isRequired;
import static ru.anafro.quark.server.utils.collections.Collections.list;

public class FormatCommand extends Command {
    public FormatCommand() {
        super(
                list("format", "prettify"),
                "Formats the instruction",
                "Formats the instruction that passed in",
                isRequired("instruction", "The formatting instruction", "The instruction you want to be formatted")
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        var query = arguments.getString("instruction");
        var server = Quark.server();
        var lexer = server.getLexer();
        var parser = server.getParser();
        var tokens = lexer.lex(query);
        var instruction = parser.parse(tokens);
        var instructionArguments = parser.getArguments();

        System.out.println(instruction.format(instructionArguments));
    }
}
