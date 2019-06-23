package com.kata.bank.manager;

import com.kata.bank.domain.Account;
import com.kata.bank.domain.OperationType;
import com.kata.bank.domain.Transaction;
import com.kata.bank.exception.AccountException;
import com.kata.bank.exception.TransactionException;
import com.kata.bank.operation.IOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AccountManager implements IAccountManager {

    private static final Logger LOGGER = LogManager.getLogger(AccountManager.class.getName());

    @Autowired
    private IOperation operation;

    @Override
    public Account findAccountById(final List<Account> accounts, final String accountId) {
        LOGGER.info("récupérer le compte d'id {}", accountId);
        if (!CollectionUtils.isEmpty(accounts)) {
            return accounts.stream().filter(acc -> acc.getIban().equals(accountId)).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public List<Transaction> findAccountHistory(final List<Account> accounts, final String accountId) {
        if (!CollectionUtils.isEmpty(accounts)) {
            Account account = accounts.stream().filter(acc -> acc.getIban().equals(accountId)).findFirst().orElse(null);
            if (account != null) {
                return account.getTransactions();
            }
        }
        return null;
    }

    @Override
    public Account doTransaction(List<Account> accounts,
                                 String accountId,
                                 Integer operationId,
                                 BigDecimal amount) throws AccountException, TransactionException {

        Account account = this.findAccountById(accounts, accountId);

        if (account == null) {
            throw new AccountException("Account not found");
        }

        if (OperationType.DEPOSIT.getKey() == operationId) { // desposit
            account = operation.deposit(account, amount, "");
        } else if (OperationType.WITHDRAWAL.getKey() == operationId) { // withdrawal
            account = operation.withdrawal(account, amount, "");
        } else {
            throw new TransactionException("operation does not exist : 1 Deposit, 2 withdrawal");
        }

        return account;
    }
}
