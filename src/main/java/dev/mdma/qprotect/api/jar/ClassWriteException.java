package dev.mdma.qprotect.api.jar;

import java.io.IOException;

/**
 * @author Cg.
 */
public class ClassWriteException extends IOException {
    public ClassWriteException() {
    }

    public ClassWriteException(String message) {
        super(message);
    }

    public ClassWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassWriteException(Throwable cause) {
        super(cause);
    }
}
