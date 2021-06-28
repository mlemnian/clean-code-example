package clean.code.cleaner.accounting.service;

public class InvalidAccountException extends RuntimeException {
    public InvalidAccountException(String msg) {
        super(msg);
    }
}
