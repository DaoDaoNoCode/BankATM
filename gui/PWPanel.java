package gui;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import bank.*;

//password setting/entering
@SuppressWarnings("serial")
public class PWPanel extends AtmPanel {
    private final SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
    protected JLabel welcome;
    protected JLabel newline;
    protected JLabel time;
    protected AtmTextField idField = new AtmTextField("Enter your Username");
    protected AtmPWField pwField = new AtmPWField("Enter Password");
    protected AtmPWField confField = new AtmPWField("Confirm Password");

    public PWPanel(AtmFrame newFrame, Bank newBank, Date newDate) {
        super(newFrame, newBank, newDate);
    }

    public PWPanel(String s, AtmFrame newFrame, Bank newBank, Date newDate) {
        this(newFrame, newBank, newDate);
        panelName = s;
        switch (s) {
            case "ManagerLogin": {
                welcome = new JLabel("  Manager Login");
                welcome.setFont(new Font("calibri", Font.BOLD, 30));
                welcome.setBounds(260, 100, 400, 100);
                next = new NextButton("Login");
                idField.setBounds(260, 225, 280, 50);
                pwField.setBounds(260, 295, 280, 50);
                next.setBounds(410, 370, 125, 60);
                add(idField);
                add(pwField);
                add(next);
                next.addActionListener(newListener);
                break;
            }
            case "ID": {
                welcome = new JLabel("Change Password");
                welcome.setFont(new Font("calibri", Font.BOLD, 30));
                welcome.setBounds(260, 100, 400, 100);
                idField.setBounds(260, 225, 280, 50);
                welcome.setBounds(260, 100, 400, 100);
                next = new NextButton();
                next.setBounds(410, 315, 125, 60);
                add(idField);
                add(next);
                next.addActionListener(newListener);
                break;
            }
            case "PW": {
                welcome = new JLabel("Change Password");
                welcome.setFont(new Font("calibri", Font.BOLD, 30));
                welcome.setBounds(260, 100, 400, 100);
                next = new NextButton();
                next.setBounds(410, 370, 125, 60);
                pwField.setBounds(260, 225, 280, 50);
                confField.setBounds(260, 295, 280, 50);
                add(pwField);
                add(confField);
                add(next);
                next.addActionListener(newListener);
                break;
            }
            case "register": {
                welcome = new JLabel("Customer Register");
                welcome.setFont(new Font("calibri", Font.BOLD, 30));
                welcome.setBounds(260, 80, 400, 100);
                idField.setBounds(260, 190, 280, 50);
                pwField.setBounds(260, 260, 280, 50);
                confField.setBounds(260, 330, 280, 50);
                next = new NextButton("Register", 17);
                next.setBounds(410, 410, 125, 60);
                add(idField);
                add(pwField);
                add(confField);
                add(next);
                next.addActionListener(newListener);
                break;
            }
            case "welcome": {
                welcome = new JLabel("Welcome to OOD Bank");
                welcome.setBounds(175, 50, 800, 200);
                welcome.setFont(new Font("calibri", Font.BOLD, 40));
                welcome.setForeground(Color.decode("#4169E1"));
                JLabel jLabelDate = new JLabel("Today is ");
                time = new JLabel(format.format(date));
                JLabel jLabelEnter = new JLabel("Press enter to continue");
                jLabelDate.setFont(new Font("calibri", Font.BOLD, 30));
                time.setFont(new Font("calibri", Font.BOLD, 30));
                jLabelEnter.setFont(new Font("calibri", Font.BOLD, 30));
                jLabelDate.setBounds(225, 200, 400, 100);
                time.setBounds(375, 200, 400, 100);
                jLabelEnter.setBounds(225, 270, 400, 100);
                next = new NextButton("Enter");
                jLabelDate.setForeground(Color.gray);
                jLabelEnter.setForeground(Color.gray);
                add(jLabelDate);
                add(time);
                add(jLabelEnter);
                add(next);
                next.addActionListener(newListener);
                break;
            }
            case "checking": {
                welcome = new JLabel("New Checking Account");
                welcome.setFont(new Font("calibri", Font.BOLD, 25));
                next = new NextButton("Open");
                welcome.setBounds(260, 20, 400, 100);
                pwField.setBounds(260, 290, 280, 50);
                confField.setBounds(260, 360, 280, 50);
                next.setBounds(410, 440, 125, 60);
                JLabel jLabelHint1 = new JLabel("Accounts will be charged a fee of withdrawal every time.");
                JLabel jLabelHint2 = new JLabel("(0.05% for withdrawal less than 1000, ");
                JLabel jLabelHint3 = new JLabel("0.1% for withdrawal equals to or more than 1000)");
                JLabel jLabelHint4 = new JLabel("Savings deposits no less than 1000 can get 0.02% daily interests.");
                jLabelHint1.setBounds(160, 110, 600, 60);
                jLabelHint2.setBounds(230, 150, 600, 60);
                jLabelHint3.setBounds(200, 175, 600, 60);
                jLabelHint4.setBounds(130, 215, 700, 60);
                jLabelHint1.setForeground(Color.gray);
                jLabelHint2.setForeground(Color.gray);
                jLabelHint3.setForeground(Color.gray);
                jLabelHint4.setForeground(Color.gray);
                jLabelHint1.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                jLabelHint2.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                jLabelHint3.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                jLabelHint4.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                add(pwField);
                add(confField);
                add(next);
                add(jLabelHint1);
                add(jLabelHint2);
                add(jLabelHint3);
                add(jLabelHint4);
                next.addActionListener(newListener);
                break;
            }
            case "savings": {
                welcome = new JLabel("New Savings Account");
                welcome.setFont(new Font("calibri", Font.BOLD, 27));
                next = new NextButton("Open");
                welcome.setBounds(260, 20, 400, 100);
                pwField.setBounds(260, 290, 280, 50);
                confField.setBounds(260, 360, 280, 50);
                next.setBounds(410, 440, 125, 60);
                JLabel jLabelHint1 = new JLabel("Accounts will be charged a fee of withdrawal every time.");
                JLabel jLabelHint2 = new JLabel("(0.05% for withdrawal less than 1000, ");
                JLabel jLabelHint3 = new JLabel("0.1% for withdrawal equals to or more than 1000)");
                JLabel jLabelHint4 = new JLabel("Deposits no less than 1000 will get a 0.02% daily save interest.");
                jLabelHint1.setBounds(160, 110, 600, 60);
                jLabelHint2.setBounds(230, 150, 600, 60);
                jLabelHint3.setBounds(200, 175, 600, 60);
                jLabelHint4.setBounds(130, 215, 700, 60);
                jLabelHint1.setForeground(Color.gray);
                jLabelHint2.setForeground(Color.gray);
                jLabelHint3.setForeground(Color.gray);
                jLabelHint4.setForeground(Color.gray);
                jLabelHint1.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                jLabelHint2.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                jLabelHint3.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                jLabelHint4.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                add(pwField);
                add(confField);
                add(next);
                add(jLabelHint1);
                add(jLabelHint2);
                add(jLabelHint3);
                add(jLabelHint4);
                next.addActionListener(newListener);
                break;
            }
            case "security": {
                welcome = new JLabel("New Security Account");
                welcome.setFont(new Font("calibri", Font.BOLD, 27));
                next = new NextButton("Open");
                welcome.setBounds(260, 20, 400, 100);
                pwField.setBounds(260, 290, 280, 50);
                confField.setBounds(260, 360, 280, 50);
                next.setBounds(410, 440, 125, 60);
                JLabel jLabelHint1 = new JLabel("If you have at least one savings account, you can open");
                JLabel jLabelHint2 = new JLabel("a security account");
                JLabel jLabelHint3 = new JLabel("You can buy and sell stocks with this account");
                JLabel jLabelHint4 = new JLabel("One user can only open one security account");
                jLabelHint1.setBounds(170, 110, 600, 60);
                jLabelHint2.setBounds(320, 135, 600, 60);
                jLabelHint3.setBounds(200, 175, 600, 60);
                jLabelHint4.setBounds(200, 215, 600, 60);
                jLabelHint1.setForeground(Color.gray);
                jLabelHint2.setForeground(Color.gray);
                jLabelHint3.setForeground(Color.gray);
                jLabelHint4.setForeground(Color.gray);
                jLabelHint1.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                jLabelHint2.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                jLabelHint3.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                jLabelHint4.setFont(new Font("calibri", Font.CENTER_BASELINE, 17));
                add(pwField);
                add(confField);
                add(next);
                add(jLabelHint1);
                add(jLabelHint2);
                add(jLabelHint3);
                add(jLabelHint4);
                next.addActionListener(newListener);
                break;
            }
        }
        add(welcome);
    }

