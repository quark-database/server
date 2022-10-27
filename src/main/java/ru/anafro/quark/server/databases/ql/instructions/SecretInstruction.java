package ru.anafro.quark.server.databases.ql.instructions;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionResultRecorder;
import ru.anafro.quark.server.databases.ql.QueryExecutionStatus;
import ru.anafro.quark.server.networking.Server;
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
    public void action(InstructionArguments arguments, Server server, InstructionResultRecorder result) {
        result.status(QueryExecutionStatus.OK, "https://i.ibb.co/MfZgJTx/1-1.png");
    }
}
