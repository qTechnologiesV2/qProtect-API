package dev.mdma.qprotect.api.transformer.exclusions;

public enum RenamerExclusionType {
    METHODNAME("Keep MethodName"),
    FIELDNAME("Keep FieldName");

    private final String value;

    RenamerExclusionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}