package clean.code.cleaner.accounting.service;

import clean.code.clean.accounting.entity.FundTransferTxn;
import clean.code.clean.accounting.service.CleanMoneyTransferService;
import clean.code.cleaner.accounting.dao.AccountDAO;
import clean.code.cleaner.accounting.dao.TransactionDAO;
import clean.code.cleaner.accounting.entity.Account;
import clean.code.cleaner.accounting.entity.AccountTransaction;
import java.util.logging.Logger;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

// Example from https://dzone.com/articles/clean-code-dont-mix-different

//@Log
//@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CleanerMoneyTransferService {

    private static final Logger logger = Logger.getLogger(CleanerMoneyTransferService.class.getName());

    private final @NonNull AccountDAO accountDAO;
    private final @NonNull TransactionDAO transactionDAO;

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
        transactionDAO.saveTransaction(sourceAccount, targetAccount, amount);
    }
}
