package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;

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

        result.ok("The plugin list is returned.");
    }
}
