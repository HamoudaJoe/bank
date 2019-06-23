package com.kata.bank.controller;

import com.kata.bank.converter.AccountConverter;
import com.kata.bank.domain.Account;
import com.kata.bank.domain.Transaction;
import com.kata.bank.dto.AccountDto;
import com.kata.bank.manager.AccountManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountManager accountManager;

    @MockBean
    private AccountConverter accountConverter;

    @Test
    public void testGetAllAccounts() throws Exception {

        //WHEN
        when(accountConverter.convertAccount(any())).thenCallRealMethod();

        // THEN
        mvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAccountBalance() throws Exception {

        // GIVEN
        final Account account = new Account.Builder().balance(new BigDecimal(250)).idClient("AZ122").iban("FR250AAAY").build();
        final AccountDto accountDto = new AccountDto.Builder().balance(new BigDecimal(1200)).idClient("AZ122").iban("FR250AAAY").build();

        // WHEN
        when(accountManager.findAccountById(any(), any())).thenReturn(account);
        when(accountConverter.convertAccount(account)).thenReturn(accountDto);

        // THEN
        mvc.perform(get("/{accountId}", "FR250AAAY")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iban").value(account.getIban()));
    }

    @Test
    public void testGetAccountHistory() throws Exception {

        // GIVEN
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

        final Account account = new Account.Builder()
                .transactions(Arrays.asList(transaction1, transaction2))
                .balance(new BigDecimal(250))
                .idClient("AZ122")
                .iban("FR250AAAY").build();

        // WHEN
        when(accountManager.findAccountById(any(), any())).thenReturn(account);

        // THEN
        mvc.perform(get("/{accountId}/history", "FR250AAAY")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].accountId").value(transaction1.getAccountId()))
                .andExpect(jsonPath("$[0].oldBalance").value(transaction1.getOldBalance()))
                .andExpect(jsonPath("$[0].newBalance").value(transaction1.getNewBalance()));
    }

    @Test
    public void testDoTransaction() throws Exception {

        // GIVEN
        Transaction transaction1 = new Transaction.Builder()
                .accountId("FR250AAAY")
                .oldBalance(new BigDecimal(200))
                .newBalance(new BigDecimal(300))
                .amount(new BigDecimal(100))
                .build();

        final Account account = new Account.Builder()
                .balance(new BigDecimal(250))
                .idClient("AZ122").iban("FR250AAAY")
                .transactions(Arrays.asList(transaction1))
                .build();

        // WHEN
        when(accountManager.doTransaction(any(), any(), any(), any())).thenReturn(account);

        // THEN
        mvc.perform(get("/{accountId}/operation/{operationId}/{amount}", "FR250AAAY", "1", "1250")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }
}
