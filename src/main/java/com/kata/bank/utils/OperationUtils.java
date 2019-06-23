package com.kata.bank.utils;

import com.kata.bank.domain.OperationType;

import static com.kata.bank.utils.OperationConstants.OPERATION_NOT_FOUND;

public class OperationUtils {

    public static String getOperation(int operationId) {

        if (OperationType.DEPOSIT.getKey() == operationId) {
            return OperationType.DEPOSIT.getValue();
        }

        if (OperationType.WITHDRAWAL.getKey() == operationId) {
            return OperationType.WITHDRAWAL.getValue();
        }
        return OPERATION_NOT_FOUND;
    }
}
