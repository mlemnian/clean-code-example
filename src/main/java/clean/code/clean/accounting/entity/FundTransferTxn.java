package clean.code.clean.accounting.entity;

public class FundTransferTxn {

    private Account sourceAccount;
    private Account targetAccount;
    private long amount;
    private boolean allowDuplicateTxn;

    FundTransferTxn(Account sourceAccount, Account targetAccount, long amount, boolean allowDuplicateTxn) {
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.allowDuplicateTxn = allowDuplicateTxn;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Account targetAccount) {
        this.targetAccount = targetAccount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public boolean isAllowDuplicateTxn() {
        return allowDuplicateTxn;
    }

    public void setAllowDuplicateTxn(boolean allowDuplicateTxn) {
        this.allowDuplicateTxn = allowDuplicateTxn;
    }
}
