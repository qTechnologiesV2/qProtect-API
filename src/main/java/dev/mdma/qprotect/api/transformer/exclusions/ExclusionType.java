package dev.mdma.qprotect.api.transformer.exclusions;

// TODO: Replace this for a per-transformer exclusion system.
public enum ExclusionType {
    GLOBAL("Global"),
    STRING_ENCRYPTION("StringEncryption"),
    FLOW_OBFUSCATION("FlowObfuscation"),
    REFRENCE_ENCRYPTION("ReferenceEncryption"),
    NUMBER_OBFUSCATION("NumberObfuscation"),
    ARITHMETIC_OBFUSCATION("ArithmeticObfuscation"),
    SOURCEINFO("SourceInfo"),
    LINE_NUMBERS("LineNumbers"),
    ANTI_DEBUGGER("AntiDebugger"),
    LOCAL_VARIABLES("LocalVariables"),
    CRASHER("Crasher"),
    SHUFFLER("Shuffler"),
    ACCESS("Access"),
    RENAMER("Renamer"),
    OPTIMIZER("Optimizer"),
    MATH_MUTATION("MathMutation"),
    BOOLEAN("BooleanTransformer"),
    CUSTOM("Custom");

    private final String value;

    ExclusionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}