package ru.anafro.quark.server.documentation;

import ru.anafro.quark.server.console.Console;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.files.File;
import ru.anafro.quark.server.utils.strings.Strings;
import ru.anafro.quark.server.utils.strings.TextBuffer;

public class DocumentationGenerator {
    private static final File OUTPUT_FILE = File.create("Documentation.md");

    public static void generate() {
        var documentation = new TextBuffer(STR."""
                This documentation is for \{Quark.NAME} \{Quark.version()}.

                > ℹ️ Note for users of Quark older \{Quark.version().getShortVersion()}
                >
                > For older versions of Quark, please, search for README.md
                > in the release commit with tag you want. We are currently working
                > on a documentation system with multiple versions. Thank you.
                """);

        documentation.appendLine("""
                ### Instructions
                Instructions are used to create tables, query data, and much more.
                """);
        documentation.appendMany(Quark.instructions(), instruction -> STR."""
                    #### `\{instruction.getName()}`

                    \{instruction.getDescription()}

                    Permission: `\{instruction.getPermission()}`

                    Parameters:
                    \{Lists.join(instruction.getParameters().stream().toList(), parameter -> STR."`\{parameter.getName()}: \{parameter.getType()}`}", "\n\n")}

                    Syntax:
                    ```sql
                    \{instruction.getSyntax().transform(Console::colorless)}
                    ```
                    """);

        documentation.appendLine("""
                ### Constructors
                Constructors can transform entities in both your instructions and tables.
                Use them for integers, strings, lists, and any other type of entities.
                """);
        documentation.appendMany(Quark.constructors(), constructor -> STR."""
                    #### `\{constructor.getName()}: \{constructor.getReturnType()}`

                    \{constructor.getReturnDescription().getDescription().transform(Strings::capitalize)}

                    Parameters:

                    \{Lists.join(constructor.getParameters().asList(), parameter -> STR."`\{parameter.name()}: \{parameter.type()}`", "\n\n")}

                    Syntax:
                    ```sql
                    \{constructor.getSyntax().transform(Console::colorless)}
                    ```
                    """);

        documentation.appendLine("""
                ### Commands
                Commands helps with server interaction.
                """);
        documentation.appendMany(Quark.commands(), command -> STR."""
                    #### `\{command}`

                    \{command.getLongDescription()}

                    Parameters:

                    \{Lists.join(command.getParameters().toList(), parameter -> STR."`\{parameter.name()}: \{parameter.type()}` - \{parameter.longDescription()}", "\n\n")}

                    Syntax:
                    ```sql
                    \{command.getSyntax()}
                    ```
                    """);

        OUTPUT_FILE.write(documentation.getContent());
    }
}
