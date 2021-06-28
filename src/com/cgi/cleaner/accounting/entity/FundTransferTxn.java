package com.cgi.cleaner.accounting.entity;

import java.math.BigDecimal;

public class FundTransferTxn {

    private Account sourceAccount;
    private Account targetAccount;
    private BigDecimal amount;
    private boolean allowDuplicateTxn;

    FundTransferTxn(Account sourceAccount, Account targetAccount, BigDecimal amount, boolean allowDuplicateTxn) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isAllowDuplicateTxn() {
        return allowDuplicateTxn;
    }

    public void setAllowDuplicateTxn(boolean allowDuplicateTxn) {
        this.allowDuplicateTxn = allowDuplicateTxn;
    }
}
