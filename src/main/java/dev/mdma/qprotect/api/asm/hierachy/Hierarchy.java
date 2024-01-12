package dev.mdma.qprotect.api.asm.hierachy;

import dev.mdma.qprotect.api.qProtectAPI;
import dev.mdma.qprotect.api.utils.BytecodeUtils;
import lombok.SneakyThrows;
import org.objectweb.asm.tree.ClassNode;
import org.tinylog.Logger;

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
                Logger.error("Could not find " + string + " in classpath!");
                return BytecodeUtils.createClassNode(string);
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
            Tree tree = new Tree(classNode);
            this.buildHierarchy(classNode, null, tree);
            this.hierarchy.put(string, tree);
        }
        return this.hierarchy.get(string);
    }

    public void buildHierarchy(final ClassNode classNode, final ClassNode subNode, Tree tree) {
        String className = classNode.name;

            if (tree.getParentClasses().isEmpty()) {
                if (classNode.superName != null) {
                    tree.getParentClasses().add(classNode.superName);
                    this.buildHierarchy(this.getClassNode(classNode.superName), classNode, tree);
                }

                if (classNode.interfaces != null) {
                    for (String interfaceName : classNode.interfaces) {
                        tree.getParentClasses().add(interfaceName);
                        this.buildHierarchy(this.getClassNode(interfaceName), classNode, tree);
                    }
                }
            }

            if (subNode != null) {
                tree.getSubClasses().add(subNode.name);
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