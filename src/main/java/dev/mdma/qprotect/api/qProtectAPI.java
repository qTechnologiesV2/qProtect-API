package dev.mdma.qprotect.api;

import dev.mdma.qprotect.api.asm.hierachy.Hierarchy;
import dev.mdma.qprotect.api.jar.ResourceEntry;
import dev.mdma.qprotect.api.jar.classpools.ClassPool;
import dev.mdma.qprotect.api.transformer.ClassTransformer;
import dev.mdma.qprotect.api.transformer.RenamerTransformer;
import dev.mdma.qprotect.api.transformer.Transformer;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface qProtectAPI {
    /**
     * Changes the GUI's ProgressBar state.
     * @param amount New progressbar value
     * @param status Status text to be displayed
     */
    void changeProgressBar(int amount, String status);

    /**
     * @return List of all transformers currently loaded
     */
    List<Transformer<?, ?>> getTransformers();

    /**
     * @return List of classTransformers
     */
    List<ClassTransformer> getClassTransformers();

    /**
     * Registers a transformer
     * @param clazz The class of the transformer
     */
    void registerTransformer(Class<? extends Transformer<?, ?>> clazz);

    /**
     * Unregisters a transformer
     * @param clazz The class of the transformer
     */
    void unregisterTransformer(Class<? extends Transformer<?, ?>> clazz);

    /**
     * Enables a transformer by class
     * @param clazz The class of the transformer
     */
    void enableTransformer(Class<? extends Transformer<?, ?>> clazz);

    /**
     * Disables a transformer by class
     * @param clazz The class of the transformer
     */
    void disableTransformer(Class<? extends Transformer<?, ?>> clazz);

    /**
     * The extension to be used on classes
     * @return String of global class extension
     */
    String getClassExtension();

    /**
     * The Class Hierarchy
     * @return An instance of Hierarchy
     */
    Hierarchy getHierarchy();


    /**
     * Gets all of our classes through the ClassPool
     * @return ClassPool instance of all loaded ClassNodes
     */
    ClassPool<? extends ClassNode> getClassPool();

    /**
     * Gets all of our classes through the ClassPool
     * @return ClassPool instance of all excluded ClassNodes
     */
    ClassPool<? extends ClassNode> getExcludedClassPool();

    /**
     * gets the current API version
     * @return Versioon of the API
     */
    String getVersion();

    /**
     * Check If class is excluded
     * @return true if class excluded
     */
    boolean isExcluded(ClassNode classNode, String exclusionType);

    /**
     * Push a class to Exclusions
     */
    void addExclusion(String transformerName, String classToExclude);

    /**
     * Remove a class from Exclusions
     */
    void removeExclusion(String transformerName, String excludedClass);

    /**
     * Gets a List of all exclusions
     * @return List<String>
     */
    List<String> getExclusions();

    /**
     * Check If class is included
     * @return true if class included
     */
    boolean isIncluded(ClassNode classNode, String exclusionType);

    /**
     * Check If class is included globally
     * @return true if class included
     */
    boolean isIncluded(ClassNode classNode);

    /**
     * Push a class to Exclusions
     */
    void addInclusion(String transformerName, String classToInclude);

    /**
     * Remove a class from Exclusions
     */
    void removeInclusion(String transformerName, String includedClass);

    /**
     * Gets a List of all inclusions
     * @return List<String>
     */
    List<String> getInclusions();


    /**
     * Sends an error to qprotect-core if a classNode is missing in the dependencies
     */
    void setMissingDependencies();

    /**
     * Puts a classNode to the dependency classPool
     */
    void putToDependencies(String className, ClassNode classNode);

    /**
     * Gets the bootstrapClassNode from the Processor
     *  @return classNode
     */
    ClassNode getBootstrapClassNode();

    /**
     * Gets the input or output from the Processor
     *  @return File
     */
    File getInputFile();
    File getOutputFile();

    Collection<ResourceEntry<byte[]>> getResources();

    class Factory {
        private static qProtectAPI api;

        public static qProtectAPI getAPI() {
            return api;
        }

        public static void setApi(qProtectAPI api) {
            Factory.api = api;
        }
    }
}
