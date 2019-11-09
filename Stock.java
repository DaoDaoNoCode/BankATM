public class Stock {
    private final String stockTableName = "STOCK";

    private final String stockPrimaryKey = "STOCK_NAME";

    protected String name;

    protected double price; // USD

    protected int shares;

    public Stock (String name, double price, int shares) {
        this.name = name;
        this.price = price;
        this.shares = shares;
        if (!Database.hasDataRow(stockTableName, stockPrimaryKey, this.name)) {
            String[] insertedData = new String[]{name, Double.toString(price), Integer.toString(shares)};
            Database.insertData(stockTableName, insertedData);
        }
    }

    public double getUSDPrice() {
        return this.price;
    }

    public double getEurPrice() {
        return this.price * 7 / 8;
    }

    public double getRMBPrice() {
        return this.price * 7;
    }

    public String getName() {
        return this.name;
    }

    // for Bank
    public void setPrice(double price) {
        this.price = price;
        String[] queryIndex = {stockPrimaryKey};
        String[] queryValue = {name};
        Database.updateData(stockTableName, "PRICE", Double.toString(price), queryIndex, queryValue);
    }

    // for customer
    public void buyShares (int i) {
        this.shares += i;
    }
    public void sellShares (int i) {
        this.shares -= i;
    }
}