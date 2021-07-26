package clean.code.cleaner.accounting.service;

import clean.code.cleaner.accounting.dao.AccountDAO;
import clean.code.cleaner.accounting.dao.TransactionDAO;
import clean.code.cleaner.accounting.entity.Account;
import clean.code.cleaner.accounting.entity.AccountTransaction;
import clean.code.cleaner.accounting.entity.FundTransferTxn;
import javax.inject.Inject;

// Example from https://dzone.com/articles/clean-code-dont-mix-different

public class CleanerMoneyTransferService {

    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    @Inject
    CleanerMoneyTransferService(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }

    public void transferFunds(FundTransferTxn txn) {
        Account sourceAccount = validateAndGetAccount(txn.getSourceAccount().getAcno());
        Account targetAccount = validateAndGetAccount(txn.getTargetAccount().getAcno());
        checkForOverdraft(sourceAccount, txn.getAmount());
        checkForDuplicateTransaction(txn);
        makeTransfer(sourceAccount, targetAccount, txn.getAmount());
    }

    private Account validateAndGetAccount(String acno){
        Account account = accountDAO.getAccount(acno);
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
        AccountTransaction lastTxn = transactionDAO.getLastTransaction(txn.getSourceAccount().getAcno());
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
