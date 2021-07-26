package clean.code.cleaner.accounting.dao;

import clean.code.cleaner.accounting.entity.Account;

public class AccountDAO {

    private AccountDAO(){}

    public Account getAccount(String acno) {
        return new Account(acno, 10L, true, 10L);
    }
}
