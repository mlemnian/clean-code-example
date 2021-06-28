package com.cgi.cleaner.accounting.service;

public class DuplicateTransactionException extends RuntimeException {
    public DuplicateTransactionException(String msg) {
        super(msg);
    }
}
