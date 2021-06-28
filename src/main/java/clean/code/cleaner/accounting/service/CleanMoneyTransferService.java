package clean.code.cleaner.accounting.service;

import clean.code.cleaner.accounting.dao.AccountDAO;
import clean.code.cleaner.accounting.dao.TransactionDAO;
import clean.code.cleaner.accounting.entity.Account;
import clean.code.cleaner.accounting.entity.AccountTransaction;
import clean.code.cleaner.accounting.entity.FundTransferTxn;

import java.math.BigDecimal;

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

    private void checkForOverdraft(Account account, BigDecimal amount) {
        if(!account.isOverdraftAllowed()) {
            if((account.getBalance().subtract(amount)).compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientBalanceException("Insufficient Balance");
            }
        } else {
            if(((account.getBalance().add(account.getOverdraftLimit())).subtract(amount)).compareTo(BigDecimal.ZERO) < 0) {
                throw new ExceedingOverdraftLimitException("Insufficient Balance, Exceeding Overdraft Limit");
            }
        }
    }

    private void checkForDuplicateTransaction(FundTransferTxn txn) {
        AccountTransaction lastTxn = TransactionDAO.getLastTransaction(txn.getSourceAccount().getAcno());  // nicht gut!
        if(lastTxn != null) {
            if(lastTxn.getTargetAcno().equals(txn.getTargetAccount().getAcno())
                    && lastTxn.getAmount().equals(txn.getAmount())
                    && !txn.isAllowDuplicateTxn()) {
                throw new DuplicateTransactionException("Duplicate transaction exception");
            }
        }
    }

    private void makeTransfer(Account sourceAccount, Account targetAccount, BigDecimal amount) {
        sourceAccount.debit(amount);
        targetAccount.credit(amount);
        //TransactionService.saveTransaction(source, target, amount);
    }
}
