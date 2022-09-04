package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.data.TableRecordSelector;
import ru.anafro.quark.server.databases.ql.entities.*;

public class SelectorConstructor extends InstructionEntityConstructor {
    public SelectorConstructor() {
        super("selector", InstructionEntityConstructorParameter.required("selector expression", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new SelectorEntity(new TableRecordSelector(arguments.<StringEntity>get("selector expression").getString()));
    }
}
