import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * This class is the whole bank, including all the information in the bank.
 */

public class Bank {

    private ArrayList<Customer> customers;

    private ArrayList<Transaction> bankerTransactions;

    private HashMap<String, Stock> stocks;

    public Bank() {
        customers = new ArrayList<>();
        bankerTransactions = new ArrayList<>();
        stocks = new HashMap<>();
    }

    public void registerCustomer(Customer customer) {
        customers.add(customer);
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

    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
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
    // name 在UI里用下拉框选择
    // price 在UI里填写
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
