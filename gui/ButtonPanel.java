package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import bank.*;
import bank.Currency;

//user and manager interface
@SuppressWarnings("serial")
public class ButtonPanel extends MainPanel {

    private AtmButton[] buttons;
    private AtmPanel[] buttonLinks;
    private int buttonNum;

    public ButtonPanel(String s, AtmFrame newFrame, Bank newBank, Date newDate) {
        super(s, newFrame, newBank, newDate);
        switch (s) {
            case "main": {
                String[] names = {"Open an Account", "View My Accounts", "Transfer",
                        "Request Loans"};
                int[] sizes = {17, 15, 20, 17};
                setButtons(names, sizes, 4);
                buttonNum = 4;
                setTitle("Select Your Transaction");
                showName();
                break;
            }
            case "openopt": {
                setTitle("Select a Type of Account");
                String[] names = {"Checking", "Savings", "Security"};
                int[] sizes = {20, 20, 20};
                showName();
                setButtons(names, sizes, 3);
                buttonNum = 3;
                break;
            }
            case "view": {
                String[] names = {"Withdrawal", "Deposit", "Transactions", "Close Account"};
                int[] sizes = {20, 20, 17, 17};
                setButtons(names, sizes, 4);
                buttonNum = 4;
                buttons[3].setBackground(Color.gray);
                showBalance();
                break;
            }
            case "withdraw": {
                String[] names = {"$20", "$40", "$60", "$80",
                        "$100", "Enter Amount"};
                int[] sizes = {20, 20, 20, 20, 20, 20};
                setButtons(names, sizes, 6);
                buttonNum = 6;
                setTitle("Get Cash");
                showBalance();
                break;
            }
            case "deposit": {
                String[] names = {"$20", "$40", "$60", "$80",
                        "$100", "Enter Amount"};
                int[] sizes = {20, 20, 20, 20, 20, 20};
                setButtons(names, sizes, 6);
                buttonNum = 6;
                setTitle("Deposit");
                showBalance();
                break;
            }
            case "security": {//view security
                String[] names = {"View Stocks", "Close Account"};
                int[] sizes = {17, 17};
                buttonNum = 2;
                setButtons(names, sizes, 2);
                buttons[1].setBackground(Color.gray);
                showName();
                setTitle("Security Account");
                break;
            }
            case "manager": {
                String[] names = {"Add 1 Day", "Add 3 Days", "Daily Report"
                        , "View Customers", "View Stocks", "Add Stocks"};
                int[] sizes = {17, 17, 15, 15, 17, 17};
                setButtons(names, sizes, 6);
                buttonNum = 6;
                setTitle("Hello! Banker");
                showBalance();
                break;
            }
        }
    }

    //show components&info

    private void setButtons(String[] words, int[] sizes, int num) {
        buttons = new AtmButton[num];
        buttonLinks = new AtmPanel[num];
        for (int i = 0; i < num; i++) {
            buttons[i] = new AtmButton(words[i], sizes[i]);
            buttons[i].setBounds(370 - (int) Math.pow(-1, i) * 110,
                    150 + (int) (i / 2) * 120, 200, 100);
            add(buttons[i]);
            buttons[i].addActionListener(newListener);
        }
    }

    //reset functions

