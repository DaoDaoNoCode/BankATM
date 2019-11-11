import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;

public class SecurityAccount extends Account {
    private final String stockDealPrimaryKey = "DEAL_ID";

    private final String[] stockDealCreateArgs = {"DEAL_ID char(12) not null", "ACCOUNT_NUMBER char(12) not null",
            "STOCK_NAME varchar(20) not null", "STOCK_SHARE varchar(20) not null"};

    private final String[] stockDealArgs = {"DEAL_ID", "ACCOUNT_NUMBER", "STOCK_NAME", "STOCK_SHARE"};

    private final String[] stockArgs = {"STOCK_NAME", "PRICE", "SHARE"};

    private final String stockTableName = "STOCK";

    private final String stockDealTableName = "STOCK_DEAL";

    private HashMap<String, Stock> stocks = new HashMap<>();
    private HashMap<String, StockDeal> stockDeals = new HashMap<>(); // records the manipulation towards the stocks of one account

    public SecurityAccount(Bank bank, String accountNumber, String owner, String password, Date date) {
        super(bank, owner, password, date);
        this.number = accountNumber;
        type = AccountType.SECURITY;
        readStocksBoughtFromDatabase();
    }
    public SecurityAccount(Bank bank, String owner, String password, Date date) {
        super(bank, owner, password, date);
        type = AccountType.SECURITY;
        stocks = new HashMap<>();
    }
    public HashMap<String, Stock> getStocks() {
        return this.stocks;
    }
    private void readStocksBoughtFromDatabase() {
        if (!Database.hasTable(stockDealTableName)) {
            Database.createTable(stockDealTableName, stockDealCreateArgs);
            Database.setPrimaryKey(stockDealTableName, stockDealPrimaryKey);
        } else {
                String[] queryIndex = {"ACCOUNT_NUMBER"};
                String[] queryValue = {this.number};
                List<List<String>> stocks = Database.queryData(stockDealTableName, queryIndex, queryValue, stockDealArgs);
                for (List<String> stock: stocks) {
                    int shares = Integer.valueOf(stock.get(3));
                    String stockName = stock.get(2);
                    String[] queryStockIndex = {"STOCK_NAME"};
                    String[] queryStockValue = {stockName};
                    List<List<String>> stockPrice = Database.queryData(stockTableName, queryStockIndex, queryStockValue, stockArgs);
                    for (List p: stockPrice) {
                        double price = Double.valueOf(1);
                        this.stocks.put(stock.get(0), new Stock(stock.get(1), price, shares));

                    }
                    int s = Integer.valueOf(stock.get(3));
                    this.stockDeals.put(stock.get(0), new StockDeal(stock.get(0), stock.get(1), stock.get(2), s));
                }
        }
    }

    /* 
        UI
        currency, saving account, 
        Stockname, shares
    */                
    public int buyStock (String name, int shares, Currency currency, SavingsAccount account, Date date) {
        Stock bankStock = bank.getStocks().get(name);

        if (shares > bankStock.getShares()) {
            //System.out.println("Cannot buy more than bank's inventory!");
            return -1;
        }

        double amount = 0;
        switch(currency) {
            case USD: 
                amount = CommonMathMethod.twoDecimal(shares * bankStock.getUSDPrice());
            case CNY:
                amount = CommonMathMethod.twoDecimal(shares * bankStock.getRMBPrice());
            case EUR: 
                amount = CommonMathMethod.twoDecimal(shares * bankStock.getEurPrice());
        }
        
        if (account.getDeposit(currency) < amount) {
            //System.out.println("Saving account money insufficient!");
            return 1;
        }
        else {
            // update owner stocks
            if (stocks.containsKey(name)) {
                stocks.get(name).buyShares(shares);
                stockDeals.get(name).setShares(stockDeals.get(name).getShares() + shares);

                // Simply update the shares that a client has
                String[] updateArgs = {"STOCK_SHARE"};
                String[] updateValues = {"STOCK_SHARE + " + shares};
                Database.updateData(stockDealTableName, "DEAL_ID",
                        stockDeals.get(name).getDeal_ID(), updateArgs, updateValues);
            } else {
                stocks.put(name, new Stock(name, bankStock.price, shares));
                StockDeal sd = new StockDeal(this.number, name, shares);
                stockDeals.put(name, sd);
            }

            // reduce money on saving account
            account.withdraw(amount, currency, date);
            // update bank stocks
            bankStock.sellShares(shares);
            String[] updateBankArgs = {"SHARE"};
            String[] updateBankValues = {"SHARE - " + shares};
            Database.updateData(stockTableName, "STOCK_NAME", name, updateBankArgs , updateBankValues);
            
        }
        return 0;
    }

    public int sellStock (String name, int shares, Currency currency, SavingsAccount account, Date date) {
        if (shares > stocks.get(name).shares) {
            //System.out.println("Cannot sell more than current biggest shares");
            return -1;
        }
        
        // add money on saving account
        double amount = CommonMathMethod.twoDecimal(shares *bank.getStocks().get(name).price);
        account.save(amount, currency, date);
        // update owner stocks
        stocks.get(name).sellShares(shares);
        String[] updateSecurityAccountArgs = {"ACCOUNT_NUMBER", "STOCK_NAME"};
        String[] updateSecurityAccountValues = {this.number, name};
        Database.updateData(stockDealTableName, "STOCK_SHARE",
                "STOCK_SHARE - " + shares, updateSecurityAccountArgs, updateSecurityAccountValues);
        // update bank stocks
        bank.getStocks().get(name).buyShares(shares);
        String[] updateStockArgs = {"STOCK_NAME"};
        String[] updateStockValues = {name};
        Database.updateData(stockTableName, "SHARE",
                "SHARE + " + shares, updateStockArgs, updateStockValues);
        return 0;
    }
    
    public String toString() {
    		return type.toString();
    }
    
    public String[][] stockTable(){
		String[][] table = new String[stocks.size()][4];
		int i = 0;
		for (String name: stocks.keySet()) {
			table[i][0] = name;
			table[i][1] = String.valueOf(stocks.get(name).getUSDPrice());
			table[i][2] = String.valueOf(CommonMathMethod.bigDecimalMultiply(stocks.get(name).getChange(), 100.0)) + "%";
			table[i][3] = String.valueOf(stocks.get(name).getShares());
			i++;
		}
		return table;
    }
    
    public boolean hasStock(String stockName) {
    		if (stocks.get(stockName)!=null)
    			return true;
    		return false;
    }
}
