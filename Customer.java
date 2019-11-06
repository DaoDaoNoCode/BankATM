import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents a customer in the bank.
 */

public class Customer {

    private Bank bank;

    private String username;

    private String password;

    private ArrayList<SavingsAccount> savingsAccounts;

    private ArrayList<CheckingAccount> checkingAccounts;

    private SecurityAccount securityAccount;

    private ArrayList<Account> closedAccounts;

    private final String savingsAccountTableName = "SAVINGS_ACCOUNT";

    private final String checkingAccountTableName = "CHECKING_ACCOUNT";

    private final String securityAccountTableName = "SECURITY_ACCOUNT";

    private final String[] checkingAccountCreateArgs = {"ACCOUNT_NUMBER char(12) not null", "OWNER varchar(20) not null", "PASSWORD varchar(20) not null", "USD_BALANCE varchar(20) not null", "EUR_BALANCE varchar(20) not null", "CNY_BALANCE varchar(20) not null", "DATE varchar(12) not null", "STATUS varchar(10) not null"};

    private final String[] savingsAccountCreateArgs = {"ACCOUNT_NUMBER char(12) not null", "OWNER varchar(20) not null", "PASSWORD varchar(20) not null", "USD_BALANCE varchar(20) not null", "EUR_BALANCE varchar(20) not null", "CNY_BALANCE varchar(20) not null", "USD_LOAN varchar(20) not null", "EUR_LOAN varchar(20) not null", "CNY_LOAN varchar(20) not null", "USD_INTEREST varchar(20) not null", "EUR_INTEREST varchar(20) not null", "CNY_INTEREST varchar(20) not null", "DATE varchar(12) not null", "STATUS varchar(10) not null"};

    private final String[] securityAccountCreateArgs = {"ACCOUNT_NUMBER char(12) not null", "OWNER varchar(20) not null", "PASSWORD varchar(20) not null", "STOCK_NAME varchar(20) not null", "STOCK_SHARE varchar(20) not null", "DATE varchar(12) not null", "STATUS varchar(10) not null"};

    private final String[] checkingAccountArgs = {"ACCOUNT_NUMBER", "OWNER", "PASSWORD", "USD_BALANCE", "EUR_BALANCE", "CNY_BALANCE", "DATE", "STATUS"};

    private final String[] savingsAccountArgs = {"ACCOUNT_NUMBER", "OWNER", "PASSWORD", "USD_BALANCE", "EUR_BALANCE", "CNY_BALANCE", "USD_LOAN", "EUR_LOAN", "CNY_LOAN", "USD_INTEREST", "EUR_INTEREST", "CNY_INTEREST", "DATE", "STATUS"};

    private final String[] securityAccountArgs = {"ACCOUNT_NUMBER", "OWNER", "PASSWORD", "STOCK_NAME", "STOCK_SHARE", "DATE", "STATUS"};

    private final String accountPrimaryKey = "ACCOUNT_NUMBER";

    public Customer(Bank bank, String username, String password) {
        this.bank = bank;
        this.username = username;
        this.password = password;
        readAccountsFromDatabase();
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
    
    public SecurityAccount getSecurityAccounts() {
        return this.securityAccount;
    }

    public ArrayList<Account> getClosedAccounts() {
        return this.closedAccounts;
    }

    private void readAccountsFromDatabase() {
        savingsAccounts = new ArrayList<>();
        checkingAccounts = new ArrayList<>();
        closedAccounts = new ArrayList<>();
        securityAccount = null;
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        if (!Database.hasTable(savingsAccountTableName)) {
            Database.createTable(savingsAccountTableName, savingsAccountCreateArgs);
            Database.setPrimaryKey(savingsAccountTableName, accountPrimaryKey);
        } else {
            String[] queryIndex = {"OWNER"};
            String[] queryValue = {username};
            List<List<String>> accounts = Database.queryData(savingsAccountTableName, queryIndex, queryValue, savingsAccountArgs);
            for (List<String> account : accounts) {
                Double[] balance = {Double.valueOf(account.get(3)), Double.valueOf(account.get(4)), Double.valueOf(account.get(5))};
                Double[] loan = {Double.valueOf(account.get(6)), Double.valueOf(account.get(7)), Double.valueOf(account.get(8))};
                Double[] interest = {Double.valueOf(account.get(9)), Double.valueOf(account.get(10)), Double.valueOf(account.get(11))};
                Date date = new Date();
                try {
                    date = formatter.parse(account.get(12));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println(balance.length);
                SavingsAccount savingsAccount = new SavingsAccount(bank, account.get(0), account.get(1), account.get(2), balance, loan, interest, date);
                if (account.get(13).equals("CLOSED")) {
                    closedAccounts.add(savingsAccount);
                } else {
                    savingsAccounts.add(savingsAccount);
                }
            }
        }
    }

    public Account openAccount(AccountType type, String password, Date date) {
        Account account;
        if (type == AccountType.CHECKING) {
            account = new CheckingAccount(bank, username, password, date);
            checkingAccounts.add((CheckingAccount) account);
            account.openAccount();
        } else if (type == AccountType.SAVINGS) {
            account = new SavingsAccount(bank, username, password, date);
            savingsAccounts.add((SavingsAccount) account);
            String[] values = {account.getNumber(), username, password, String.valueOf(0.0), String.valueOf(0.0), String.valueOf(0.0), String.valueOf(0.0), String.valueOf(0.0), String.valueOf(0.0), String.valueOf(0.0), String.valueOf(0.0), String.valueOf(0.0), getDateString(date), "ACTIVATE"};
            Database.insertData(savingsAccountTableName, values);
            account.openAccount();
        } else {
            account = new SecurityAccount(bank, username, password, date);
            securityAccount = (SecurityAccount) account;
            account.openAccount();
        }
        return account;
    }

    public void closeAccount(Account account, Date date) {
        account.closeAccount(date);
        closedAccounts.add(account);
        if (account.getType() == AccountType.CHECKING) {
            checkingAccounts.remove(account);
        } else if (account.getType() == AccountType.SAVINGS) {
            savingsAccounts.remove(account);
        } else {
            this.securityAccount = null;
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

    private String getDateString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        return formatter.format(date);
    }

    public String toString() {
        return "Customer " + username;
    }
}
