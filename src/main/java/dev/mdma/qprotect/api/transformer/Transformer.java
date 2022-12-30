package dev.mdma.qprotect.api.transformer;

public interface Transformer<T, M> {
    boolean doInitialization(M m) throws TransformException;

    /**
     * The implementation of this method performs the transformations on the target.
     *
     * @param t The specific target to be transformed.
     * @return A {@code true} value should be returned if the target is modified.
     */
    boolean transform(M m, T t) throws TransformException;

    boolean doFinalization(M m) throws TransformException;
}
