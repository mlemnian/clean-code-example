package clean.code.cleaner.accounting.service;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String msg) {
        super(msg);
    }
}
