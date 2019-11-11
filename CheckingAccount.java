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
        super(bank, owner, password, accountNumber, date);
        System.out.println(this.number);
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
        double transferFee = CommonMathMethod.bigDecimalMultiply(transferFeeRate, money);
        double balance = deposit.get(currency);
        if (money + transferFee > balance) {
            return 0;
        } else {
            deposit.put(currency, CommonMathMethod.twoDecimal(balance - money - transferFee));
            updateDepositInDatabase(currency);
            Transaction transaction1 = new Transaction(-money, currency, TransactionType.TRANSFER_OUT, owner, super.getNumber(), date);
            transaction1.insertTransactionIntoDatabase();
            Transaction transaction2 = new Transaction(-transferFee, currency, TransactionType.TRANSFER_FEE, owner, super.getNumber(), date);
            transaction2.insertTransactionIntoDatabase();
            Transaction transaction3 = new Transaction(money, currency, TransactionType.TRANSFER_IN, account.getOwner(), account.getNumber(), date);
            transaction3.insertTransactionIntoDatabase();
            transactions.add(transaction1);
            transactions.add(transaction2);
            account.transferIn(money, currency);
            account.addTransaction(transaction3);
            bank.addTransaction(transaction2);
            return 1;
        }
    }

    private void transferIn(double money, Currency currency) {
        double balance = deposit.get(currency);
        deposit.put(currency, CommonMathMethod.twoDecimal(balance + money));
        updateDepositInDatabase(currency);
    }

    @Override
    public String toString() {
        return type + " - " + number;
    }
}
