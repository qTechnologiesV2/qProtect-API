package dev.mdma.qprotect.api.jar;

import dev.mdma.qprotect.api.asm.HierarchyClassWriter;
import dev.mdma.qprotect.api.jar.classpools.ClassPool;
import dev.mdma.qprotect.api.jar.classpools.DefaultClassPool;

import dev.mdma.qprotect.api.qProtectAPI;
import lombok.Getter;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Cg.
 */
public class JarFile {
    @Getter
    private ClassPool<? extends ClassNode> classPool, excludedPool;
    private Map<String, ResourceEntry<byte[]>> resources;

    public JarFile() {
        this(new DefaultClassPool(), new DefaultClassPool(), new HashMap<>());
    }

    public JarFile(ClassPool<? extends ClassNode> classPool, ClassPool<? extends ClassNode> excludedPool, Map<String, ResourceEntry<byte[]>> resources) {
        this.classPool = classPool;
        this.excludedPool = excludedPool;
        this.resources = resources;
    }

    public Collection<ResourceEntry<byte[]>> getResources() {
        return Collections.unmodifiableCollection(resources.values());
    }

    public ResourceEntry<byte[]> getResource(String fileName) {
        return this.resources.get(fileName);
    }

    public void addResource(ResourceEntry<byte[]> resource) {
        if (this.resources.containsKey(resource.getFileName())) {
            throw new IllegalStateException("Resource already exists");
        }
        this.resources.put(resource.getFileName(), resource);
    }

    public ResourceEntry<byte[]> removeResource(String fileName) {
        return this.resources.remove(fileName);
    }

    public ResourceEntry<byte[]> removeResource(ResourceEntry<byte[]> resource) {
        return this.removeResource(resource.getFileName());
    }

    public void write(ZipOutputStream outputStream) throws ClassWriteException {
        this.write(outputStream, ClassWriter.COMPUTE_FRAMES);
    }

    public void write(ZipOutputStream outputStream, int flag) throws ClassWriteException {
        qProtectAPI api = qProtectAPI.Factory.getAPI();

        api.changeProgressBar(80, "Writing class files...");

        long curTime = System.currentTimeMillis();

        api.getClassPool().getClasses().forEach(cn -> {
            try {
                ClassWriter writer = new HierarchyClassWriter(api.getHierarchy(), flag);
                writer.newUTF8("qProtect " + api.getVersion());
                cn.accept(writer);
                writeEntry(outputStream, cn.name + api.getClassExtension(), curTime, writer.toByteArray());
            } catch (Throwable t) {
                t.printStackTrace();
                Logger.warn("ClassWriter failed for {}, forcing COMPUTE_MAXS", cn.name);
                ClassWriter writer = new HierarchyClassWriter(api.getHierarchy(),
                        ClassWriter.COMPUTE_MAXS);
                writer.newUTF8("qProtect " + api.getVersion());

                cn.accept(writer);
                writeEntry(outputStream, cn.name + api.getClassExtension(), curTime,
                        writer.toByteArray());
            }
        });
        api.getExcludedClassPool().getClasses().forEach(classNode -> {
            ClassWriter writer = new HierarchyClassWriter(api.getHierarchy(),
                    ClassWriter.COMPUTE_MAXS);
            writer.newUTF8("qProtect " + api.getVersion());

            classNode.accept(writer);
            writeEntry(outputStream, classNode.name + api.getClassExtension(), curTime,
                    writer.toByteArray());
        });
        resources.values().forEach(resourceEntry -> writeEntry(outputStream, resourceEntry.getFileName(), curTime, resourceEntry.getContent()));
    }

    private void writeEntry(ZipOutputStream outputStream, String fileName, long modTime, byte[] content) {
        ZipEntry entry = new ZipEntry(fileName);
        entry.setTime(modTime);
        try {
            outputStream.putNextEntry(entry);
            outputStream.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
