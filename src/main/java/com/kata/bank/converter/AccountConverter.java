package com.kata.bank.converter;

import com.kata.bank.domain.Account;
import com.kata.bank.dto.AccountDto;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {

    /**
     * convert an entity account to a accountDto
     *
     * @param account
     * @return account dto
     */
    public AccountDto convertAccount(Account account) {
        return new AccountDto.Builder()
                .iban(account.getIban())
                .balance(account.getBalance())
                .idClient(account.getIdClient())
                .build();
    }
}
