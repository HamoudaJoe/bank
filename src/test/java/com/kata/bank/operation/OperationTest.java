package com.kata.bank.operation;

import com.kata.bank.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationTest {

    @Autowired
    private IOperation operation;

    @Test
    public void testDeposit() {
        // GIVEN
        Account account = new Account.Builder().balance(new BigDecimal(250)).idClient("AZ122").iban("FR250AAAY").build();
        account = operation.deposit(account, new BigDecimal(250), "transaction 1 deposit");

        // ASSERT
        assertNotNull(account);
        assertEquals(account.getTransactions().size(), 1);
        assertEquals(account.getTransactions().get(0).getAmount(), new BigDecimal(250));
        assertEquals(account.getTransactions().get(0).getNewBalance(), new BigDecimal(500));
        assertEquals(account.getTransactions().get(0).getDate(), LocalDate.now());
    }


    @Test
    public void testWithDrawalOK() {
        Account account = new Account.Builder().balance(new BigDecimal(250)).idClient("AZ122").iban("FR250AAAY").build();
        try {
            operation.withdrawal(account, new BigDecimal(50), "transaction 1 deposit");

            // ASSERT
            assertNotNull(account);
            assertEquals(account.getTransactions().size(), 1);
            assertEquals(account.getTransactions().get(0).getAmount(), new BigDecimal(50));
            assertEquals(account.getTransactions().get(0).getNewBalance(), new BigDecimal(200));
            assertEquals(account.getTransactions().get(0).getDate(), LocalDate.now());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testWithDrawalNegativeBalance() {
        Account account = new Account.Builder().balance(new BigDecimal(0)).idClient("AZ122").iban("FR250AAAY").build();
        try {
            operation.withdrawal(account, new BigDecimal(50), "transaction 1 deposit");
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "transaction refused : the amount requested is higher than account balance");
        }
    }

}
