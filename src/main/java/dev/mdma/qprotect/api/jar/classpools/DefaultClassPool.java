package dev.mdma.qprotect.api.jar.classpools;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;

/**
 * @author Cg.
 */
public class DefaultClassPool implements ClassPool<ClassNode> {
    private Map<String, ClassNode> classes;

    public DefaultClassPool() {
        this(new HashMap<>());
    }

    public DefaultClassPool(Map<String, ClassNode> classes) {
        this.classes = classes;
    }

    @Override
    public void addClass(String name, ClassNode clazz) {
        classes.put(name, clazz);
    }

    @Override
    public ClassNode removeClass(String name) {
        return classes.remove(name);
    }

    @Override
    public boolean containsClass(String name) {
        return classes.containsKey(name);
    }

    @Override
    public ClassNode getClass(String name) {
        return classes.get(name);
    }

    @Override
    public Collection<ClassNode> getClasses() {
        return Collections.unmodifiableCollection(classes.values());
    }

    @Override
    public Set<String> getClassNames() {
        return Collections.unmodifiableSet(classes.keySet());
    }

    @Override
    public int poolSize() {
        return classes.size();
    }

    @Override
    public void clear() {
        classes.clear();
    }

    @Override
    public Map<String, ClassNode> getClassMap() {
        return classes;
    }

    @Override
    public String toString() {
        return "ClassPool{" +
                "Size=" + poolSize() +
                ", Classes=" + classes +
                '}';
    }
}
