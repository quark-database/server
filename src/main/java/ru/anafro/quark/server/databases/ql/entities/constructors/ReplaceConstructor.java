package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class ReplaceConstructor extends InstructionEntityConstructor {
    public ReplaceConstructor() {
        super(
                "replace",
                InstructionEntityConstructorParameter.required("string where to replace", "str"),
                InstructionEntityConstructorParameter.required("string to be replaced", "str"),
                InstructionEntityConstructorParameter.required("string to replace", "str")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        StringEntity stringWhereToReplace = arguments.get("string where to replace");
        StringEntity stringToBeReplaced = arguments.get("string to be replaced");
        StringEntity stringToReplace = arguments.get("string to replace");

        return new StringEntity(stringWhereToReplace.getString().replace(stringToBeReplaced.getString(), stringToReplace.getString()));
    }
}
