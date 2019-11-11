import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SecurityAccount extends Account {
    private final String stockDealPrimaryKey = "DEAL_ID";

    private final String[] stockDealCreateArgs = {"DEAL_ID char(12) not null", "ACCOUNT_NUMBER char(12) not null",
            "STOCK_NAME varchar(20) not null", "STOCK_SHARE varchar(20) not null"};

    private final String[] stockDealArgs = {"DEAL_ID", "ACCOUNT_NUMBER", "STOCK_NAME", "STOCK_SHARE"};

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
        readStocksBoughtFromDatabase();
    }

    public HashMap<String, StockDeal> getStockDeals() {
        return stockDeals;
    }

    public HashMap<String, Stock> getStocks() {
        HashMap<String, Stock> bankStocks = bank.getStocks();
        for (StockDeal stockDeal : stockDeals.values()) {
            String stockName = stockDeal.getStock();
            Stock stock = new Stock(stockName, bankStocks.get(stockName).getUSDPrice(), stockDeal.getShares(), bankStocks.get(stockName).getChange());
            stocks.put(stockName, stock);
        }
        return stocks;
    }

    private void readStocksBoughtFromDatabase() {
        if (!Database.hasTable(stockDealTableName)) {
            Database.createTable(stockDealTableName, stockDealCreateArgs);
            Database.setPrimaryKey(stockDealTableName, stockDealPrimaryKey);
        } else {
            String[] queryIndex = {"ACCOUNT_NUMBER"};
            String[] queryValue = {this.number};
            List<List<String>> stockDeals = Database.queryData(stockDealTableName, queryIndex, queryValue, stockDealArgs);
            for (List<String> stockDeal : stockDeals) {
                int shares = Integer.parseInt(stockDeal.get(3));
                String stockName = stockDeal.get(2);
                this.stockDeals.put(stockDeal.get(0), new StockDeal(stockDeal.get(0), stockDeal.get(1), stockName, shares));
            }
        }
    }

    /* 
        UI
        currency, saving account, 
        Stockname, shares
    */
    public int buyStock(String name, int shares, Currency currency, SavingsAccount account, Date date) {
        Stock bankStock = bank.getStocks().get(name);

        if (shares > bankStock.getShares()) {
            //System.out.println("Cannot buy more than bank's inventory!");
            return -1;
        }

        double amount = 0.0;
        double fee = 0.0;
        switch (currency) {
            case USD: {
                amount = CommonMathMethod.twoDecimal(CommonMathMethod.bigDecimalMultiply(shares, bankStock.getUSDPrice()));
                fee = 5.0;
                break;
            }
            case CNY: {
                amount = CommonMathMethod.twoDecimal(CommonMathMethod.bigDecimalMultiply(shares, bankStock.getRMBPrice()));
                fee = CommonMathMethod.twoDecimal(CommonMathMethod.bigDecimalMultiply(5.0, 7.0));
                break;
            }
            case EUR: {
                amount = CommonMathMethod.twoDecimal(CommonMathMethod.bigDecimalMultiply(shares, bankStock.getEurPrice()));
                fee = CommonMathMethod.twoDecimal(CommonMathMethod.bigDecimalMultiply(CommonMathMethod.bigDecimalMultiply(5.0, 7.0), 0.125));
                break;
            }
        }

        if (account.getDeposit(currency) < amount + fee) {
            //System.out.println("Saving account money insufficient!");
            return 1;
        } else {
            // update owner stocks
            HashMap<String, Stock> stocks = getStocks();
            if (stocks.containsKey(name)) {
                for (StockDeal stockDeal : stockDeals.values()) {
                    if (stockDeal.getStock().equals(name)) {
                        stockDeal.buyShares(shares);
                        int newShare = stockDeal.getShares();

                        // Simply update the shares that a client has
                        String[] updateArgs = {"STOCK_SHARE"};
                        String[] updateValues = {String.valueOf(newShare)};
                        Database.updateData(stockDealTableName, "DEAL_ID", stockDeal.getDeal_ID(), updateArgs, updateValues);
                    }
                }
            } else {
                StockDeal stockDeal = new StockDeal(number, name, shares);
                stockDeals.put(stockDeal.getDeal_ID(), stockDeal);
            }

            // reduce money on saving account
            account.buyStock(amount, fee, currency, date);
            // update bank stocks
            bankStock.setShares(bankStock.getShares() - shares);
            String[] updateBankArgs = {"SHARE"};
            String[] updateBankValues = {String.valueOf(bankStock.getShares())};
            Database.updateData(stockTableName, "STOCK_NAME", name, updateBankArgs, updateBankValues);
        }
        return 0;
    }

    public int sellStock(String name, int shares, Currency currency, SavingsAccount account, Date date) {
        Stock bankStock = bank.getStocks().get(name);
        for (StockDeal stockDeal : stockDeals.values()) {
            if (stockDeal.getStock().equals(name)) {
                if (shares > stockDeal.getShares()) {
                    //System.out.println("Cannot sell more than current biggest shares");
                    return -1;
                }

                double amount = 0.0;
                double fee = 5.0;
                switch (currency) {
                    case USD: {
                        amount = CommonMathMethod.twoDecimal(CommonMathMethod.bigDecimalMultiply(shares, bankStock.getUSDPrice()));
                        fee = 5.0;
                        break;
                    }
                    case CNY: {
                        amount = CommonMathMethod.twoDecimal(CommonMathMethod.bigDecimalMultiply(shares, bankStock.getRMBPrice()));
                        fee = CommonMathMethod.twoDecimal(CommonMathMethod.bigDecimalMultiply(5.0, 7.0));
                        break;
                    }
                    case EUR: {
                        amount = CommonMathMethod.twoDecimal(CommonMathMethod.bigDecimalMultiply(shares, bankStock.getEurPrice()));
                        fee = CommonMathMethod.twoDecimal(CommonMathMethod.bigDecimalMultiply(CommonMathMethod.bigDecimalMultiply(5.0, 7.0), 0.125));
                        break;
                    }
                }

                // update owner stocks
                stockDeal.sellShares(shares);
                int newShare = stockDeal.getShares();
                stocks.get(name).setShares(newShare);
                if (newShare == 0) {
                    stockDeals.remove(stockDeal.getDeal_ID());
                    Database.deleteData(stockDealTableName, stockDealPrimaryKey, stockDeal.getDeal_ID());
                } else {
                    // Simply update the shares that a client has
                    String[] updateArgs = {"STOCK_SHARE"};
                    String[] updateValues = {String.valueOf(newShare)};
                    Database.updateData(stockDealTableName, "DEAL_ID", stockDeal.getDeal_ID(), updateArgs, updateValues);
                }

                // add money on saving account
                account.sellStock(amount, fee, currency, date);
                // update bank stocks
                bankStock.setShares(bankStock.getShares() + shares);
                String[] updateBankArgs = {"SHARE"};
                String[] updateBankValues = {String.valueOf(bankStock.getShares())};
                Database.updateData(stockTableName, "STOCK_NAME", name, updateBankArgs, updateBankValues);
            }
        }
        return 0;
    }

    public String toString() {
        return type.toString();
    }

    public String[][] stockTable() {
        HashMap<String, Stock> stocks = getStocks();
        String[][] table = new String[stocks.size()][4];
        int i = 0;
        for (StockDeal stockDeal : stockDeals.values()) {
            String stockName = stockDeal.getStock();
            table[i][0] = stockName;
            table[i][1] = String.valueOf(stocks.get(stockName).getUSDPrice());
            table[i][2] = String.valueOf(CommonMathMethod.bigDecimalMultiply(stocks.get(stockName).getChange(), 100.0)) + "%";
            table[i][3] = String.valueOf(stockDeal.getShares());
            i++;
        }
        return table;
    }

    public boolean hasStock(String stockName) {
        if (stockDeals.get(stockName) != null)
            return true;
        return false;
    }
}
