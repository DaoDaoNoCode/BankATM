package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import bank.*;
import bank.Currency;

//user and manager interface
@SuppressWarnings("serial")
public class SelectPanel extends MainPanel {

    private JComboBox<Account> accounts;
    //transfer component
    private DefaultComboBoxModel<Account> model;
    private JComboBox<Customer> customers;
    private JComboBox<Account> extraAccounts;
    //record component
    private AtmTextField enter;
    private int dealshares;
    //stocks component

    public SelectPanel(String s, AtmFrame newFrame, Bank newBank, Date newDate) {
        super(s, newFrame, newBank, newDate);
        switch (s) {
            case "select": {
                showName();
                textAndBox("Select an Account");
                next = new NextButton();
                add(next);
                next.addActionListener(newListener);
                break;
            }
            case "buystocks": {
                textAndBox("Select an Account");
                enter = new AtmTextField("");
                enter.setBounds(260, 360, 250, 30);
                enter.setHorizontalAlignment(JTextField.LEFT);
                add(enter);
                JLabel label = new JLabel("Enter Shares");
                label.setFont(new Font("calibri", Font.BOLD, 20));
                label.setBounds(260, 260, 300, 100);
                showStocks();
                add(label);
                next = new NextButton("Buy");
                add(next);
                next.addActionListener(newListener);
                break;
            }
            case "sellstocks": {
                textAndBox("Select an Account");
                enter = new AtmTextField("");
                enter.setBounds(260, 360, 250, 30);
                enter.setHorizontalAlignment(JTextField.LEFT);
                add(enter);
                JLabel label = new JLabel("Enter Shares");
                label.setFont(new Font("calibri", Font.BOLD, 20));
                label.setBounds(260, 260, 300, 100);
                showStocks();
                add(label);
                next = new NextButton("Sell");
                add(next);
                next.addActionListener(newListener);
                break;
            }
            case "transfer": {//adjust later
                textAndBox("Transfer from");
                JLabel label = new JLabel("To Account");
                label.setFont(new Font("calibri", Font.BOLD, 20));
                label.setBounds(260, 270, 250, 100);
                add(label);
                customers = new JComboBox<>();
                model = new DefaultComboBoxModel<>();
                extraAccounts = new JComboBox<>(model);
                customers.setBounds(260, 340, 200, 100);
                extraAccounts.setBounds(480, 340, 250, 100);
                add(customers);
                add(extraAccounts);
                showName();
                next = new NextButton();
                add(next);
                next.addActionListener(newListener);
                break;
            }
            case "loans": {
                setRadio("Request Loan", "Repay Loan");
                JTextArea comment = new JTextArea("You can request a loan of no more than 10,000"
                        + "\n on each currency.\n\n"
                        + "You cannot request a loan if you haven't repaid it.\n\n"
                        + "We will charge a fee at a 0.1% daily rate.");
                next = new NextButton();

                JLabel label = new JLabel("Choose an Account");
                label.setFont(new Font("calibri", Font.BOLD, 20));
                label.setBounds(260, 150, 300, 100);
                add(label);
                accounts = new JComboBox<>();
                accounts.setBounds(260, 210, 250, 100);

                comment.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                comment.setForeground(Color.gray);
                comment.setBounds(260, 320, 500, 150);
                comment.setFocusable(false);
                add(comment);
                showName();
                add(next);
                add(accounts);
                next.addActionListener(newListener);
                break;
            }
            case "accounts": {
                //textAndBox()
                setRadio("All Customers", "Loan Customers");

                next = new NextButton();
                JLabel label1 = new JLabel("Customer");
                JLabel label2 = new JLabel("Account");
                label1.setFont(new Font("calibri", Font.BOLD, 20));
                label2.setFont(new Font("calibri", Font.BOLD, 20));
                label1.setBounds(260, 150, 300, 100);
                label2.setBounds(260, 270, 300, 100);
                add(label1);
                add(label2);
                customers = new JComboBox<>();
                model = new DefaultComboBoxModel<>();
                accounts = new JComboBox<>(model);
                customers.setBounds(260, 210, 250, 100);
                accounts.setBounds(260, 330, 250, 100);
                add(next);
                add(customers);
                add(accounts);
                showBalance();
                next.addActionListener(newListener);
                break;
            }
        }
    }

