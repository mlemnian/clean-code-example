package clean.code.clean.accounting.dao;

import clean.code.clean.accounting.entity.Account;

public class AccountDAO {

    private AccountDAO(){}

    public static Account getAccount(String acno) {
        return new Account(acno, 10L, true, 10L);
    }
}
