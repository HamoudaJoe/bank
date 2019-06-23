package com.kata.bank.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountDto implements Serializable {

    private static final long serialVersionUID = 3197418772576161866L;

    private String iban;

    private BigDecimal balance;

    private String idClient;

    private AccountDto(Builder builder) {
        this.iban = builder.iban;
        this.balance = builder.balance;
        this.idClient = builder.idClient;
    }

    public static Builder newAccountDto() {
        return new Builder();
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public static final class Builder {
        private String iban;
        private BigDecimal balance;
        private String idClient;

        public Builder() {
            super();
        }

        public AccountDto build() {
            return new AccountDto(this);
        }

        public Builder iban(String iban) {
            this.iban = iban;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder idClient(String idClient) {
            this.idClient = idClient;
            return this;
        }
    }
}
