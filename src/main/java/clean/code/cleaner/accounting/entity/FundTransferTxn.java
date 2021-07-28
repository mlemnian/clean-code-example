package clean.code.cleaner.accounting.entity;

import clean.code.cleaner.accounting.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class FundTransferTxn {
    private @NonNull Account sourceAccount;
    private @NonNull Account targetAccount;
    private long amount;
    private boolean allowDuplicateTxn;
}
