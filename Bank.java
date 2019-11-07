import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;  

/**
 * This class is the whole bank, including all the information in the bank.
 */

public class Bank {

    private ArrayList<Customer> customers;

    private ArrayList<Transaction> bankerTransactions;

    private HashMap<String, Stock> stocks;

    private final String customerTableName = "CUSTOMER";
    
    private final String bankerTransactionTableName = "BANKTRANSACTION";

    private final String[] customerCreateArgs = {"USERNAME varchar(20) not null", "PASSWORD varchar(20) not null"};
    
    private final String[] bankerTransactionCreateArgs = {"ACCOUNT_NUMBER char(12) not null", "TYPE varchar(20) not null", 
    		"MONEY varchar(20) not null", "CURRENCY varchar(3) not null", "DATE varchar(20) not null, ID char(12) not null"};

    private final String[] customerArgs = {"USERNAME", "PASSWORD"};
    
    private final String[] bankerTransactionArgs = {"ACCOUNT_NUMBER","TYPE", "MONEY", "CURRENCY", "DATE"};

    private final String customerPrimaryKey = "USERNAME";
    
    private final String bankerTransactionPrimaryKey = "ID";

    public Bank() {
        readCustomerFromDatabase();
        readTransactionFromDatabase();
        stocks = new HashMap<>();
    }

    private void readCustomerFromDatabase() {
        customers = new ArrayList<>();
        if (!Database.hasTable(customerTableName)) {
            Database.createTable(customerTableName, customerCreateArgs);
            Database.setPrimaryKey(customerTableName, customerPrimaryKey);
        } else {
        	List<List<String>> customers = Database.queryData(customerTableName, null, null, customerArgs);
            for (List<String> customer : customers) {
                this.customers.add(new Customer(this, customer.get(0), customer.get(1)));
            }
        }
    }

    private void readTransactionFromDatabase() {
        bankerTransactions = new ArrayList<>();
        if (!Database.hasTable(bankerTransactionTableName)) {
            Database.createTable(bankerTransactionTableName, bankerTransactionCreateArgs);
            Database.setPrimaryKey(bankerTransactionTableName, bankerTransactionPrimaryKey);
        } else {
        	List<List<String>> transactions = Database.queryData(bankerTransactionTableName, null, null, bankerTransactionArgs);
            for (List<String> transaction : transactions) {
            	double money = Double.valueOf(transaction.get(2));
            	SimpleDateFormat formatter=new SimpleDateFormat("MM-dd-yyyy"); 
            	try {
					Date date=formatter.parse(transaction.get(4));
					this.bankerTransactions.add(new Transaction(money, Currency.valueOf(transaction.get(3)), 
							TransactionType.valueOf(transaction.get(1)), transaction.get(0), date));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }
    
    public void registerCustomer(Customer customer) {
        customers.add(customer);
        String[] insertedData = new String[]{customer.getUsername(), customer.getPassword()};
        Database.insertData(customerTableName, insertedData);
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Transaction> getBankerTransactions() {
        return bankerTransactions;
    }

    public void addTransaction(Transaction transaction) {
        bankerTransactions.add(transaction);
        String[] insertedData = new String[]{transaction.getAccount(), transaction.getTransactionType().toString(), 
        		String.valueOf(transaction.getMoney()), transaction.getCurrency().toString(), transaction.getDateString(), transaction.getID()};
        Database.insertData(bankerTransactionTableName, insertedData);
    }

    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
        Database.deleteData(customerTableName, customerPrimaryKey, customer.getUsername());
    }

    public void calculateLoanInterest(Date date) {
        for (Customer customer : customers) {
            for (SavingsAccount savingsAccount : customer.getSavingsAccounts()) {
                savingsAccount.calculateLoanInterest(date);
            }
        }
    }

    public void addSaveInterest(int day, Date date) {
        for (Customer customer : customers) {
            for (SavingsAccount savingsAccount : customer.getSavingsAccounts()) {
                savingsAccount.addSaveInterest(day, date);
            }
            for (CheckingAccount checkingAccount : customer.getCheckingAccounts()) {
                checkingAccount.addSaveInterest(day, date);
            }
        }
    }

    public HashMap<Currency, Double> calculateMoneyEarned() {
        HashMap<Currency, Double> moneyEarned;
        moneyEarned = new HashMap<>();
        for (Currency currency : Currency.values()) {
            moneyEarned.put(currency, 0.0);
        }
        for (Transaction transaction : bankerTransactions) {
            Currency currency = transaction.getCurrency();
            double transMoney = transaction.getMoney();
            double earnedMoney = moneyEarned.get(currency);
            moneyEarned.put(currency, Account.twoDecimal(transMoney + earnedMoney));
        }
        return moneyEarned;
    }


    /* 
        Stock Part
    */
    
    public HashMap<String, Stock> getStocks() {
        return this.stocks;
    }

    public void setStockPrice(String name, double price) {
        stocks.get(name).setPrice(price);
    }

    public void deleteStock(String name) {
        if (stocks.get(name).shares > 0) {
            System.out.println("cannot delete a stock with positive shares");
        } 
        else {
            stocks.remove(name);
        }
    }

    public void addNewStock(String name, double price, int shares) {
        Stock newStock = new Stock(name, price, shares);
        stocks.put(name, newStock);
    }
    public void addStockShare(String name, int shares) {
        stocks.get(name).buyShares(shares);
    }

    public void reduceStockShare(String name, int shares) {
        stocks.get(name).sellShares(shares);
    }
}
