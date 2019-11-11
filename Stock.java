public class Stock {
    private final String stockTableName = "STOCK";

    private final String stockPrimaryKey = "STOCK_NAME";

    protected String name;

    protected double price; // USD

    protected int shares;

    protected double change;

    public Stock (String name, double price, int shares) {
        this.name = name;
        this.price = price;
        this.shares = shares;
        this.change = 0.0;
        if (!Database.hasDataRow(stockTableName, stockPrimaryKey, this.name)) {
            String[] insertedData = new String[]{name, Double.toString(price), Integer.toString(shares)};
            Database.insertData(stockTableName, insertedData);
        }
    }
    public Stock (String name, double price, int shares, double change) {
        this.name = name;
        this.price = price;
        this.shares = shares;
        this.change = change;
        if (!Database.hasDataRow(stockTableName, stockPrimaryKey, this.name)) {
            String[] insertedData = new String[]{name, Double.toString(price), Integer.toString(shares), Double.toString(change)};
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
    
    public int getShares() {
    		return this.shares;
    }
    
    public double getChange() {
    		return this.change;
    }

    public boolean someoneHasShare() {
        return true;
    }

    // for Bank
    public void setPrice(double price) {
        this.price = price;
        String[] args = {"PRICE"};
        String[] updateValue = {Double.toString(price)};
        Database.updateData(stockTableName, stockPrimaryKey, name, args, updateValue);
    }

    // for Bank
    public void setShares(int shares) {
        this.shares = shares;
        String[] args = {"SHARE"};
        String[] updateValue = {Integer.toString(shares)};
        Database.updateData(stockTableName, stockPrimaryKey, name, args, updateValue);
    }

    // for Bank
    public void setChange(double change) {
        this.change = change;
        String[] args = {"CHANGE_PERCENTAGE"};
        String[] updateValues = {Double.toString(change)};
        Database.updateData(stockTableName, "STOCK_NAME", name, args, updateValues);
    }

    // for customer
    public void buyShares (int i) {
        this.shares += i;
    }

    public void sellShares (int i) {
        this.shares -= i;
    }
}