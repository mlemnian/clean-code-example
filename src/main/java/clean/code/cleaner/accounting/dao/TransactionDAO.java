package clean.code.cleaner.accounting.dao;

import clean.code.cleaner.accounting.entity.Account;
import clean.code.cleaner.accounting.entity.AccountTransaction;

public class TransactionDAO {

    private TransactionDAO(){}

    public AccountTransaction getLastTransaction(String acno)
    {
        return null;
    }

    public void saveTransaction(Account sourceAccount, Account targetAccount, long amount) {
        // do something
    }
}
