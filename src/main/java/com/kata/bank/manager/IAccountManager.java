package com.kata.bank.manager;

import com.kata.bank.domain.Account;
import com.kata.bank.domain.Transaction;
import com.kata.bank.exception.AccountException;
import com.kata.bank.exception.TransactionException;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountManager {

    /**
     * Find an account by id (iban)
     *
     * @param accounts
     * @param accountId
     * @return Account
     */
    Account findAccountById(List<Account> accounts, String accountId);

    /**
     * find the history of an account
     *
     * @param accounts
     * @param accountId
     * @return list of transactions executed on account
     */
    List<Transaction> findAccountHistory(List<Account> accounts, String accountId);

    /**
     * execute a transaction : 1 deposit, 2 withdrawal
     *
     * @param accounts
     * @param accountId
     * @param operationId
     * @param amount
     * @return
     * @throws AccountException
     * @throws TransactionException
     */
    Account doTransaction(List<Account> accounts, String accountId, Integer operationId, BigDecimal amount) throws AccountException, TransactionException;
}
