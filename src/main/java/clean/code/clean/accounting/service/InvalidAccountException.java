package clean.code.clean.accounting.service;

public class InvalidAccountException extends RuntimeException {
    public InvalidAccountException(String msg) {
        super(msg);
    }
}
