import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents a customer in the bank.
 */

public class Customer {

    private Bank bank;

    private String username;

    private String password;

    private ArrayList<SavingsAccount> savingsAccounts;

    private ArrayList<CheckingAccount> checkingAccounts;

    private ArrayList<Account> closedAccounts;

    public Customer(Bank bank, String username, String password) {
        this.bank = bank;
        this.username = username;
        this.password = password;
        this.savingsAccounts = new ArrayList<>();
        this.checkingAccounts = new ArrayList<>();
        this.closedAccounts = new ArrayList<>();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public ArrayList<SavingsAccount> getSavingsAccounts() {
        return this.savingsAccounts;
    }

    public ArrayList<CheckingAccount> getCheckingAccounts() {
        return this.checkingAccounts;
    }

    public ArrayList<Account> getClosedAccounts() {
        return this.closedAccounts;
    }

    public Account openAccount(AccountType type, String password, Date date) {
        Account account;
        if (type == AccountType.CHECKING) {
            account = new CheckingAccount(bank, this, password, date);
            checkingAccounts.add((CheckingAccount) account);
            account.openAccount();
        } else {
            account = new SavingsAccount(bank, this, password, date);
            savingsAccounts.add((SavingsAccount) account);
            account.openAccount();
        }
        return account;
    }

    public void closeAccount(Account account, Date date) {
        account.closeAccount(date);
        closedAccounts.add(account);
        if (account.getType() == AccountType.CHECKING) {
            checkingAccounts.remove(account);
        } else {
            savingsAccounts.remove(account);
        }
    }

    public ArrayList<SavingsAccount> findLoanAccounts() {
        ArrayList<SavingsAccount> list = new ArrayList<>();
        for (SavingsAccount account : savingsAccounts) {
            for (Currency currency : Currency.values()) {
                if (account.getLoan(currency) > 0) {
                    list.add(account);
                    break;
                }
            }
        }
        return list;
    }

    public String toString() {
        return "Customer " + username;
    }
}
