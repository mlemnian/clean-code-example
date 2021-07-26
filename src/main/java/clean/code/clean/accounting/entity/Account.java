package clean.code.clean.accounting.entity;

public class Account {

    String accountNumber;
    long balance;
    boolean isOverdraftAllowed;
    long overdraftLimit;

    public Account(String accountNumber, long balance, boolean isOverdraftAllowed, long overdraftLimit)
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

    public long getBalance() {
        return this.balance;
    }

    public void debit(long amount) {
        this.balance -= amount;
    }

    public void credit(long amount) {
        this.balance += amount;
    }

    public long getOverdraftLimit() {
        return this.overdraftLimit;
    }
}
