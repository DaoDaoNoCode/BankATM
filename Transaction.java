import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents a transaction in bank.
 */

public class Transaction {

    private Date date;

    private Currency currency;
    private TransactionType transactionType;

    private String customer;

    private Account account;

    private double money;

    public Transaction(double money, Currency currency, TransactionType transactionType, String customer, Account account) {
        this.money = Account.twoDecimal(money);
        this.currency = currency;
        this.transactionType = transactionType;
        this.customer = customer;
        this.account = account;
        this.date = new Date();
    }

    public Transaction(double money, Currency currency, TransactionType transactionType, String customer, Account account, Date date) {
        this.money = Account.twoDecimal(money);
        this.currency = currency;
        this.transactionType = transactionType;
        this.customer = customer;
        this.account = account;
        this.date = date;
    }

    public double getMoney() {
        return this.money;
    }

    public Date getDate() {
        return this.date;
    }

    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    private String getDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        return formatter.format(date);
    }

    public String toString() {
        return " " + getDateString() + " | " + this.money + " " + this.currency + " | " + this.transactionType;
    }

    public String toString(Bank bank) {
        return " " + getDateString() + " | " + this.money + " " + this.currency + " | " + this.transactionType + " | Customer " + this.customer + " | " + this.account;
    }
}
