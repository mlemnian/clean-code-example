package com.cgi.cleaner.accounting.dao;

import com.cgi.cleaner.accounting.entity.Account;

import java.math.BigDecimal;

public class AccountDAO {

    private AccountDAO(){

    }

    public static Account getAccount(String acno) {
        return new Account(acno, BigDecimal.TEN, true, BigDecimal.TEN);
    }
}
