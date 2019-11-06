import java.math.BigDecimal;
import java.util.*;

/**
 * This class represents an account in the bank.
 */

public class Account {

    private static final int ACCOUNT_NUMBER_LENGTH = 12;

    private static final double ACCOUNT_OPEN_CLOSE_FEE = 5.0;

    protected Bank bank;

    protected String owner;

    protected HashMap<Currency, Double> deposit;

    protected String password;

    protected String number;

    protected Date createDate;

    protected ArrayList<Transaction> transactions;

    protected AccountType type;

    public Account() { }

    public Account(Bank bank, String owner, String password, Date date) {
        this.bank = bank;
        this.owner = owner;
        this.password = password;
        this.transactions = new ArrayList<>();
        this.deposit = new HashMap<>();
        for (Currency currency : Currency.values()) {
            this.deposit.put(currency, 0.0);
        }
        this.generateAccountNumber();
        this.createDate = date;
    }

    /**
     * Calculate the days between two dates.
     * @param startTime start date
     * @param endTime end date
     * @return days between them
     */
    public static int getDistanceTime(Date startTime, Date endTime) {
        int days = 0;
        long time1 = startTime.getTime();
        long time2 = endTime.getTime();

        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        days = (int) (diff / (24 * 60 * 60 * 1000));
        return days;
    }

    /**
     * The result should be quoted in two decimal places.
     * @param decimal the input decimal
     * @return decimal after round half up
     */
    static public double twoDecimal(double decimal) {
        BigDecimal newDecimal = BigDecimal.valueOf(decimal);
        return newDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public double getDeposit(Currency currency) {
        return deposit.get(currency);
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public String getNumber() {
        return this.number;
    }

    public AccountType getType() {
        return this.type;
    }

    public String getPassword() {
        return this.getPassword();
    }

    public void openAccount() {
        transactions.add(new Transaction(-ACCOUNT_OPEN_CLOSE_FEE, Currency.USD, TransactionType.OPEN_ACCOUNT_FEE, owner, this, createDate));
        bank.addTransaction(new Transaction(ACCOUNT_OPEN_CLOSE_FEE, Currency.USD, TransactionType.OPEN_ACCOUNT_FEE, owner, this, createDate));
    }

    public void save(double money, Currency currency, Date date) {
        double balance = deposit.get(currency);
        deposit.put(currency, twoDecimal(balance + money));
        transactions.add(new Transaction(money, currency, TransactionType.SAVE, owner, this, date));
    }

    /**
     * Withdraw money, if money less than 1000, then fee rate is 0.05%, else fee rate is 0.1%.
     * @param money money owner wants to withdraw
     * @param currency type of currency
     * @param date withdrawal time
     * @return -1 means withdrawal is invalid, 0 means no enough deposit to withdraw, 1 means withdraw successfully
     */
    public int withdraw(double money, Currency currency, Date date) {
        if (money <= 0) {
            return -1;
        }
        double withdrawFeeRate = money >= 1000 ? 0.001 : 0.0005;
        BigDecimal withdrawFeeRateBig = BigDecimal.valueOf(withdrawFeeRate);
        BigDecimal moneyBig = BigDecimal.valueOf(money);
        double withdrawFee = withdrawFeeRateBig.multiply(moneyBig).doubleValue();
        double balance = deposit.get(currency);
        if (money + withdrawFee > balance) {
            return 0;
        } else {
            deposit.put(currency, twoDecimal(balance - money - withdrawFee));
            transactions.add(new Transaction(-money, currency, TransactionType.WITHDRAW, owner, this, date));
            transactions.add(new Transaction(-withdrawFee, currency, TransactionType.WITHDRAW_FEE, owner, this, date));
            bank.addTransaction(new Transaction(withdrawFee, currency, TransactionType.WITHDRAW_FEE, owner, this, date));
            return 1;
        }
    }

    /**
     * Give interest at a daily rate of 0.02% to those deposit no less than 1000.
     * @param day the day added by banker
     * @param date the day to calculate the interest
     */
    public void addSaveInterest(int day, Date date) {
        Calendar calendar = Calendar.getInstance();
        for (Currency currency : Currency.values()) {
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -day);
            double currentBalance = deposit.get(currency);
            if (currentBalance >= 1000) {
                int loop = day;
                while (loop > 0) {
                    BigDecimal saveInterestRateBig = BigDecimal.valueOf(0.0002);
                    BigDecimal currentBalanceBig = BigDecimal.valueOf(currentBalance);
                    double saveInterest = saveInterestRateBig.multiply(currentBalanceBig).doubleValue();
                    deposit.put(currency, twoDecimal(currentBalance + saveInterest));
                    currentBalance += saveInterest;
                    loop--;
                    calendar.add(Calendar.DATE, 1);
                    transactions.add(new Transaction(saveInterest, currency, TransactionType.SAVE_INTEREST, owner, this, calendar.getTime()));
                    bank.addTransaction(new Transaction(-saveInterest, currency, TransactionType.SAVE_INTEREST, owner, this, calendar.getTime()));
                }
            }
        }
    }

    /**
     * If someone close an account without withdrawing all of the money in it, the money will be given to banker.
     * @param date
     */
    public void closeAccount(Date date) {
        transactions.add(new Transaction(-ACCOUNT_OPEN_CLOSE_FEE, Currency.USD, TransactionType.CLOSE_ACCOUNT_FEE, owner, this, date));
        bank.addTransaction(new Transaction(ACCOUNT_OPEN_CLOSE_FEE, Currency.USD, TransactionType.CLOSE_ACCOUNT_FEE, owner, this, date));
        for (Currency currency : Currency.values()) {
            double balance = deposit.get(currency);
            if (balance > 0) {
                transactions.add(new Transaction(-balance, currency, TransactionType.BALANCE_REMAINED_WHEN_CLOSE, owner, this, date));
                bank.addTransaction(new Transaction(balance, currency, TransactionType.BALANCE_REMAINED_WHEN_CLOSE, owner, this, date));
            }
        }
    }

    public boolean isPasswordRight(String password) {
        return this.password.equals(password);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Generate a random account number of 12 digits
     */
    private void generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            int number = random.nextInt(10);
            sb.append((char) (number + '0'));
        }
        number = sb.toString();
    }
}
