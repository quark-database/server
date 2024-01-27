package ru.anafro.quark.server.database.language.instructions;

import ru.anafro.quark.server.database.language.Instruction;
import ru.anafro.quark.server.database.language.InstructionArguments;
import ru.anafro.quark.server.database.language.InstructionResultRecorder;
import ru.anafro.quark.server.database.language.ResponseStatus;
import ru.anafro.quark.server.security.TokenPermission;

public class SecretInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public SecretInstruction() {
        super(
                "secret",

                "What does it do? Hm-m...",

                TokenPermission.ALLOWED_FOR_ALL_TOKENS
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        result.ok("https://i.ibb.co/MfZgJTx/1-1.png");
    }
}
