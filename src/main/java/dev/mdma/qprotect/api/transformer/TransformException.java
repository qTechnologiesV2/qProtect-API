package dev.mdma.qprotect.api.transformer;

import java.io.IOException;

public class TransformException extends IOException {
    public TransformException() {}

    public TransformException(String message) {
        super(message);
    }

    public TransformException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransformException(Throwable cause) {
        super(cause);
    }
}
