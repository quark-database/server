package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.debug.components.Debugger;
import ru.anafro.quark.server.utils.patterns.NamedObjectsList;

public class DebuggerList extends NamedObjectsList<Debugger> {
    @Override
    protected String getNameOf(Debugger debugger) {
        return debugger.getName();
    }
}
