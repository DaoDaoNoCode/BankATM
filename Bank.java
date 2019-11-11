import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class is the whole bank, including all the information in the bank.
 */

public class Bank {
    private  Date date;

    private ArrayList<Customer> customers;

    private ArrayList<Transaction> bankerTransactions;

    private HashMap<String, Stock> stocks;

    private final String customerTableName = "CUSTOMER";

    private  final String bankerTableName = "BANKER";

    private final String bankerTransactionTableName = "BANK_TRANSACTION";

    private final String stockTableName = "STOCK";

    private  final String[] bankerCreateArgs = {"USERNAME varchar(20) not null", "PASSWORD varchar(20) not null", "DATE varchar(20) not null"};

    private  final String[] bankerArgs = {"USERNAME", "PASSWORD", "DATE"};

    private final String[] customerCreateArgs = {"USERNAME varchar(20) not null", "PASSWORD varchar(20) not null"};
    
    private final String[] bankerTransactionCreateArgs = {"ACCOUNT_NUMBER char(12) not null", "CUSTOMER varchar(20) not null", "TYPE varchar(20) not null",
    		"MONEY varchar(20) not null", "CURRENCY varchar(3) not null", "DATE varchar(20) not null, ID varchar(20) not null"};

    private final String[] stockCreateArgs = {"STOCK_NAME varchar(20) not null", "PRICE varchar(20) not null",
            "SHARE varchar(20) not null", "CHANGE_PERCENTAGE varchar(20) not null"};

    private final String[] customerArgs = {"USERNAME", "PASSWORD"};
    
    private final String[] bankerTransactionArgs = {"ACCOUNT_NUMBER", "CUSTOMER", "TYPE", "MONEY", "CURRENCY", "DATE", "ID"};

    private final String[] stockArgs = {"STOCK_NAME", "PRICE", "SHARE", "CHANGE_PERCENTAGE"};

    private final String customerPrimaryKey = "USERNAME";
    
    private final String bankerTransactionPrimaryKey = "ID";

    private final String stockPrimaryKey = "STOCK_NAME";

    private final String bankerPrimaryKey = "USERNAME";
    
    private final String bankerName = "admin";
    
    private final String bankerPW = "admin";

    public Bank() {
        readCustomerFromDatabase();
        readTransactionFromDatabase();
        readStockFromDatabase();
        readBankerFromDatabase();
    }
    
