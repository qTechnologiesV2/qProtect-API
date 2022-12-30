package dev.mdma.qprotect.api.jar.readers;

import java.io.IOException;

/**
 * @author Cg.
 */
public class JarReaderException extends IOException {
    public JarReaderException() {
    }

    public JarReaderException(String message) {
        super(message);
    }

    public JarReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public JarReaderException(Throwable cause) {
        super(cause);
    }
}
