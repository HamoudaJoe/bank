package com.kata.bank.operation;

import com.kata.bank.domain.Account;
import com.kata.bank.domain.OperationType;
import com.kata.bank.domain.Transaction;
import com.kata.bank.exception.TransactionException;
import com.kata.bank.manager.AccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 */
@Service
public class OperationImpl implements IOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationImpl.class);

    @Autowired
    private AccountManager accountManager;

    @Override
    public Account withdrawal(Account account, BigDecimal amount, String description) throws TransactionException {
        LOGGER.info("start withdrawal transaction : account id {}, amount {}", account.getIban(), amount);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new TransactionException("transaction refused : the amount requested is higher than account balance");
        } else {
            BigDecimal oldBalance = account.getBalance();
            account.setBalance(account.getBalance().subtract(amount));
            account.getTransactions().add(new Transaction.Builder()
                    .type(OperationType.WITHDRAWAL)
                    .amount(amount)
                    .accountId(account.getIban())
                    .date(LocalDate.now())
                    .description(description)
                    .newBalance(account.getBalance())
                    .oldBalance(oldBalance)
                    .build());
            LOGGER.info("end withdrawal transaction successfuly");
        }

        return account;
    }

    @Override
    public Account deposit(final Account account, BigDecimal amount, String description) {
        LOGGER.info("start deposit transaction on account id : {} - amount of : {} $", account.getIban(), amount);

        BigDecimal oldBalance = account.getBalance();

        account.setBalance(account.getBalance().add(amount));
        account.getTransactions().add(new Transaction.Builder()
                .type(OperationType.DEPOSIT)
                .amount(amount)
                .date(LocalDate.now())
                .accountId(account.getIban())
                .description(description)
                .newBalance(account.getBalance())
                .oldBalance(oldBalance)
                .build());

        LOGGER.info("end deposit transaction successfuly");

        return account;
    }
}
