package clean.code.clean.accounting.dao;

import clean.code.clean.accounting.entity.Account;
import clean.code.clean.accounting.entity.AccountTransaction;

public class TransactionDAO {

    private TransactionDAO(){}

    public static AccountTransaction getLastTransaction(String acno)
    {
        return null;
    }

    public static void saveTransaction(Account sourceAccount, Account targetAccount, long amount) {
      // do something
    }
}
