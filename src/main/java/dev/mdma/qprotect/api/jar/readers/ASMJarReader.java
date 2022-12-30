package dev.mdma.qprotect.api.jar.readers;

import dev.mdma.qprotect.api.jar.JarFile;

/**
 * @author Cg.
 */
public interface ASMJarReader extends JarReader {
    JarFile loadJarFile(byte[] fileContent, boolean structureOnly) throws JarReaderException;

    JarFile loadJarFile(byte[] fileContent, int flag) throws JarReaderException;
}
