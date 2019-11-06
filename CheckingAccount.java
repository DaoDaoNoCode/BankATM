import java.math.BigDecimal;
import java.util.Date;

/**
 * This class is the checking account, which extends Account class, and has the function to request a loan.
 */

public class CheckingAccount extends Account {

    public CheckingAccount(Bank bank, String owner, String password, Date date) {
        super(bank, owner, password, date);
        type = AccountType.CHECKING;
    }

    public CheckingAccount(Bank bank, String accountNumber, String owner, String password, Double[] balance, Date date) {
        super(bank, owner, password, date);
        this.number = accountNumber;
        type = AccountType.CHECKING;
        for (int i = 0; i < Currency.values().length; i++) {
            this.deposit.put(Currency.values()[i], balance[i]);
        }
    }

    /**
     * Transfer to another checking account. The fee rate is 1%.
     * @param account account to transfer out
     * @param money money to transfer out
     * @param currency currency to transfer out
     * @param date date of transfer
     * @return -1 if money input is invalid, 0 if deposit is not enough, 1 if transfer successfully
     */
    public int transferOut(CheckingAccount account, double money, Currency currency, Date date) {
        if (money <= 0) {
            return -1;
        }
        double transferFeeRate = 0.01;
        BigDecimal transferFeeRateBig = BigDecimal.valueOf(transferFeeRate);
        BigDecimal moneyBig = BigDecimal.valueOf(money);
        double transferFee = transferFeeRateBig.multiply(moneyBig).doubleValue();
        double balance = deposit.get(currency);
        if (money + transferFee > balance) {
            return 0;
        } else {
            deposit.put(currency, twoDecimal(balance - money - transferFee));
            transactions.add(new Transaction(-money, currency, TransactionType.TRANSFER_OUT, owner, this, date));
            transactions.add(new Transaction(-transferFee, currency, TransactionType.TRANSFER_FEE, owner, this, date));
            account.transferIn(money, currency);
            account.addTransaction(new Transaction(money, currency, TransactionType.TRANSFER_IN, owner, this, date));
            bank.addTransaction(new Transaction(transferFee, currency, TransactionType.TRANSFER_FEE, owner, this, date));
            return 1;
        }
    }

    private void transferIn(double money, Currency currency) {
        double balance = deposit.get(currency);
        deposit.put(currency, twoDecimal(balance + money));
    }

    @Override
    public String toString() {
        return type + " - " + number;
    }
}
