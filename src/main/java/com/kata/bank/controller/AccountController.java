package com.kata.bank.controller;

import com.kata.bank.converter.AccountConverter;
import com.kata.bank.domain.Account;
import com.kata.bank.exception.AccountException;
import com.kata.bank.exception.TransactionException;
import com.kata.bank.manager.AccountManager;
import com.kata.bank.utils.OperationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountController {

    private static final Logger LOGGER = LogManager.getLogger(AccountController.class.getName());

    private static List<Account> accounts;

    @Autowired
    private AccountConverter accountConverter;

    @Autowired
    private AccountManager accountManager;

    @PostConstruct
    private List<Account> initAccountList() {

        // init accounts
        accounts = new ArrayList<>();
        accounts.add(new Account.Builder().id(1l).iban("FR14 3000 2150").balance(BigDecimal.ZERO).idClient("1234").build());
        accounts.add(new Account.Builder().id(2l).iban("FR14 3001 2152").balance(BigDecimal.ZERO).idClient("1234").build());
        return accounts;
    }

    /**
     * Find all accounts
     *
     * @return accounts
     */
    @GetMapping("/")
    public ResponseEntity getAllAccounts() {
        LOGGER.info("récuperer la liste des comptes");
        return new ResponseEntity<>(
                accounts.stream().map(acc -> accountConverter.convertAccount(acc)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    /**
     * account balance
     *
     * @param accountId
     * @return account
     */
    @GetMapping("/{accountId}")
    public ResponseEntity getAccountBalance(@PathVariable(value = "accountId") String accountId) {
        LOGGER.info("récupérer le solde du compte : {} ", accountId);
        Account account = accountManager.findAccountById(accounts, accountId);
        if (account == null) {
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(accountConverter.convertAccount(account), HttpStatus.OK);
    }

    /**
     * get the history of an account
     *
     * @param accountId
     * @return list of transactions
     */
    @GetMapping("/{accountId}/history")
    public ResponseEntity getAccountHistory(@PathVariable(value = "accountId") String accountId) {
        LOGGER.info("récupérer l'historique des transaction sur le compte : {} ", accountId);

        Account account = accountManager.findAccountById(accounts, accountId);
        if (account == null) {
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account.getTransactions(), HttpStatus.OK);
    }

    /**
     *
     * @param accountId String account id (iban)
     * @param operationId int operation id : 1 DESPOSIT, 2 WITHDRAWAL
     * @param amount the amount of the transaction
     *
     * @return list of operations of account after executing this transaction
     *
     * @throws AccountException
     * @throws TransactionException
     */
    @GetMapping("/{accountId}/operation/{operationId}/{amount}")
    public ResponseEntity doTransaction(@PathVariable(value = "accountId") final String accountId,
                                        @PathVariable(value = "operationId") final int operationId,
                                        @PathVariable(value = "amount") final BigDecimal amount)
                                    throws AccountException, TransactionException {

        LOGGER.info("effectuer un {} sur le compte : {} d'un montant de {}",
                    OperationUtils.getOperation(operationId), accountId, amount);

        Account account = accountManager.doTransaction(accounts, accountId, operationId, amount);
        return new ResponseEntity<>(account.getTransactions(), HttpStatus.OK);
    }
}
