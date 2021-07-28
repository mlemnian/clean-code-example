package clean.code.clean.accounting.service;

import clean.code.clean.accounting.dao.AccountDAO;
import clean.code.clean.accounting.dao.TransactionDAO;
import clean.code.clean.accounting.entity.Account;
import clean.code.clean.accounting.entity.FundTransferTxn;
import clean.code.clean.accounting.entity.AccountTransaction;
import clean.code.ugly.accounting.service.UglyMoneyTransferService;
import java.util.logging.Logger;

// Example from https://dzone.com/articles/clean-code-dont-mix-different

public class CleanMoneyTransferService {

    private static final Logger logger = Logger.getLogger(CleanMoneyTransferService.class.getName());

    public void transferFunds(FundTransferTxn txn) {
        Account sourceAccount = validateAndGetAccount(txn.getSourceAccount().getAcno());
        Account targetAccount = validateAndGetAccount(txn.getTargetAccount().getAcno());
        checkForOverdraft(sourceAccount, txn.getAmount());
        checkForDuplicateTransaction(txn);
        makeTransfer(sourceAccount, targetAccount, txn.getAmount());
    }

    private Account validateAndGetAccount(String acno){
        Account account = AccountDAO.getAccount(acno); // Schlechte Testbarkeit!
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
        AccountTransaction lastTxn = TransactionDAO.getLastTransaction(txn.getSourceAccount().getAcno());  // Schlechte Testbarkeit!
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
        TransactionDAO.saveTransaction(sourceAccount, targetAccount, amount);
    }
}
