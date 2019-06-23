package com.kata.bank.operation;

import com.kata.bank.domain.Account;
import com.kata.bank.exception.TransactionException;

import java.math.BigDecimal;

public interface IOperation {

    /**
     *
     * withdrawl transaction
     *
     * @param account Account
     * @param amount BigDecimal
     * @param description String description of the transaction
     *
     * @throws TransactionException
     */
    Account withdrawal(Account account, BigDecimal amount, String description) throws TransactionException;

    /**
     * deposit transaction
     *
     * @param account Account
     * @param amount BigDecimal
     * @param description String
     *
     * @throws TransactionException
     */
    Account deposit(Account account, BigDecimal amount, String description);
}
