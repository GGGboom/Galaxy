package com.example.Galaxy.util.enums;

public enum OperationType {
    UNKNOWN("UNKNOWN"),
    SELECT("SELECT"),
    INSERT("INSERT"),
    DELETE("DELETE"),
    UPDATE("UPDATE");
    private String operationType;

    OperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
