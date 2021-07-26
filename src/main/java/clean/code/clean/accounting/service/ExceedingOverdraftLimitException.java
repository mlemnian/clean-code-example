package clean.code.clean.accounting.service;

public class ExceedingOverdraftLimitException extends RuntimeException {
    public ExceedingOverdraftLimitException(String msg) {
        super(msg);
    }
}
