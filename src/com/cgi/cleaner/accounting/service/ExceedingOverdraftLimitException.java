package com.cgi.cleaner.accounting.service;

public class ExceedingOverdraftLimitException extends RuntimeException {
    public ExceedingOverdraftLimitException(String msg) {
        super(msg);
    }
}
