public class Stock {
    protected String name;

    protected double price; // USD

    protected int shares;

    public Stock (String name, double price, int shares) {
        this.name = name;
        this.price = price;
        this.shares = shares;
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

    // for Bank
    public void setPrice(double price) {
        this.price = price;
    }

    // for customer
    public void buyShares (int i) {
        this.shares += i;
    }
    public void sellShares (int i) {
        this.shares -= i;
    }
}