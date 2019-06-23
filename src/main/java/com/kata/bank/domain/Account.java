package com.kata.bank.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Account implements Serializable {

    private static final long serialVersionUID = 5877223637863713930L;

    private long id;

    private String iban;

    private String idClient;

    private BigDecimal balance;

    private List<Transaction> transactions;

    public Account() {
        super();
    }

    private Account(Builder builder) {
        this.id = builder.id;
        this.iban = builder.iban;
        this.idClient = builder.idClient;
        this.balance = builder.balance;
        this.transactions = builder.transactions;
    }

    public static Builder newAccount() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public BigDecimal getBalance() {
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public static final class Builder {
        private long id;
        private String iban;
        private String idClient;
        private BigDecimal balance;
        private List<Transaction> transactions;

        public Builder() {
            super();
        }

        public Account build() {
            return new Account(this);
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder iban(String iban) {
            this.iban = iban;
            return this;
        }

        public Builder idClient(String idClient) {
            this.idClient = idClient;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder transactions(List<Transaction> transactions) {
            this.transactions = transactions;
            return this;
        }
    }
}
