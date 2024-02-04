package ru.anafro.quark.server.documentation;

import ru.anafro.quark.server.console.Console;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.files.File;
import ru.anafro.quark.server.utils.strings.TextBuffer;

public class DocumentationGenerator {
    private static final File OUTPUT_FILE = File.create("Documentation.md");

    public static void generate() {
        var documentation = new TextBuffer();

        for (var instruction : Quark.instructions()) {
            documentation.appendLine(STR."""
                    ### `\{instruction.getName()}`

                    \{instruction.getDescription()}

                    Permission: `\{instruction.getPermission()}`

                    Syntax:
                    ```sql
                    \{instruction.getSyntax().transform(Console::colorless)}
                    ```
                    """);
        }

        OUTPUT_FILE.write(documentation.getContent());
    }
}
