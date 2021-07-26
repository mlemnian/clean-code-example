package clean.code.clean.accounting.service;

import clean.code.clean.accounting.dao.AccountDAO;
import clean.code.clean.accounting.dao.TransactionDAO;
import clean.code.clean.accounting.entity.Account;
import clean.code.clean.accounting.entity.FundTransferTxn;
import clean.code.clean.accounting.entity.AccountTransaction;

// Example from https://dzone.com/articles/clean-code-dont-mix-different

public class CleanMoneyTransferService {

    public void transferFunds(FundTransferTxn txn) {
        Account sourceAccount = validateAndGetAccount(txn.getSourceAccount().getAcno());
        Account targetAccount = validateAndGetAccount(txn.getTargetAccount().getAcno());
        checkForOverdraft(sourceAccount, txn.getAmount());
        checkForDuplicateTransaction(txn);
        makeTransfer(sourceAccount, targetAccount, txn.getAmount());
    }

    private Account validateAndGetAccount(String acno){
        Account account = AccountDAO.getAccount(acno); // nicht gut!
        if(account == null) {
            throw new InvalidAccountException("Invalid ACNO :" + acno);
        }
        return account;
    }

    private void checkForOverdraft(Account account, long amount) {
        if(!account.isOverdraftAllowed()) {
            if(account.getBalance() - amount < 0L) {
                throw new InsufficientBalanceException("Insufficient Balance");
            }
        } else {
            if(account.getBalance() + account.getOverdraftLimit() - amount < 0L) {
                throw new ExceedingOverdraftLimitException("Insufficient Balance, Exceeding Overdraft Limit");
            }
        }
    }

    private void checkForDuplicateTransaction(FundTransferTxn txn) {
        AccountTransaction lastTxn = TransactionDAO.getLastTransaction(txn.getSourceAccount().getAcno());  // nicht gut!
        if(lastTxn != null) {
            if(lastTxn.getTargetAcno().equals(txn.getTargetAccount().getAcno())
                    && lastTxn.getAmount() == txn.getAmount()
                    && !txn.isAllowDuplicateTxn()) {
                throw new DuplicateTransactionException("Duplicate transaction exception");
            }
        }
    }

    private void makeTransfer(Account sourceAccount, Account targetAccount, long amount) {
        sourceAccount.debit(amount);
        targetAccount.credit(amount);
        //TransactionService.saveTransaction(source, target, amount);
    }
}
