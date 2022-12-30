package dev.mdma.qprotect.api.jar;

/**
 * @author Cg.
 */
public class DefaultResourceEntry implements ResourceEntry<byte[]> {
    private String fileName;
    private byte[] content;

    public DefaultResourceEntry(String fileName, byte[] content) {
        this.fileName = fileName;
        this.content = content;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public boolean isEmpty() {
        return this.content.length == 0;
    }

    @Override
    public boolean isDirectory() {
        return fileName.endsWith("/") && content.length == 0;
    }
}
