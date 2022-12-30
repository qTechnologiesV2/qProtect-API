package dev.mdma.qprotect.api.jar.readers;

import dev.mdma.qprotect.api.jar.JarFile;

/**
 * @author Cg.
 */
public interface JarReader {
    JarFile loadJarFile(byte[] fileContent) throws JarReaderException;
}
