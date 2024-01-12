package dev.mdma.qprotect.api.jar.classpools;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;

/**
 * @author Cg.
 */
public interface ClassPool<T> {

    void addClass(String name, ClassNode clazz);

    T removeClass(String name);

    boolean containsClass(String name);

    T getClass(String name);

    Collection<T> getClasses();

    Set<String> getClassNames();

    Map<String, ClassNode> getClassMap();

    int poolSize();

    void clear();
}