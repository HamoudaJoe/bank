package com.kata.bank.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction implements Serializable {

    private static final long serialVersionUID = -241818269903406216L;

    private long id;

    private LocalDate date;

    private BigDecimal amount;

    private String description;

    private OperationType type;

    private String accountId;

    private BigDecimal oldBalance;

    private BigDecimal newBalance;

    public Transaction() {
        super();
    }

    private Transaction(Builder builder) {
        this.id = builder.id;
        this.date = builder.date;
        this.amount = builder.amount;
        this.description = builder.description;
        this.type = builder.type;
        this.accountId = builder.accountId;
        this.oldBalance = builder.oldBalance;
        this.newBalance = builder.newBalance;
    }

    public static Builder newTransaction() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public BigDecimal getOldBalance() {
        return oldBalance;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public static final class Builder {
        private long id;
        private LocalDate date;
        private BigDecimal amount;
        private String description;
        private OperationType type;
        private String accountId;
        private BigDecimal oldBalance;
        private BigDecimal newBalance;

        public Builder() {
            super();
        }

        public Transaction build() {
            return new Transaction(this);
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder type(OperationType type) {
            this.type = type;
            return this;
        }

        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder newBalance(BigDecimal newBalance) {
            this.newBalance = newBalance;
            return this;
        }

        public Builder oldBalance(BigDecimal oldBalance) {
            this.oldBalance = oldBalance;
            return this;
        }
    }
}
