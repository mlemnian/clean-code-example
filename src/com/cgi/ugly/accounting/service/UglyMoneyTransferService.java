package com.cgi.ugly.accounting.service;

import com.cgi.ugly.accounting.entity.Account;
import com.cgi.ugly.accounting.entity.AccountTransaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

// Example from https://dzone.com/articles/clean-code-dont-mix-different

public class UglyMoneyTransferService {

    private static final Logger logger = Logger.getLogger(UglyMoneyTransferService.class.getName());

    public void transferFunds(Account source, Account target, BigDecimal amount, boolean allowDuplicateTxn)
            throws IllegalArgumentException, RuntimeException {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("Select * from accounts where acno = ?");
            pstmt.setString(1, source.getAcno());
            ResultSet rs = pstmt.executeQuery();
            Account sourceAccount = null;
            if(rs.next()) {
                sourceAccount = new Account();
                //populate account properties from ResultSet
            }
            if(sourceAccount == null) {
                throw new IllegalArgumentException("Invalid Source ACNO");
            }
            Account targetAccount = null;
            pstmt.setString(1, target.getAcno());
            rs = pstmt.executeQuery();
            if(rs.next()) {
                targetAccount = new Account();
                //populate account properties from ResultSet
            }
            if(targetAccount == null) {
                throw new IllegalArgumentException("Invalid Target ACNO");
            }
            if(!sourceAccount.isOverdraftAllowed()) {
                if((sourceAccount.getBalance().subtract(amount)).compareTo(BigDecimal.ZERO) < 0) {
                    throw new RuntimeException("Insufficient Balance");
                }
            } else {
                if(((sourceAccount.getBalance().add(sourceAccount.getOverdraftLimit())).subtract(amount)).compareTo(BigDecimal.ZERO) < 0) {
                    throw new RuntimeException("Insufficient Balance, Exceeding Overdraft Limit");
                }
            }
            AccountTransaction lastTxn = null ; //JDBC code to obtain last transaction of sourceAccount
            if(lastTxn != null) {
                if(lastTxn.getTargetAcno().equals(targetAccount.getAcno()) && lastTxn.getAmount().equals(amount) && !allowDuplicateTxn) {
                    throw new RuntimeException("Duplicate transaction exception");//ask for confirmation and proceed
                }
            }
            sourceAccount.debit(amount);
            targetAccount.credit(amount);
            //TransactionService.saveTransaction(source, target, amount);
        } catch(Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            try {
                conn.close();
            } catch(Exception e) {
                // Not everything is in your control..sometimes we have to believe in GOD/JamesGosling and proceed
            }
        }
    }
}