    //show components&info

    private void textAndBox(String line) {
        JLabel label = new JLabel(line);
        label.setFont(new Font("calibri", Font.BOLD, 20));
        label.setBounds(260, 130, 300, 100);
        accounts = new JComboBox<>();
        accounts.setBounds(260, 200, 250, 100);
        add(label);
        add(accounts);
    }

    private void resetSelect() {
        accounts.removeAllItems();
        if (customer.getCheckingAccounts().size() != 0) {
            for (Account account : customer.getCheckingAccounts()) {
                accounts.addItem(account);
            }
        }
        if (customer.getSavingsAccounts().size() != 0) {
            for (Account account : customer.getSavingsAccounts()) {
                accounts.addItem(account);
            }
        }
        if (panelName.equals("select") && customer.getSecurityAccounts() != null) {
            accounts.addItem(customer.getSecurityAccounts());
        }
    }

    private void resetTransfer() {
        accounts.removeAllItems();
        customers.removeAllItems();
        model.removeAllElements();
        if (customer.getCheckingAccounts().size() != 0) {
            for (Account account : customer.getCheckingAccounts()) {
                accounts.addItem(account);
            }
            accounts.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    updateToAccounts((Customer) customers.getSelectedItem());
                }
            });
        }
        if (bank.getCustomers().size() != 0) {
            for (Customer customer : bank.getCustomers()) {
                customers.addItem(customer);
            }
            customers.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.DESELECTED)
                        updateToAccounts((Customer) customers.getSelectedItem());
                }
            });
        }
        updateToAccounts((Customer) customers.getSelectedItem());
    }

    private void resetAccounts() {
        customers.removeAllItems();
        if (bank.getCustomers().size() != 0) {
            if (radio1.isSelected()) {
                for (Customer customer : bank.getCustomers()) {
                    customers.addItem(customer);
                }
            } else {
                for (Customer customer : bank.getCustomers()) {
                    if (customer.findLoanAccounts().size() != 0) {
                        customers.addItem(customer);
                    }
                }
            }
            customers.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.DESELECTED) {
                        updateViewAccounts((Customer) customers.getSelectedItem());

                    }
                }
            });
            updateViewAccounts((Customer) customers.getSelectedItem());
        }
    }

    private void updateViewAccounts(Customer selected) {
        model.removeAllElements();
        if (selected != null) {
            if (radio1.isSelected()) {
                if (selected.getCheckingAccounts().size() != 0) {
                    for (Account account : selected.getCheckingAccounts()) {
                        model.addElement(account);
                    }
                }
                if (selected.getSavingsAccounts().size() != 0) {
                    for (Account account : selected.getSavingsAccounts()) {
                        model.addElement(account);
                    }
                }
            } else if (selected.findLoanAccounts().size() != 0) {
                for (Account account : selected.findLoanAccounts()) {
                    model.addElement(account);
                }
            }
        }
    }

    private void updateToAccounts(Customer toCustomer) {
        model.removeAllElements();
        if (toCustomer != null) {
            if (toCustomer.getCheckingAccounts().size() != 0) {
                for (Account account : toCustomer.getCheckingAccounts()) {
                    if (account == accounts.getSelectedItem())
                        continue;
                    model.addElement(account);
                }
            }
        }
    }

    public void reset() {//update data
    		super.reset();
        if (panelName.equals("transfer"))
            resetTransfer();
        if (panelName.equals("loans") || panelName.equals("select")) 
            resetSelect();
        if (panelName.equals("accounts")) 
            resetAccounts();
        if (panelName.equals("buystocks") || panelName.equals("sellstocks")) {
            resetSelect();
            enter.setText("");
        }
    }

    public void forward() {
        if (panelName.equals("select")) {
            Account newAccount = (Account) accounts.getSelectedItem();
            setAccount(newAccount);
            if (newAccount != null) {
                setAccount(newAccount);
                if (this.quickVerify()) {
                    if (account instanceof SecurityAccount)
                        super.link();
                    else
                        super.forward();
                }
            } else {
                noAccountMsg();
            }
        } 
        else if (panelName.equals("transfer")) {
            CheckingAccount accountFrom = (CheckingAccount) accounts.getSelectedItem();
            CheckingAccount accountTo = (CheckingAccount) extraAccounts.getSelectedItem();
            if (accountFrom == null || accountTo == null) {
                noAccountMsg();
            } else
                transactions();
        } 
        else if (panelName.equals("loans")) {
            if (!radio1.isSelected() && !radio2.isSelected()) {
                JOptionPane.showMessageDialog(null,
                        "Please select a loan transaction!",
                        "Request Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                if (accounts.getSelectedItem() == null) {
                    noAccountMsg();
                    return;
                } else if (!(accounts.getSelectedItem() instanceof SavingsAccount)) {
                    JOptionPane.showMessageDialog(null,
                            "Please select a savings account!",
                            "Request Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                } else
                    transactions();
            }
        } 
        else if (panelName.equals("accounts")) {
            if (!radio1.isSelected() && !radio2.isSelected()) {
                JOptionPane.showMessageDialog(null,
                        "Please select customer type!",
                        "Request Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                Account account = (Account) accounts.getSelectedItem();
                Customer customer = (Customer) customers.getSelectedItem();
                if (account != null && customer != null) {
                    setAccount(account);
                    setCustomer(customer);
                    if (radio1.isSelected())
                        super.forward();
                    else
                        super.link();
                } else {
                    noAccountMsg();
                }
            }
        } 
        else if (panelName.equals("buystocks")) {
            if (accounts.getSelectedItem() == null) {
                noAccountMsg();
                return;
            } else if (!(accounts.getSelectedItem() instanceof SavingsAccount)) {
                JOptionPane.showMessageDialog(null,
                        "Please select a savings account!",
                        "Request Failed", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                try {
                    int shares = Integer.parseInt(enter.getText());
                    if (shares <= 0) {
                        requestFailMsg();
                    } else {
                        int response = JOptionPane.showConfirmDialog(null,
                                "$" + CommonMathMethod.twoDecimal(shares * stock.getUSDPrice()) + " in total, yes to purchase!", "Buy Stocks", 0);
                        if (response == JOptionPane.YES_OPTION) {
                            dealshares = shares;
                            transactions();
                        }
                    }
                } catch (Exception e) {
                    requestFailMsg();
                }
            }
        } 
        else if (panelName.equals("sellstocks")) {
            if (accounts.getSelectedItem() == null) {
                noAccountMsg();
                return;
            } else if (!(accounts.getSelectedItem() instanceof SavingsAccount)) {
                JOptionPane.showMessageDialog(null,
                        "Please select a savings account!",
                        "Request Failed", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                try {
                    int shares = Integer.parseInt(enter.getText());
                    if (shares <= 0) {
                        requestFailMsg();
                    } else {
                        int response = JOptionPane.showConfirmDialog(null,
                                "$" + CommonMathMethod.twoDecimal(shares * stock.getUSDPrice()) + " in total, yes to sell!", "Sell Stocks", 0);
                        if (response == JOptionPane.YES_OPTION) {
                            dealshares = shares;
                            transactions();
                        }
                    }
                } catch (Exception e) {
                    requestFailMsg();
                }
            }
        }
        else
            super.forward();
    }
    
    private void transactions() {
        JTextField jTextFieldMoney = new JTextField(10);
        JPasswordField jPasswordFieldPassword = new JPasswordField(10);
        JComboBox<Currency> jComboBoxCurrency = new JComboBox<>();
        for (Currency currency : Currency.values())
            jComboBoxCurrency.addItem(currency);

        JPanel jPanel = new JPanel();
        if (!(panelName.equals("loans") && radio2.isSelected())
                && !panelName.equals("buystocks")
                && !panelName.equals("sellstocks")) {
            jPanel.add(new JLabel("Money: "));
            jPanel.add(jTextFieldMoney);
        }
        jPanel.add(Box.createHorizontalStrut(15)); // a spacer
        jPanel.add(new JLabel("Password: "));
        jPanel.add(jPasswordFieldPassword);
        jPanel.add(Box.createHorizontalStrut(15)); // a spacer
        jPanel.add(new JLabel("Currency: "));
        jPanel.add(jComboBoxCurrency);

        String dialogTitle;
        switch (panelName) {
            case "loans":{
            		if (radio1.isSelected())
            			dialogTitle = "Request Loans";
            		else
            			dialogTitle = "Repay Loans";
            		break;
            }
            case "transfer":
                dialogTitle = "Transfer";
                break;
            case "buystocks":
                dialogTitle = "Buy Stocks";
                break;
            case "sellstocks":
                dialogTitle = "Sell Stocks";
                break;
            default:
                dialogTitle = "";
        }
        
        int result = JOptionPane.showConfirmDialog(null, jPanel,
                dialogTitle,
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Currency currency = (Currency) jComboBoxCurrency.getSelectedItem();
            String password = String.valueOf(jPasswordFieldPassword.getPassword());
            try {
                if ((!(panelName.equals("loans") && radio2.isSelected())
                        && !panelName.equals("buystocks")
                        && !panelName.equals("sellstocks"))
                        && jTextFieldMoney.getText().length() == 0) {
                    noInputMsg();
                } else if (password.length() == 0) {
                    noPWMsg();
                } else {
                    switch (panelName) {
                        case "transfer": {
                            double money = Double.valueOf(jTextFieldMoney.getText());
                            CheckingAccount accountFrom = (CheckingAccount) accounts.getSelectedItem();
                            CheckingAccount accountTo = (CheckingAccount) extraAccounts.getSelectedItem();
                            if (!accountFrom.isPasswordRight(password)) {
                                wrongPWMsg();
                            } else {
                                int transfer = accountFrom.transferOut(accountTo, money, currency, date);
                                if (transfer == -1) {
                                    JOptionPane.showMessageDialog(null,
                                            "Transfer amount must be more than 0!",
                                            "Request Failed", JOptionPane.ERROR_MESSAGE);
                                } else if (transfer == 0) {
                                    noMoneyMsg();
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Transfer Money Finished!",
                                            "Transfer money success", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                            break;
                        }
                        case "loans": {
                            SavingsAccount savings = (SavingsAccount) accounts.getSelectedItem();
                            if (!savings.isPasswordRight(password)) {
                                wrongPWMsg();
                            } else {
                                if (radio1.isSelected()) {
                                    double money = Double.valueOf(jTextFieldMoney.getText());
                                    int loan = savings.requestLoan(money, currency, date);
                                    if (loan == -1) {
                                        JOptionPane.showMessageDialog(null,
                                                "You need to repay your loan first!",
                                                "Request Failed", JOptionPane.ERROR_MESSAGE);
                                    } else if (loan == 0) {
                                        JOptionPane.showMessageDialog(null,
                                                "Loan must be between 0-10000!",
                                                "Request Failed", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null,
                                                "Loan Finished!",
                                                "Loan success", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                } else {
                                    int repay = savings.repayLoan(currency, date);
                                    if (repay == -1) {
                                        JOptionPane.showMessageDialog(null,
                                                "You have no loan to repay!",
                                                "Request Failed", JOptionPane.ERROR_MESSAGE);
                                    } else if (repay == 0) {
                                        noMoneyMsg();
                                    } else {
                                        JOptionPane.showMessageDialog(null,
                                                "Repay Finished!",
                                                "Repay success", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                            }
                            break;
                        }
                        case "buystocks": {
                            SavingsAccount savings = (SavingsAccount) accounts.getSelectedItem();
                            if (!savings.isPasswordRight(password)) {
                                wrongPWMsg();
                            } else {
                                int buy = customer.getSecurityAccounts().buyStock(stock.getName(), dealshares, currency, savings, date);
                                if (buy == -1) {
                                    JOptionPane.showMessageDialog(null,
                                            "Cannot buy more than bank's inventory!",
                                            "Request Failed", JOptionPane.ERROR_MESSAGE);
                                } else if (buy == 1) {
                                    noMoneyMsg();
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Purchase Finished!",
                                            "Purchase success", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                            break;
                        }
                        case "sellstocks": {
                            SavingsAccount savings = (SavingsAccount) accounts.getSelectedItem();
                            if (!savings.isPasswordRight(password)) {
                                wrongPWMsg();
                            } else {
                                int sell = customer.getSecurityAccounts().sellStock(stock.getName(), dealshares, currency, savings, date);
                                if (sell == -1) {
                                    JOptionPane.showMessageDialog(null,
                                            "Cannot sell more than your holding shares!",
                                            "Request Failed", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Selling Finished!",
                                            "Selling success", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                            break;
                        }
                    }
                }
                reset();
            } catch (Exception e) {
                requestFailMsg();
            }
        }
    }
}