package clean.code.ugly.accounting.entity;

public class Account {

    String accountNumber;
    long balance;
    boolean isOverdraftAllowed;
    long overdraftLimit;

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
