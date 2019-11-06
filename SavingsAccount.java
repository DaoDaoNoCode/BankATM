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
        super(bank, owner, password, date);
        this.number = accountNumber;
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
            loan.put(currency, twoDecimal(money + balance));
            BigDecimal interestRateBig = BigDecimal.valueOf(0.001);
            BigDecimal moneyBig = BigDecimal.valueOf(money);
            double currentInterest = interestRateBig.multiply(moneyBig).doubleValue();
            loanInterest.put(currency, twoDecimal(currentInterest));
            deposit.put(currency, twoDecimal(money));
            transactions.add(new Transaction(money, currency, TransactionType.LOAN, owner, this, date));
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
                loanInterest.put(currency, 0.0);
                deposit.put(currency, twoDecimal(currentBalance - currentLoan - currentInterest));
                transactions.add(new Transaction(-currentLoan, currency, TransactionType.LOAN_REPAY, owner, this, date));
                transactions.add(new Transaction(-currentInterest, currency, TransactionType.LOAN_INTEREST, owner, this, date));
                bank.addTransaction(new Transaction(currentInterest, currency, TransactionType.LOAN_INTEREST, owner, this, date));
                return 1;
            }
        }
    }

    @Override
    public String toString() {
        return type + " - " + number;
    }
}
