package dev.mdma.qprotect.api.asm.hierachy;

import dev.mdma.qprotect.api.exception.qProtectException;
import dev.mdma.qprotect.api.qProtectAPI;
import lombok.SneakyThrows;
import org.objectweb.asm.tree.ClassNode;

import java.util.*;

public class Hierarchy {

    private final Map<String, ClassNode> classes, dependencies;
    private final Map<String, Tree> hierarchy = new HashMap<>();

    public Hierarchy(final Map<String, ClassNode> classes, final Map<String, ClassNode> dependencies) {
        this.classes = Collections.synchronizedMap(classes);
        this.dependencies = Collections.synchronizedMap(dependencies);
    }

    @SneakyThrows
    public ClassNode getClassNode(final String string) {
        qProtectAPI api = qProtectAPI.Factory.getAPI();
        if (!this.classes.containsKey(string)) {
            if (!this.dependencies.containsKey(string)) {
                api.setMissingDependencies();
                throw new qProtectException("Could not find " + string + " in classpath!");
            } else {
                return this.dependencies.get(string);
            }
        } else {
            return this.classes.get(string);
        }
    }

    public Tree getTree(final String string) {
        if (!this.hierarchy.containsKey(string)) {
            ClassNode classNode = this.getClassNode(string);

            this.buildHierarchy(classNode, null);
        }
        return this.hierarchy.get(string);
    }

    public void buildHierarchy(final ClassNode classNode, final ClassNode subNode) {
        if (this.hierarchy.get(classNode.name) == null) {
            final Tree hierarchy = new Tree(classNode);
            if (classNode.superName != null) {
                hierarchy.getParentClasses().add(classNode.superName);
                this.buildHierarchy(this.getClassNode(classNode.superName), classNode);
            }
            if (classNode.interfaces != null) {
                classNode.interfaces.forEach(interfaces -> {
                    hierarchy.getParentClasses().add(interfaces);
                    this.buildHierarchy(this.getClassNode(interfaces), classNode);
                });
            }
            this.hierarchy.put(classNode.name, hierarchy);
        }
        if (subNode != null) {
            this.hierarchy.get(classNode.name).getSubClasses().add(subNode.name);
        }
    }

    public static class Tree {

        private final ClassNode classNode;

        private final Set<String> parentClasses = new HashSet<>();
        private final Set<String> subClasses = new HashSet<>();

        public Tree(final ClassNode classNode) {
            this.classNode = classNode;
        }

        public ClassNode getClassNode() {
            return this.classNode;
        }

        public Set<String> getParentClasses() {
            return this.parentClasses;
        }

        public Set<String> getSubClasses() {
            return this.subClasses;
        }

    }
}