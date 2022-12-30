package dev.mdma.qprotect.api.jar;

/**
 * @author Cg.
 */
public interface ResourceEntry<T> {
    String getFileName();

    void setFileName(String fileName);

    T getContent();

    void setContent(T content);

    boolean isEmpty();

    boolean isDirectory();
}
