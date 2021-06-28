package clean.code.cleaner.accounting.entity;

import java.math.BigDecimal;

public class Account {

    String accountNumber;
    BigDecimal balance;
    boolean isOverdraftAllowed;
    BigDecimal overdraftLimit;

    public Account(String accountNumber, BigDecimal balance, boolean isOverdraftAllowed, BigDecimal overdraftLimit)
    {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.isOverdraftAllowed = isOverdraftAllowed;
        this.overdraftLimit = overdraftLimit;
    }

    public String getAcno() {
        return this.accountNumber;
    }

    public boolean isOverdraftAllowed() {
        return this.isOverdraftAllowed;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void debit(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public BigDecimal getOverdraftLimit() {
        return this.overdraftLimit;
    }
}
