package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.utils.strings.TextBuffer;

public class ConcatConstructor extends InstructionEntityConstructor {
    public ConcatConstructor() {
        super("concat", InstructionEntityConstructorParameter.varargs("strings", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        TextBuffer concatenated = new TextBuffer();

        for(var string : arguments.<ListEntity>get("strings")) {
            concatenated.append(string);
        }

        return new StringEntity(concatenated.extractContent());
    }
}
