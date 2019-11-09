import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * This class represents a transaction in bank.
 */

public class Transaction {
	
	private static final int TRANSACTION_NUMBER_LENGTH = 12;

    private Date date;

    private Currency currency;

    private TransactionType transactionType;
    
    private final String bankerTransactionTableName = "BANK_TRANSACTION";
    
    private final String bankerTransactionPrimaryKey = "ID";

    private String customer; // customer's username

    private String account; // account number
    
    private String ID;

    private double money;

    public Transaction(double money, Currency currency, TransactionType transactionType, String customer, String account, Date date) {
        this.money = Account.twoDecimal(money);
        this.currency = currency;
        this.transactionType = transactionType;
        this.customer = customer;
        this.account = account;
        this.generateIDNumber();
        this.date = date;
        String dateStr = "mm-dd-yyyy";
        DateFormat df = new SimpleDateFormat(dateStr);
        String[] insertedData = new String[]{account, transactionType.toString(), Double.toString(money), currency.toString(), df.format(date), this.ID};
        Database.insertData(bankerTransactionTableName, insertedData);
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

	public void setDate(Date date) {
		this.date = date;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Currency getCurrency() {
        return this.currency;
    }

    public String getDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        return formatter.format(date);
    }
    
    public String getID() {
    	return this.ID;
    }

    /**
     * Generate a random transaction ID number of 12 digits
     */
    private void generateIDNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TRANSACTION_NUMBER_LENGTH; i++) {
            int number = random.nextInt(10);
            sb.append((char) (number + '0'));
        }
        this.ID = sb.toString();
    }
    
    public String toString() {
        return " " + getDateString() + " | " + this.money + " " + this.currency + " | " + this.transactionType;
    }

    public String toString(Bank bank) {
        return " " + getDateString() + " | " + this.money + " " + this.currency + " | " + this.transactionType + " | Customer " + this.customer + " | " + this.account;
    }
}
