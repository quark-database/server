package ru.anafro.quark.server.debug.exceptions;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class ImageForDebugFrameIconNotFoundException extends UIException {
    public ImageForDebugFrameIconNotFoundException(String path) {
        super("An image for debug frame icon by path %s cannot be found".formatted(quoted(path)));
    }
}
