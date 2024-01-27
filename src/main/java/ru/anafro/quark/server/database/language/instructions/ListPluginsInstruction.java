package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;
import ru.anafro.quark.server.facade.Quark;

public class ListPluginsInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public ListPluginsInstruction() {
        super(
                "list plugins",
                "Lists loaded plugins",
                "server.plugins"
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        result.header("plugin name", "plugin author");

        for (var plugin : Quark.plugins()) {
            result.row(plugin.getName(), plugin.getAuthor());
        }

        result.ok("Plugins are listed.");
    }
}
