import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents a transaction in bank.
 */

public class Transaction {

    private static final int TRANSACTION_NUMBER_LENGTH = 12;
    private final String bankerTransactionTableName = "BANK_TRANSACTION";
    private final String bankerTransactionPrimaryKey = "ID";
    private Date date;
    private Currency currency;
    private TransactionType transactionType;
    private String customer; // customer's username

    private String account; // account number

    private int ID;

    private double money;

    public Transaction(double money, Currency currency, TransactionType transactionType, String customer, String account, Date date) {
        this.money = CommonMathMethod.twoDecimal(money);
        this.currency = currency;
        this.transactionType = transactionType;
        this.customer = customer;
        this.account = account;
        this.generateIDNumber();
        this.date = date;
    }

    public Transaction(double money, Currency currency, TransactionType transactionType, String customer, String account, int ID, Date date) {
        this.money = CommonMathMethod.twoDecimal(money);
        this.currency = currency;
        this.transactionType = transactionType;
        this.customer = customer;
        this.account = account;
        this.ID = ID;
        this.date = date;
    }

    public void insertTransactionIntoDatabase() {
        String dateStr = "MM-dd-yyyy";
        DateFormat df = new SimpleDateFormat(dateStr);
        String[] insertedData = new String[]{account, customer, transactionType.toString(), Double.toString(money), currency.toString(), df.format(date), String.valueOf(this.ID)};
        Database.insertData(bankerTransactionTableName, insertedData);
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        return formatter.format(date);
    }

    public int getID() {
        return this.ID;
    }

    private void generateIDNumber() {
        String[] args = {"COUNT(*)"};
        String resultStr = Database.queryData(bankerTransactionTableName, null, null, args).get(0).get(0);
        System.out.println(resultStr);
        int resultNum = Integer.valueOf(resultStr);
        this.ID = resultNum + 1;
    }

    public String toString() {
        return " " + getDateString() + " | " + this.money + " " + this.currency + " | " + this.transactionType;
    }

    public String toString(Bank bank) {
        return " " + getDateString() + " | " + this.money + " " + this.currency + " | " + this.transactionType + " | Customer " + this.customer + " | Account " + this.account;
    }
}
