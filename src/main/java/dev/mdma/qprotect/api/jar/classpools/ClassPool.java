package dev.mdma.qprotect.api.jar.classpools;

import org.objectweb.asm.tree.ClassNode;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

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