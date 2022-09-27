package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.debug.components.DebugFrame;
import ru.anafro.quark.server.utils.patterns.NamedObjectsRegistry;

public class DebugFrameRegistry extends NamedObjectsRegistry<DebugFrame> {
    @Override
    protected String getNameOf(DebugFrame frame) {
        return frame.getName();
    }
}