    public Date getDate() {
        List<List<String>> bankers = Database.queryData(bankerTableName, null, null, bankerArgs);
        for (List<String> banker : bankers) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                return formatter.parse(banker.get(2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new Date();
    }
    
    private String getDateString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        return formatter.format(date);
    }
    /**
     * access to the database for the existing data
     */
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
                TransactionType type = TransactionType.valueOf(transaction.get(2));
                if (type == TransactionType.BALANCE_REMAINED_WHEN_CLOSE || type == TransactionType.CLOSE_ACCOUNT_FEE || type == TransactionType.OPEN_ACCOUNT_FEE || type == TransactionType.SAVE_INTEREST || type == TransactionType.TRANSFER_FEE || type == TransactionType.LOAN_INTEREST || type == TransactionType.WITHDRAW_FEE) {
                    double money = Double.valueOf(transaction.get(3));
                    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                    try {
                        Date date = formatter.parse(transaction.get(5));
                        this.bankerTransactions.add(new Transaction(money, Currency.valueOf(transaction.get(4)),
                                TransactionType.valueOf(transaction.get(2)), transaction.get(1), transaction.get(0), Integer.parseInt(transaction.get(6)), date));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    private void readBankerFromDatabase() {
        if (!Database.hasTable(bankerTableName)) {
            Database.createTable(bankerTableName, bankerCreateArgs);
            Database.setPrimaryKey(bankerTableName, bankerPrimaryKey);
        }
        else {
            List<List<String>> bankers = Database.queryData(bankerTableName, null, null, bankerArgs);
            if (bankers.size() == 0) {
                date = new Date();
                String[] values = {bankerName, bankerPW, getDateString(date)};
                System.out.println(getDateString(date));
                Database.insertData(bankerTableName, values);
            }
            else {
                for (List<String> banker : bankers) {
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                        this.date = formatter.parse(banker.get(2));
                    } catch(ParseException e){
                        e.printStackTrace();
                }
            }

            }
        }
    }
    
    private  void initializeStock(String name, double price, int shares, double change) {
        String[] insertArgs = {name, Double.toString(price), Integer.toString(shares), Double.toString(change) };
        Database.insertData(stockTableName, insertArgs);
    }
    
    private void readStockFromDatabase() {
        stocks = new HashMap<>();
        if (!Database.hasTable(stockTableName)) {
            Database.createTable(stockTableName, stockCreateArgs);
            Database.setPrimaryKey(stockTableName, stockPrimaryKey);
            initializeStock("GENERAL ELECTRIC", 11.52, 10000, 0.0);
            initializeStock("AMD", 36.29, 10000, 0.0);
            initializeStock("BOA", 33.26, 10000, 0.0);
            initializeStock("GAP", 16.68, 10000, 0.0);
            initializeStock("DISNEY", 137.96, 10000, 0.0);
            initializeStock("SEALED AIR", 40.02, 10000, 0.0);
            initializeStock("NWS", 13.23, 10000, 0.0);
            initializeStock("IRM", 32.54, 10000, 0.0);
            initializeStock("APPLE", 260.14, 10000,0.0);
            initializeStock("MICROSOFT", 145.96, 10000,0.0);
            initializeStock("GOOGLE", 1309.30, 10000,0.0);
        }
        List<List<String>> res = Database.queryData(stockTableName, null, null, stockArgs);
        for (List<String> stock: res) {
            double price = Double.valueOf(stock.get(1));
            int share = Integer.valueOf(stock.get(2));
            double change = Double.valueOf(stock.get(3));
            this.stocks.put(stock.get(0), new Stock(stock.get(0), price, share, change));
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
    }
    
    public String getBankerName() {
    		return this.bankerName;
    }
    
    public String getBankerPW() {
    		return this.bankerPW;
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
            double transMoney = -transaction.getMoney();
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
        // Find old price from Database
        String[] queryIndex = {"STOCK_NAME"};
        String[] queryValue = {name};
        List<List<String>> ls = Database.queryData(bankerTableName, queryIndex, queryValue, stockArgs);
        for (List<String> stock : ls) {
            double prevPrice = Double.valueOf(stock.get(1));
            double change = (price - prevPrice) / prevPrice;

            // update change in Database
            String[] args = {"CHANGE_PERCENTAGE"};
            String[] updateValues = {Double.toString(change)};
            Database.updateData(stockTableName, "STOCK_NAME", name, args, updateValues);
        }

        stocks.get(name).setPrice(price);
        String[] updateArgs = {"PRICE"};
        String[] updateValues = {Double.toString(price)};
        Database.updateData(stockTableName, "STOCK_NAME", name, updateArgs, updateValues);
    }


    public HashMap<String, Integer> getBuyersNum() {
        HashMap<String, Integer> res = new HashMap<>();
        for (String key : stocks.keySet()) {
            res.put(key ,0);
        }
        for (Customer customer : customers) {
            HashMap<String, Stock> temp = customer.getSecurityAccounts().getStocks();
            for (String s : temp.keySet()) {
                // update shares bought sum
                res.put(s, res.get(s) + temp.get(s).shares);
            }
        }
        return res;
    }
    public boolean deleteStock(String name) {
        if (stocks.get(name).shares > 0) {
            return false;
        } 
        else {
            // delete table
            stocks.remove(name);
            Database.deleteData(stockTableName, stockPrimaryKey, stocks.get(name).getName());
            return true;
        }
    }

    public void addNewStock(String name, double price, int shares, double change) {
        Stock newStock = new Stock(name, price, shares, change);
        stocks.put(name, newStock);
        String[] insertArgs = {name, Double.toString(price), Integer.toString(shares), Double.toString(change) };
        Database.insertData(stockTableName, insertArgs);

    }
    public void addStockShare(String name, int shares) {
        stocks.get(name).buyShares(shares);
        String[] updateArgs = {"SHARE"};
        String[] updateValues = {"SHARE + " + shares};
        Database.updateData(stockTableName, "STOCK_NAME", name, updateArgs, updateValues);
    }

    public void reduceStockShare(String name, int shares) {
        stocks.get(name).sellShares(shares);
        String[] updateArgs = {"SHARE"};
        String[] updateValues = {"SHARE - " + shares};
        Database.updateData(stockTableName, "STOCK_NAME", name, updateArgs, updateValues);
    }
 
    public String[][] stockTable(){
    		String[][] table = new String[stocks.size()][4];
    		int i = 0;
    		for (String name: stocks.keySet()) {
    			table[i][0] = name;
    			table[i][1] = String.valueOf(stocks.get(name).getUSDPrice());
    			table[i][2] = String.valueOf(stocks.get(name).getChange());
    			table[i][3] = String.valueOf(stocks.get(name).getShares());
    			i++;
    		}
    		return table;
    }
    
    public String[][] holderTable(String stockName){
    		ArrayList<String[]> tableList = new ArrayList<String[]>();
    		String[] firstLine = {"Total", String.valueOf(stocks.get(stockName).getShares())};
    		tableList.add(firstLine);
    		for (Customer customer: customers) {
    			if (customer.getSecurityAccounts()!=null 
    					&& customer.getSecurityAccounts().hasStock(stockName)) {
    				String[] line = {customer.toString(), 
    						String.valueOf(customer.getSecurityAccounts().getStocks().get(stockName).getShares())};
    				tableList.add(line);
    			}
    		}
    		String[][] table = new String[tableList.size()][2];
    		for (int i=0; i<tableList.size(); i++)
    			table[i] = tableList.get(i);
    		return table;
    }
}
