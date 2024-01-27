package ru.anafro.quark.server.utils.streams;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class IOStreams {
    private IOStreams() {
    }

    public static String readWholeString(InputStream stream) throws IOException {
        if (stream == null) {
            return null;
        }

        var bufferedStream = new BufferedInputStream(stream);
        var byteArrayStream = new ByteArrayOutputStream();

        for (int result = bufferedStream.read(); result != -1; result = bufferedStream.read()) {
            byteArrayStream.write((byte) result);
        }

        return byteArrayStream.toString(StandardCharsets.UTF_8);
    }
}
