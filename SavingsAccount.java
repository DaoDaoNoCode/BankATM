import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

/**
 * This class is the savings account, which extends Account class, and has the function to transfer.
 */

public class SavingsAccount extends Account {

    private HashMap<Currency, Double> loan;

    private HashMap<Currency, Double> loanInterest;

    public SavingsAccount(Bank bank, String owner, String password, Date date) {
        super(bank, owner, password, date);
        type = AccountType.SAVINGS;
        loan = new HashMap<>();
        for (Currency currency : Currency.values()) {
            loan.put(currency, 0.0);
        }
        loanInterest = new HashMap<>();
        for (Currency currency : Currency.values()) {
            loanInterest.put(currency, 0.0);
        }
    }

    public SavingsAccount(Bank bank, String accountNumber, String owner, String password, Double[] balance, Double[] loan, Double[] interest, Date date) {
        super(bank, owner, password, accountNumber, date);
        type = AccountType.SAVINGS;
        for (int i = 0; i < Currency.values().length; i++) {
            this.deposit.put(Currency.values()[i], balance[i]);
        }
        this.loan = new HashMap<>();
        for (int i = 0; i < Currency.values().length; i++) {
            this.loan.put(Currency.values()[i], loan[i]);
        }
        this.loanInterest = new HashMap<>();
        for (int i = 0; i < Currency.values().length; i++) {
            this.loanInterest.put(Currency.values()[i], interest[i]);
        }
    }

    public double getLoan(Currency currency) {
        return loan.get(currency);
    }

    public double getLoanInterest(Currency currency) {
        return loanInterest.get(currency);
    }

    /**
     * Request a loan.
     * @param money money want to request
     * @param currency currency of the loan
     * @param date loan date
     * @return -1 if the loan has not been repaid, 0 if the money is invalid, 1 if request a loan successfully
     */
    public int requestLoan(double money, Currency currency, Date date) {
        double currentLoan = loan.get(currency);
        if (currentLoan > 0) return -1;
        else if (money > 10000.0 || money <= 0.0) return 0;
        else {
            double balance = deposit.get(currency);
            loan.put(currency, twoDecimal(money));
            updateLoanInDatabase(currency);

            BigDecimal interestRateBig = BigDecimal.valueOf(0.001);
            BigDecimal moneyBig = BigDecimal.valueOf(money);
            double currentInterest = interestRateBig.multiply(moneyBig).doubleValue();
            loanInterest.put(currency, twoDecimal(currentInterest));
            updateLoanInDatabase(currency);

            deposit.put(currency, twoDecimal(money + balance));
            updateDepositInDatabase(currency);
            transactions.add(new Transaction(money, currency, TransactionType.LOAN, owner, super.getNumber(), date));
            return 1;
        }
    }

    /**
     * Calculate the interest of loan at a daily rate 0.1%.
     * @param date the current date
     */
    public void calculateLoanInterest(Date date) {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionType() == TransactionType.LOAN) {
                Currency currency = transaction.getCurrency();
                double currentLoan = loan.get(currency);
                if (currentLoan != 0) {
                    int days = getDistanceTime(transaction.getDate(), date);
                    BigDecimal daysBig = BigDecimal.valueOf(days);
                    BigDecimal currentLoanBig = BigDecimal.valueOf(currentLoan);
                    BigDecimal currentLoanBigRate = BigDecimal.valueOf(0.001);
                    loanInterest.put(currency, twoDecimal(currentLoanBig.multiply(daysBig.multiply(currentLoanBigRate)).doubleValue()));
                    updateLoanInDatabase(currency);
                }
            }
        }
    }

    /**
     * Loan can only be repaid by the same account.
     * @param currency repay currency
     * @param date repay date
     * @return -1 if there is no loan, 0 if the money is not enough, 1 if repay successfully
     */
    public int repayLoan(Currency currency, Date date) {
        double currentLoan = loan.get(currency);
        double currentInterest = loanInterest.get(currency);
        if (currentLoan <= 0) {
            return -1;
        } else {
            if (deposit.get(currency) < currentLoan + currentInterest) {
                return 0;
            } else {
                double currentBalance = deposit.get(currency);
                loan.put(currency, 0.0);
                updateLoanInDatabase(currency);
                loanInterest.put(currency, 0.0);
                updateLoanInDatabase(currency);
                deposit.put(currency, twoDecimal(currentBalance - currentLoan - currentInterest));
                updateLoanInDatabase(currency);
                transactions.add(new Transaction(-currentLoan, currency, TransactionType.LOAN_REPAY, owner, super.getNumber(), date));
                transactions.add(new Transaction(-currentInterest, currency, TransactionType.LOAN_INTEREST, owner, super.getNumber(), date));
                bank.addTransaction(new Transaction(currentInterest, currency, TransactionType.LOAN_INTEREST, owner, super.getNumber(), date));
                return 1;
            }
        }
    }
    protected void updateLoanInDatabase(Currency currency) {
        String tableName = "SAVINGS_ACCOUNT";
        String[] args;
        if (currency == Currency.USD) {
            args = new String[]{"USD_LOAN", "USD_INTEREST"};
        } else if (currency == Currency.EUR) {
            args = new String[]{"EUR_LOAN", "EUR_INTEREST"};
        } else {
            args = new String[]{"CNY_LOAN", "CNY_INTEREST"};
        }
        String[] updateValues = {loan.get(currency).toString(), loanInterest.get(currency).toString()};
        Database.updateData(tableName, "ACCOUNT_NUMBER", number, args, updateValues);
    }
    @Override
    public String toString() {
        return type + " - " + number;
    }
}
