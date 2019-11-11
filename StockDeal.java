import javax.xml.crypto.Data;
import java.util.Random;

public class StockDeal {
    private static final int ACCOUNT_NUMBER_LENGTH = 12;

    private final String stockDealTableName = "STOCK_DEAL";

    private String Deal_ID;
    private String account;
    private String stock;
    private int shares;

    public StockDeal() {
        this.generateAccountNumber();
    }

    public StockDeal(String account, String stock, int shares) {
        this();
        this.account = account;
        this.stock = stock;
        this.shares = shares;
        if (!Database.hasDataRow(stockDealTableName, "DEAL_ID", this.Deal_ID)) {
            String s = Integer.toString(this.shares);
            String[] insertValue = {this.Deal_ID, this.account, this.stock, s};
            Database.insertData(stockDealTableName, insertValue);
        }
    }

    public StockDeal(String id, String account, String stock, int shares) {
        this.Deal_ID = id;
        this.account = account;
        this.stock = stock;
        this.shares = shares;
        if (!Database.hasDataRow(stockDealTableName, "DEAL_ID", this.Deal_ID)) {
            String s = Integer.toString(this.shares);
            String[] insertValue = {this.Deal_ID, this.account, this.stock, s};
            Database.insertData(stockDealTableName, insertValue);
        }
    }

    public String getDeal_ID() {
        return Deal_ID;
    }

    public String getStock() {
        return stock;
    }

    public void setDeal_ID(String deal_ID) {
        Deal_ID = deal_ID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    // for customer
    public void buyShares (int i) {
        this.shares += i;
    }

    public void sellShares (int i) {
        this.shares -= i;
    }

    /**
     * Generate a random ID number of 12 digits
     */
    private void generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            int number = random.nextInt(10);
            sb.append((char) (number + '0'));
        }
        this.Deal_ID = sb.toString();
    }
}
