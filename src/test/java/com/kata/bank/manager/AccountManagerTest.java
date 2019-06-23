package com.kata.bank.manager;

import com.kata.bank.domain.Account;
import com.kata.bank.domain.OperationType;
import com.kata.bank.domain.Transaction;
import com.kata.bank.exception.AccountException;
import com.kata.bank.exception.TransactionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountManagerTest {

    @Autowired
    private IAccountManager accountManager;

    @Test
    public void testFindAccountById() {

        // THEN
        Account result = accountManager.findAccountById(getAccountList(), "FR14 3000 2150");

        // ASSERT
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getIban(), "FR14 3000 2150");
    }

    @Test
    public void testDoDepositTransaction() {
        try {
            Account account = accountManager.doTransaction(getAccountList(), "FR14 3000 2150", 1, new BigDecimal(1300));
            assertNotNull(account.getTransactions());
            assertEquals(account.getTransactions().size(), 1);
            assertEquals(account.getTransactions().get(0).getType(), OperationType.DEPOSIT);
        } catch (AccountException e) {
            fail();
        } catch (TransactionException e) {
            fail();
        }
    }

    @Test
    public void testDoWithdrawalTransaction() {
        try {
            Account account = accountManager.doTransaction(getAccountList(), "FR14 3000 2150", 2, new BigDecimal(1200));
            assertNotNull(account.getTransactions());
            assertEquals(account.getTransactions().size(), 1);
            assertEquals(account.getTransactions().get(0).getType(), OperationType.WITHDRAWAL);
        } catch (AccountException e) {
            fail();
        } catch (TransactionException e) {
            fail();
        }
    }

    @Test
    public void testFindAccountHistory() {
        List<Transaction> accountHistory = accountManager.findAccountHistory(getAccountList(), "FR14 3001 2152");
        Assert.assertNotNull(accountHistory);
        Assert.assertEquals(accountHistory.size(), 2);
    }

    private static List<Account> getAccountList() {

        Transaction transaction1 = new Transaction.Builder()
                .accountId("FR250AAAY")
                .oldBalance(new BigDecimal(200))
                .newBalance(new BigDecimal(300))
                .amount(new BigDecimal(100))
                .build();

        Transaction transaction2 = new Transaction.Builder()
                .accountId("FR250AAAY")
                .oldBalance(new BigDecimal(300))
                .newBalance(new BigDecimal(400))
                .amount(new BigDecimal(100))
                .build();

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account.Builder().id(1l).iban("FR14 3000 2150").balance(new BigDecimal(1450)).idClient("1234").build());
        accounts.add(new Account.Builder().id(2l).iban("FR14 3001 2152").balance(BigDecimal.ZERO).idClient("1234").transactions(Arrays.asList(transaction1, transaction2)).build());

        return accounts;
    }
}
