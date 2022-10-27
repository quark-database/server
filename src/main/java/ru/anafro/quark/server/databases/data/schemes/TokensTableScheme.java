package ru.anafro.quark.server.databases.data.schemes;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.ListEntity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;

public class TokensTableScheme extends TableScheme {
    public TokensTableScheme() {
        super(
                "Quark.Tokens",

                ListEntity.of(
                        Quark.constructor("id").eval(),
                        Quark.constructor("str").eval(
                                new InstructionEntityConstructorArgument("column name", new StringEntity("token"))
                        ),
                        Quark.constructor("str").eval(
                                new InstructionEntityConstructorArgument("column name", new StringEntity("permission"))
                        )
                ),

                ListEntity.of()
        );
    }
}
