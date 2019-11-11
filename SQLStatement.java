public class SQLStatement {

    //table names
    public static final String bankerTransactionTableName = "BANK_TRANSACTION";

    public static final String customerTableName = "CUSTOMER";

    public static  final String bankerTableName = "BANKER";

    public static final String stockTableName = "STOCK";

    public static final String savingsAccountTableName = "SAVINGS_ACCOUNT";

    public static final String checkingAccountTableName = "CHECKING_ACCOUNT";

    public static final String securityAccountTableName = "SECURITY_ACCOUNT";

    public static final String stockDealTableName = "STOCK_DEAL";


    // creation arguments
    public static final String[] bankerTransactionCreateArgs = {"ACCOUNT_NUMBER char(12) not null",
            "CUSTOMER varchar(20) not null", "TYPE varchar(20) not null",
            "MONEY varchar(20) not null", "CURRENCY varchar(3) not null", "DATE varchar(20) not null, ID char(12) not null"};

    public static final String[] bankerCreateArgs = {"USERNAME varchar(20) not null",
            "PASSWORD varchar(20) not null", "DATE varchar(20) not null"};

    public static final String[] customerCreateArgs = {"USERNAME varchar(20) not null", "PASSWORD varchar(20) not null"};

    public static final String[] stockCreateArgs = {"STOCK_NAME varchar(20) not null", "PRICE varchar(20) not null",
            "SHARE varchar(20) not null", "CHANGE_PERCENTAGE varchar(20) not null"};

    public static final String[] checkingAccountCreateArgs = {"ACCOUNT_NUMBER char(12) not null", "OWNER varchar(20) not null",
            "PASSWORD varchar(20) not null", "USD_BALANCE varchar(20) not null", "CNY_BALANCE varchar(20) not null",
            "EUR_BALANCE varchar(20) not null", "DATE varchar(12) not null", "STATUS varchar(10) not null"};

    public static final String[] savingsAccountCreateArgs = {"ACCOUNT_NUMBER char(12) not null",
            "OWNER varchar(20) not null", "PASSWORD varchar(20) not null", "USD_BALANCE varchar(20) not null",
            "CNY_BALANCE varchar(20) not null", "EUR_BALANCE varchar(20) not null", "USD_LOAN varchar(20) not null",
            "CNY_LOAN varchar(20) not null", "EUR_LOAN varchar(20) not null", "USD_INTEREST varchar(20) not null",
            "CNY_INTEREST varchar(20) not null", "EUR_INTEREST varchar(20) not null",
            "DATE varchar(12) not null", "STATUS varchar(10) not null"};

    public static final String[] securityAccountCreateArgs = {"ACCOUNT_NUMBER char(12) not null", "OWNER varchar(20) not null",
            "PASSWORD varchar(20) not null", "STOCK_NAME varchar(20) not null", "STOCK_SHARE varchar(20) not null",
            "DATE varchar(12) not null", "STATUS varchar(10) not null"};

    public static final String[] stockDealCreateArgs = {"DEAL_ID char(12) not null", "ACCOUNT_NUMBER char(12) not null",
            "STOCK_NAME varchar(20) not null", "STOCK_SHARE varchar(20) not null"};


    // Primary keys
    public static final String bankerTransactionPrimaryKey = "ID";

    public static final String customerPrimaryKey = "USERNAME";

    public static final String stockPrimaryKey = "STOCK_NAME";

    public static  final String bankerPrimaryKey = "USERNAME";

    public static final String accountPrimaryKey = "ACCOUNT_NUMBER";

    public static final String stockDealPrimaryKey = "DEAL_ID";


    // table arguments
    public static final String[] bankerTransactionArgs = {"ACCOUNT_NUMBER", "CUSTOMER", "TYPE", "MONEY", "CURRENCY", "DATE", "ID"};

    public static final String[] bankerTransactionArgsForAccount = {"TYPE", "MONEY", "CURRENCY", "DATE", "ID"}; //”√”⁄account¿‡

    public static  final String[] bankerArgs = {"USERNAME", "PASSWORD", "DATE"};

    public static final String[] customerArgs = {"USERNAME", "PASSWORD"};

    public static final String[] stockArgs = {"STOCK_NAME", "PRICE", "SHARE", "CHANGE_PERCENTAGE"};

    public static final String[] checkingAccountArgs = {"ACCOUNT_NUMBER", "OWNER", "PASSWORD",
            "USD_BALANCE", "CNY_BALANCE", "EUR_BALANCE",  "DATE", "STATUS"};

    public static final String[] savingsAccountArgs = {"ACCOUNT_NUMBER", "OWNER", "PASSWORD",
            "USD_BALANCE", "CNY_BALANCE", "EUR_BALANCE", "USD_LOAN", "CNY_LOAN", "EUR_LOAN", "USD_INTEREST",
            "CNY_INTEREST", "EUR_INTEREST", "DATE", "STATUS"};

    public static final String[] securityAccountArgs = {"ACCOUNT_NUMBER", "OWNER", "PASSWORD",
            "STOCK_NAME", "STOCK_SHARE", "DATE", "STATUS"};

    public static final String[] stockDealArgs = {"DEAL_ID", "ACCOUNT_NUMBER", "STOCK_NAME", "STOCK_SHARE"};
}
