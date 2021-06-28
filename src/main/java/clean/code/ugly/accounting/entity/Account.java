package clean.code.ugly.accounting.entity;

import java.math.BigDecimal;

public class Account {

    String accountNumber;
    BigDecimal balance;
    boolean isOverdraftAllowed;
    BigDecimal overdraftLimit;

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
