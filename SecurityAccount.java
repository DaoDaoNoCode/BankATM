import java.util.HashMap;
import java.util.Date;

public class SecurityAccount extends Account {

    private HashMap<String, Stock> stocks;
    public SecurityAccount(Bank bank, String accountNumber, String owner, String password, Date date) {
        super(bank, owner, password, date);
        this.number = accountNumber;
        type = AccountType.SECURITY;
        stocks = new HashMap<>();
    }
    public SecurityAccount(Bank bank, String owner, String password, Date date) {
        super(bank, owner, password, date);
        type = AccountType.SECURITY;
        stocks = new HashMap<>();
    }

    /* 
        UI 给出下拉选项: 
        currency, saving account, 
        Stockname, shares
    */                
    public void buyStock (String name, int shares, Currency currency, SavingsAccount account, Date date) {
        Stock bankStock = bank.getStocks().get(name);

        if (shares > bankStock.shares) {
            System.out.println("Cannot buy more than bank's inventory!");
            return;
        }

        double amount = 0;
        switch(currency) {
            case USD: 
                amount = twoDecimal(shares * bankStock.getUSDPrice());
            case CNY:
                amount = twoDecimal(shares * bankStock.getRMBPrice());
            case EUR: 
                amount = twoDecimal(shares * bankStock.getEurPrice());
        }
        
        if (account.getDeposit(currency) < amount) {
            System.out.println("Saving account money insufficient!");
        }
        else {
            // update owner stocks
            if (stocks.containsKey(name)) {
                stocks.get(name).buyShares(shares);
            } else {
                stocks.put(name, new Stock(name, bankStock.price, shares));
            }
            // reduce money on saving account
            account.withdraw(amount, currency, date);
            // update bank stocks
            bankStock.sellShares(shares);
            
        }
    }

    public void sellStock (String name, int shares, Currency currency, SavingsAccount account, Date date) {
        if (shares > stocks.get(name).shares) {
            System.out.println("Cannot sell more than current biggest shares");
            return;
        }
        
        // add money on saving account
        double amount = twoDecimal(shares *bank.getStocks().get(name).price);
        account.save(amount, currency, date);
        // update owner stocks
        stocks.get(name).sellShares(shares);
        // update bank stocks
        bank.getStocks().get(name).buyShares(shares);
    }
}
