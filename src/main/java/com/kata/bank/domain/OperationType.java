package com.kata.bank.domain;

public enum OperationType {

    DEPOSIT(1, "dépôt"),
    WITHDRAWAL(2, "retrait");

    private int key;
    private String value;

    OperationType(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