    public void forward() {
        switch (panelName) {
            case "ManagerLogin": {
                if (pwField.getPW().equals(bank.getBankerPW()) && idField.getText().equals(bank.getBankerName())) {
                    super.forward();
                } else
                    JOptionPane.showMessageDialog(null,
                            "Incorrect username or password.",
                            "Request Failed", JOptionPane.ERROR_MESSAGE);
                break;
            }
            case "ID": {
                for (Customer customer : bank.getCustomers()) {
                    if (customer.getUsername().equals(idField.getText())) {
                        forward.setCustomer(customer);
                        frame.addPanel(forward);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null,
                        "ID does not exist.",
                        "Request Failed", JOptionPane.ERROR_MESSAGE);
                break;
            }
            case "PW": {
                if (pwField.blank()) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a password.",
                            "Request Failed", JOptionPane.ERROR_MESSAGE);
                } else if (!pwField.getPW().equals(confField.getPW())) {
                    JOptionPane.showMessageDialog(null,
                            "Please repeat with the same password.",
                            "Request Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    customer.setPassword(pwField.getPW());
                    super.forward();
                }
                break;
            }
            case "register": {
                String username = idField.getText();
                String password = pwField.getPW();
                String confirm = confField.getPW();
                boolean nameIsUsed = false;
                if (username.length() == 0 || pwField.blank() || confField.blank()) {
                    JOptionPane.showMessageDialog(null,
                            "Username or password cannot be empty!", "Request Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    for (Customer customer : bank.getCustomers()) {
                        if (customer.getUsername().equals(username)) {
                            JOptionPane.showMessageDialog(null,
                                    "This username has been used!", "Request Failed", JOptionPane.ERROR_MESSAGE);
                            nameIsUsed = true;
                        }
                    }
                    if (!nameIsUsed) {
                        if (!password.equals(confirm)) {
                            JOptionPane.showMessageDialog(null,
                                    "Please repeat with the same password!", "Request Failed", JOptionPane.ERROR_MESSAGE);
                        } else {
                            bank.registerCustomer(new Customer(bank, username, password));
                            JOptionPane.showMessageDialog(null,
                                    "Welcome! Login and start your transactions!", "Register Success", JOptionPane.INFORMATION_MESSAGE);
                            super.forward();
                        }
                    }
                }
                break;
            }
            case "checking": {
                String password = pwField.getPW();
                String confirm = confField.getPW();
                if (pwField.blank() || confField.blank()) {
                    JOptionPane.showMessageDialog(null,
                            "Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!password.equals(confirm)) {
                        JOptionPane.showMessageDialog(null, "Please repeat with the same password!", "Request Failed", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int response = JOptionPane.showConfirmDialog(null, "You need to pay 5 USD to open an account, do you want to open it？", "Open account", 0);
                        if (response == JOptionPane.YES_OPTION) {
                            Account account = customer.openAccount(AccountType.CHECKING, password, date);
                            JOptionPane.showMessageDialog(null,
                                    "Open checking account!\nNumber is: " + account.getNumber(), "Open account success", JOptionPane.INFORMATION_MESSAGE);
                            super.forward();
                        }
                    }
                }
                break;
            }
            case "savings": {
                String password = pwField.getPW();
                String confirm = confField.getPW();
                if (pwField.blank() || confField.blank()) {
                    JOptionPane.showMessageDialog(null,
                            "Password cannot be empty!", "Request Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!password.equals(confirm)) {
                        JOptionPane.showMessageDialog(null, "Please repeat with the same password!", "Request Failed", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int response = JOptionPane.showConfirmDialog(null, "You need to pay 5 USD to open an account, do you want to open it？", "Open account", 0);
                        if (response == JOptionPane.YES_OPTION) {
                            Account account = customer.openAccount(AccountType.SAVINGS, password, date);
                            JOptionPane.showMessageDialog(null,
                                    "Open savings account!\nNumber is: " + account.getNumber(), "Open account success", JOptionPane.INFORMATION_MESSAGE);
                            super.forward();
                        }
                    }
                }
                break;
            }
            case "security": {
                String password = pwField.getPW();
                String confirm = confField.getPW();
                if (pwField.blank() || confField.blank()) {
                    JOptionPane.showMessageDialog(null,
                            "Password cannot be empty!", "Request Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!password.equals(confirm)) {
                        JOptionPane.showMessageDialog(null, "Please repeat with the same password!", "Request Failed", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int response = JOptionPane.showConfirmDialog(null, "You need to pay 5 USD to open an account, do you want to open it？", "Open account", 0);
                        if (response == JOptionPane.YES_OPTION) {
                            customer.openAccount(AccountType.SECURITY, password, date);
                            JOptionPane.showMessageDialog(null,
                                    "Open security account!\nYou can now deal with stocks!", "Open account success", JOptionPane.INFORMATION_MESSAGE);
                            super.forward();
                        }
                    }
                }
                break;
            }
            default:
                super.forward();
        }
    }

    public void reset() {
        if (panelName.equals("welcome"))
            time.setText(format.format(date));
        idField.reset();
        pwField.reset();
        confField.reset();
    }
}
