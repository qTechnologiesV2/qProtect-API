package dev.mdma.qprotect.api.asm;

import dev.mdma.qprotect.api.asm.hierachy.Hierarchy;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.lang.reflect.Modifier;
import java.util.*;

public class HierarchyClassWriter extends ClassWriter {

    private final Hierarchy hierarchy;

    public HierarchyClassWriter(final Hierarchy hierarchy, final int flags) {
        super(flags);
        this.hierarchy = hierarchy;
    }

    @Override
    protected String getCommonSuperClass(final String firstType, final String secondType) {
        if (firstType.equals("java/lang/Object") || secondType.equals("java/lang/Object")) {
            return "java/lang/Object";
        }

        String first = this.deriveCommonSuperName(firstType, secondType);

        String second = this.deriveCommonSuperName(secondType, firstType);

        if (!first.equals("java/lang/Object")) {
            return first;
        }

        if (!second.equals("java/lang/Object")) {
            return second;
        }

        return this.getCommonSuperClass(this.hierarchy.getClassNode(firstType).superName,
                this.hierarchy.getClassNode(secondType).superName);
    }

    private String deriveCommonSuperName(final String firstType, final String secondType) {
        ClassNode first = this.hierarchy.getClassNode(firstType);
        final ClassNode second = this.hierarchy.getClassNode(secondType);

        if (this.isAssignableFrom(firstType, secondType)) {
            return firstType;
        } else if (this.isAssignableFrom(secondType, firstType)) {
            return secondType;
        } else if (Modifier.isInterface(first.access) || Modifier.isInterface(second.access)) {
            return "java/lang/Object";
        } else {
            String string;
            do {
                string = first.superName;
                first = this.hierarchy.getClassNode(string);
            } while (!this.isAssignableFrom(string, secondType));
            return string;
        }
    }


    public boolean isAssignableFrom(final String firstType, final String secondType) {
        if (firstType.equals("java/lang/Object") || firstType.equals(secondType)) {
            return true;
        }

        Hierarchy.Tree firstTree = this.hierarchy.getTree(firstType);

        if (firstTree == null) {
            try {
                throw new ClassNotFoundException("Could not find " + firstType + " in the built class hierarchy");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        final Set<String> set = new HashSet<>();
        final Deque<String> deque = new ArrayDeque<>(Objects.requireNonNull(firstTree).getSubClasses());
        while (!deque.isEmpty()) {
            final String string = deque.poll();
            if (set.add(string)) {
                final Hierarchy.Tree tree = this.hierarchy.getTree(string);
                deque.addAll(tree.getSubClasses());
            }
        }
        return set.contains(secondType);
    }
}
