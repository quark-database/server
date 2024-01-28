package ru.anafro.quark.server.language.instructions;

import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionArguments;
import ru.anafro.quark.server.language.InstructionResultRecorder;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.security.TokenPermission;

public class GetVersionInstruction extends Instruction {
    /**
     * Creates a new instruction object. You should not use it anywhere
     * but in the registering ({@code Quark.instructions().add(new YourInstruction()}).
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public GetVersionInstruction() {
        super(
                "get version",
                "Returns Quark's version",
                TokenPermission.ALLOWED_FOR_ALL_TOKENS
        );
    }

    @Override
    protected void performAction(InstructionArguments arguments, InstructionResultRecorder result) {
        var version = Quark.version();

        result.header("major", "minor", "patch", "release type");
        result.row(version.major(), version.minor(), version.patch(), version.releaseType());
        result.ok("The version has been returned.");
    }
}
