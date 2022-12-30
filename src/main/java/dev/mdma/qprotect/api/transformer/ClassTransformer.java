package dev.mdma.qprotect.api.transformer;

import dev.mdma.qprotect.api.jar.JarFile;
import dev.mdma.qprotect.api.transformer.exclusions.ExclusionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.tinylog.Logger;

import java.util.concurrent.atomic.AtomicInteger;

@Getter @Setter
@RequiredArgsConstructor
public abstract class ClassTransformer extends TransformerHelper
        implements Transformer<ClassNode, JarFile>, Opcodes {
    private final String name;

    private final String message;

    protected final AtomicInteger counter = new AtomicInteger(0);

    private boolean enabled;

    @Override
    public boolean doInitialization(JarFile jarFile) {
        counter.set(0);
        return false;
    }

    @Override
    public boolean doFinalization(JarFile jarFile) {
        Logger.info(message, counter);
        return false;
    }

    @Override
    public final boolean transform(JarFile jarFile, ClassNode classNode) throws TransformException {
        return runOnClass(classNode.name, classNode, jarFile);
    }

    /**
     * Runs on the class node.
     *
     * @param className the name of the class
     * @param classNode the class node
     * @param jarFile   the jar file
     * @return whether the class was transformed
     * @throws TransformException if an error occurs
     */
    public abstract boolean runOnClass(String className, ClassNode classNode, JarFile jarFile) throws TransformException;

    /**
     * What exclusion type should this transformer use?
     *
     * @return the exclusion type
     */
    public abstract ExclusionType getExclusionType();

    /**
     * @return the amount of transformations made
     */
    public int getCount() {
        return counter.get();
    }
}
