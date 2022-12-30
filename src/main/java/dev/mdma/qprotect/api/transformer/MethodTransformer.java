package dev.mdma.qprotect.api.transformer;

import dev.mdma.qprotect.api.jar.JarFile;
import dev.mdma.qprotect.api.transformer.exclusions.ExclusionType;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public abstract class MethodTransformer extends ClassTransformer {
    public MethodTransformer(String name, String context) {
        super(name, context);
    }

    @Override
    public final boolean runOnClass(String className, ClassNode classNode, JarFile jarFile) throws TransformException {
        boolean modified = false;

        for (MethodNode method : classNode.methods) {
            modified |= runOnMethod(classNode, method, jarFile);
        }

        return modified;
    }

    public abstract boolean runOnMethod(ClassNode ownerClass, MethodNode methodNode, JarFile jarFile) throws TransformException;

    public abstract ExclusionType getExclusionType();
}