    private void newStocks() {
        JTextField jTextFieldName = new JTextField(10);
        JTextField jTextFieldPrice = new JTextField(10);
        JTextField jTextFieldShares = new JTextField(10);
        JPanel jPanel = new JPanel();

        jPanel.add(Box.createHorizontalStrut(15)); // a spacer
        jPanel.add(new JLabel("Name: "));
        jPanel.add(jTextFieldName);
        jPanel.add(Box.createHorizontalStrut(15)); // a spacer
        jPanel.add(new JLabel("Price: "));
        jPanel.add(jTextFieldPrice);
        jPanel.add(Box.createHorizontalStrut(15)); // a spacer
        jPanel.add(new JLabel("Shares: "));
        jPanel.add(jTextFieldShares);

        int result = JOptionPane.showConfirmDialog(null, jPanel,
                "Add New Stock", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String stockname = jTextFieldName.getText();
                double price = Double.parseDouble(jTextFieldPrice.getText());
                int shares = Integer.parseInt(jTextFieldShares.getText());
                if (stockname.length() == 0 || (price <= 0) || (shares <= 0)) {
                    requestFailMsg();
                } else if (bank.getStocks().get(stockname) != null) {
                    JOptionPane.showMessageDialog(null,
                            "This stock already exists!", "Request Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    bank.addNewStock(stockname, price, shares, 0);
                    JOptionPane.showMessageDialog(null,
                            "Add new stock success!", "Add New Stock", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                requestFailMsg();
            }
        }
    }

    //events
    public void addEvent(ActionEvent e) {
        super.addEvent(e);
        for (int i = 0; i < buttonNum; i++) {
            if (e.getSource().equals(buttons[i])) linkButton(i);
        }
    }

    public void setLinkButton(AtmPanel panel, int n) {
        buttonLinks[n] = panel;
    }

    public void linkButton(int n) {
        if (panelName.equals("main") || panelName.equals("openopt")
                || panelName.equals("view") || panelName.equals("manager")
                || panelName.equals("security")) {
            if (panelName.equals("manager") && (n == 0 || n == 1)) {
                Calendar calendar = Calendar.getInstance();
                date = bank.getDate();

                calendar.setTime(date);
                calendar.add(Calendar.DATE, 1 + n * 2);
                date = calendar.getTime();
                //update database date
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                String[] updateValues = {formatter.format(date)};
                String[] args = {"DATE"};
                Database.updateData("BANKER", "USERNAME", bank.getBankerName(), args, updateValues);

                bank.setDate(date);
                bank.calculateLoanInterest(date);
                bank.addSaveInterest(1 + n * 2, date);
                JOptionPane.showMessageDialog(null,
                        n == 0 ? "One day added!" : "Three days added!", "Update Date", JOptionPane.INFORMATION_MESSAGE);
            } else if (panelName.equals("manager") && (n == 5)) {
                newStocks();
            } else if (panelName.equals("view") && (n == 3)) {
                closeAccount();
                super.backward();
            } else if (panelName.equals("security") && (n == 1)) {
            		if (customer.getSecurityAccounts().getStocks().size()>0)
            			JOptionPane.showMessageDialog(null,
            					"Your cannot close your security account while holding stocks!",
            					"Request Failed", JOptionPane.ERROR_MESSAGE);
            		else {
            			closeAccount();
            			super.backward();
            		}
            } else if (panelName.equals("openopt") && (n == 2)
                    && customer.getSecurityAccounts() != null) {
                JOptionPane.showMessageDialog(null,
                        "Your can only have one security account!",
                        "Request Failed", JOptionPane.ERROR_MESSAGE);
            } else if (panelName.equals("openopt") && (n == 2)
                    && customer.getSavingsAccounts().size() == 0) {
                JOptionPane.showMessageDialog(null,
                        "You do not have a savings account!", "Request Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                buttonLinks[n].setDate(date);
                buttonLinks[n].setCustomer(customer);
                buttonLinks[n].setAccount(account);
                frame.addPanel(buttonLinks[n]);
            }
        } else if (panelName.equals("withdraw")) {
            if (n < 5) {
                if (this.quickVerify()) {
                    int withdraw = account.withdraw(20 * (n + 1), Currency.USD, date);
                    if (withdraw == 0) {
                        noMoneyMsg();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Withdraw Money Finished!",
                                "Withdraw money success", JOptionPane.INFORMATION_MESSAGE);
                        resetBalance();
                    }
                }
            } else {
                transactions();
            }
        } else if (panelName.equals("deposit")) {
            if (n < 5) {
                if (this.quickVerify()) {
                    account.save(20 * (n + 1), Currency.USD, date);
                    JOptionPane.showMessageDialog(null,
                            "Save Money Finished!",
                            "Save money success", JOptionPane.INFORMATION_MESSAGE);
                    resetBalance();
                }
            } else {
                transactions();
            }
        }
    }
    
    private void transactions() {
        JTextField jTextFieldMoney = new JTextField(10);
        JPasswordField jPasswordFieldPassword = new JPasswordField(10);
        JComboBox<Currency> jComboBoxCurrency = new JComboBox<>();
        for (Currency currency : Currency.values())
            jComboBoxCurrency.addItem(currency);

        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("Money: "));
        jPanel.add(jTextFieldMoney);
        jPanel.add(Box.createHorizontalStrut(15)); // a spacer
        jPanel.add(new JLabel("Password: "));
        jPanel.add(jPasswordFieldPassword);
        jPanel.add(Box.createHorizontalStrut(15)); // a spacer
        jPanel.add(new JLabel("Currency: "));
        jPanel.add(jComboBoxCurrency);

        int result = JOptionPane.showConfirmDialog(null, jPanel,
                panelName, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Currency currency = (Currency) jComboBoxCurrency.getSelectedItem();
            String password = String.valueOf(jPasswordFieldPassword.getPassword());
            try {
                if (jTextFieldMoney.getText().length() == 0) {
                    noInputMsg();
                } else if (password.length() == 0) {
                    noPWMsg();
                } else {
                    switch (panelName) {
                        case "deposit": {
                            if (!account.isPasswordRight(password)) {
                                wrongPWMsg();
                            } else {
                                double money = Double.valueOf(jTextFieldMoney.getText());
                                account.save(money, currency, date);
                                JOptionPane.showMessageDialog(null,
                                        "Save Money Finished!",
                                        "Save money success", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        }
                        case "withdraw": {
                            if (!account.isPasswordRight(password)) {
                                wrongPWMsg();
                            } else {
                                double money = Double.valueOf(jTextFieldMoney.getText());
                                int withdraw = account.withdraw(money, currency, date);
                                if (withdraw == -1) {
                                    JOptionPane.showMessageDialog(null,
                                            "Withdrawal must be more than 0!",
                                            "Request Failed", JOptionPane.ERROR_MESSAGE);
                                } else if (withdraw == 0) {
                                    noMoneyMsg();
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Withdraw Money Finished!",
                                            "Withdraw money success", JOptionPane.INFORMATION_MESSAGE);
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

    private void closeAccount() {
        int response = JOptionPane.showConfirmDialog(null, "You need to pay 5 USD to close this account, do you want to close it？(We will not return the remaining balances.)", "Close account", 0);
        if (response == JOptionPane.YES_OPTION) {
            customer.closeAccount(account, date);
            JOptionPane.showMessageDialog(null,
                    "Close account successfully!",
                    "Close Account", JOptionPane.INFORMATION_MESSAGE);
            super.backward();
        }
    }
}