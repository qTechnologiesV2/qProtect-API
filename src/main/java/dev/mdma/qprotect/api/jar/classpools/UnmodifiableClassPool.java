package dev.mdma.qprotect.api.jar.classpools;

import java.util.Map;

import org.objectweb.asm.tree.ClassNode;

/**
 * @author Cg.
 */
public class UnmodifiableClassPool extends DefaultClassPool {
    public UnmodifiableClassPool() {
        super();
    }

    public UnmodifiableClassPool(Map<String, ClassNode> classes) {
        super(classes);
    }

    @Override
    public void addClass(String name, ClassNode clazz) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClassNode removeClass(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
