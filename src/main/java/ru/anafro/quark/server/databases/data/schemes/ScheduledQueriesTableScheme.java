package ru.anafro.quark.server.databases.data.schemes;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArgument;
import ru.anafro.quark.server.databases.ql.entities.ListEntity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;

public class ScheduledQueriesTableScheme extends TableScheme {
    public ScheduledQueriesTableScheme() {
        super(
                "Quark.Scheduled Queries",

                ListEntity.of(
                        Quark.constructor("id").eval(),
                        Quark.constructor("str").eval(
                                new InstructionEntityConstructorArgument("column name", new StringEntity("query"))
                        ),
                        Quark.constructor("long").eval(
                                new InstructionEntityConstructorArgument("column name", new StringEntity("period"))
                        )
                ),

                ListEntity.of()
        );
    }
}
