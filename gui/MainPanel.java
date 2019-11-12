package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import bank.*;
import bank.Currency;

//user and manager interface
@SuppressWarnings("serial")
public class MainPanel extends AtmPanel {

    private final String[] currencySign = {"$", "¥", "€"};
    private final SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
    private IconLabel icon = new IconLabel();
    private JPanel bg = new JPanel();
    private JLabel title;
    //show balance or name
    private JLabel name;
    private JLabel[] labels;
    //stocks component
    protected JRadioButton radio1, radio2;

    public MainPanel(String s, AtmFrame newFrame, Bank newBank, Date newDate) {
        super(newFrame, newBank, newDate);
        setLayout(null);
        setBackground(Color.decode("#FFFFFF"));
        bg.setLayout(null);
        bg.setBackground(Color.decode("#F7F7F7"));
        bg.setBounds(0, 0, 220, 580);
        bg.setBorder(BorderFactory.createEtchedBorder());
        add(bg);
        bg.add(cancel);
        bg.add(icon);
        panelName = s;
    }

    //show components&info
    public void setTitle(String s) {
        if (title != null)
            remove(title);
        title = new JLabel(s);
        title.setBounds(240, 50, 500, 100);
        title.setFont(new Font("calibri", Font.BOLD, 20));
        title.setFocusable(false);
        add(title);
    }

    public void showBalance() {
        name = new JLabel("#");
        labels = new JLabel[3];
        labels[0] = new JLabel("#");
        labels[1] = new JLabel("#");
        labels[2] = new JLabel("#");
        JLabel usd = new JLabel("USD");
        JLabel cny = new JLabel("CNY");
        JLabel eur = new JLabel("EUR");

        name.setBounds(30, 110, 170, 100);
        name.setFont(new Font("calibri", Font.BOLD, 20));
        name.setFocusable(false);
        usd.setBounds(30, 150, 170, 100);
        usd.setFont(new Font("calibri", Font.BOLD, 15));
        usd.setFocusable(false);
        usd.setForeground(Color.gray);
        labels[0].setBounds(30, 180, 170, 100);
        labels[0].setFont(new Font("calibri", Font.BOLD, 20));
        labels[0].setFocusable(false);
        cny.setBounds(30, 215, 170, 100);
        cny.setFont(new Font("calibri", Font.BOLD, 15));
        cny.setFocusable(false);
        cny.setForeground(Color.gray);
        labels[1].setBounds(30, 245, 170, 100);
        labels[1].setFont(new Font("calibri", Font.BOLD, 20));
        labels[1].setFocusable(false);
        eur.setBounds(30, 280, 170, 100);
        eur.setFont(new Font("calibri", Font.BOLD, 15));
        eur.setFocusable(false);
        eur.setForeground(Color.gray);
        labels[2].setBounds(30, 310, 170, 100);
        labels[2].setFont(new Font("calibri", Font.BOLD, 20));
        labels[2].setFocusable(false);

        bg.add(name);
        bg.add(usd);
        bg.add(cny);
        bg.add(eur);
        bg.add(labels[0]);
        bg.add(labels[1]);
        bg.add(labels[2]);

        if (customer != null)
            resetBalance();
    }

    public void showName() {
        JLabel welcome = new JLabel("Hello!");
        name = new JLabel("#");
        welcome.setBounds(30, 110, 170, 100);
        welcome.setFont(new Font("calibri", Font.BOLD, 20));
        welcome.setForeground(Color.gray);
        welcome.setFocusable(false);
        name.setBounds(30, 150, 170, 100);
        name.setFont(new Font("calibri", Font.BOLD, 20));
        name.setForeground(Color.gray);
        name.setFocusable(false);
        bg.add(welcome);
        bg.add(name);
    }

    public void setRadio(String text1, String text2) {
        radio1 = new JRadioButton(text1);
        radio2 = new JRadioButton(text2);
        radio1.setBounds(240, 75, 200, 100);
        radio2.setBounds(450, 75, 200, 100);
        radio1.setFont(new Font("calibri", Font.BOLD, 20));
        radio2.setFont(new Font("calibri", Font.BOLD, 20));
        radio1.setSelected(true);
        radio1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radio2.setSelected(false);
                reset();
            }
        });
        radio2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radio1.setSelected(false);
                reset();
            }
        });
        add(radio1);
        add(radio2);
    }

    public void showStocks() {
        name = new JLabel("#");
        labels = new JLabel[3];
        labels[0] = new JLabel("#");
        labels[1] = new JLabel("#");
        labels[2] = new JLabel("#");

        name.setBounds(30, 110, 170, 100);
        name.setFont(new Font("calibri", Font.BOLD, 20));
        name.setFocusable(false);
        labels[0].setBounds(30, 180, 170, 100);
        labels[0].setFont(new Font("calibri", Font.BOLD, 20));
        labels[0].setFocusable(false);
        labels[1].setBounds(130, 182, 70, 100);
        labels[1].setFont(new Font("calibri", Font.BOLD, 15));
        labels[1].setFocusable(false);
        labels[2].setBounds(30, 245, 170, 100);
        labels[2].setFont(new Font("calibri", Font.BOLD, 20));
        labels[2].setFocusable(false);

        bg.add(name);
        bg.add(labels[0]);
        bg.add(labels[1]);
        bg.add(labels[2]);

        JLabel shares = new JLabel("Shares");
        JLabel price = new JLabel("Price");
        price.setBounds(30, 150, 170, 100);
        price.setFont(new Font("calibri", Font.BOLD, 15));
        price.setForeground(Color.gray);
        price.setFocusable(false);
        shares.setBounds(30, 215, 170, 100);
        shares.setFont(new Font("calibri", Font.BOLD, 15));
        shares.setForeground(Color.gray);
        shares.setFocusable(false);
        bg.add(shares);
        bg.add(price);

        if (stock != null)
            resetStocks();
    }

    //reset functions
    public void resetRadio() {
        if (!radio1.isSelected() && !radio2.isSelected())
            radio1.setSelected(true);
    }

    public void resetEarned() {
        name.setText("Total Earned");
        name.setForeground(Color.GRAY);
        HashMap<Currency, Double> moneyEarned = bank.calculateMoneyEarned();
        int i = 0;
        for (Currency currency : Currency.values()) {
            labels[i].setText(currencySign[i] + moneyEarned.get(currency));
            i++;
        }
    }

    public void resetBalance() {
        resetName();
        int i = 0;
        for (Currency currency : Currency.values()) {
            labels[i].setText(currencySign[i] + account.getDeposit(currency));
            i++;
        }
        setTitle(account.getType().toString() + " - ●●●●" + account.getNumber().substring(8));
    }

    public void resetName() {
        name.setText(customer.getUsername());
    }

    public void resetStocks() {
        if (!panelName.equals("sellstocks") && !panelName.equals("buystocks"))
            setTitle(stock.getName() + "  " + format.format(date));
        name.setText(stock.getName());
        labels[0].setText("$" + stock.getUSDPrice());
        labels[1].setText(String.valueOf(CommonMathMethod.bigDecimalMultiply(stock.getChange(), 100.0)) + "%");
        labels[2].setText(String.valueOf(stock.getShares()));
        if (stock.getChange() > 0)
            labels[1].setForeground(Color.decode("#32CD32"));
        else if (stock.getChange() < 0)
            labels[1].setForeground(Color.decode("#EE5C42"));
        else {
            labels[1].setText("0.00%");
            labels[1].setForeground(Color.gray);
        }
    }

    public boolean quickVerify() {//verify password
        JPasswordField jPasswordFieldPassword = new JPasswordField(15);
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("Password: "));
        jPanel.add(jPasswordFieldPassword);
        String dialogTitle;
        switch (panelName) {
            case "select":
                dialogTitle = "View Account";
                break;
            case "deposit":
                dialogTitle = "Quick Deposit";
                break;
            case "withdraw":
                dialogTitle = "Quick Withdraw";
                break;
            case "sellstocks":
                dialogTitle = "Sell Stocks";
                break;
            default:
                dialogTitle = "";
        }
        int result = JOptionPane.showConfirmDialog(null, jPanel,
                dialogTitle, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String password = String.valueOf(jPasswordFieldPassword.getPassword());
            if (password.length() == 0) {
                noPWMsg();
            } else if (account.isPasswordRight(password))
                return true;
            else
                wrongPWMsg();
        }
        return false;
    }

    //events
    public void addEvent(ActionEvent e) {
        super.addEvent(e);
    }

    public void reset() {//update data
        if (panelName.equals("main") || panelName.equals("loans")
                || panelName.equals("transfer") || panelName.equals("openopt")
                || panelName.equals("select") || panelName.equals("security")
                || panelName.equals("stocks") || panelName.equals("securitytrans"))
            resetName();
        else if (panelName.equals("deposit") || panelName.equals("withdraw")
                || panelName.equals("transactions") || panelName.equals("view")
                || panelName.equals("viewloans"))
            resetBalance();
        else if (panelName.equals("manager") || panelName.equals("daily")
                || panelName.equals("accounts") || panelName.equals("viewstocks"))
            resetEarned();
        if (panelName.equals("stocks") || panelName.equals("loans")
                || panelName.equals("accounts"))
            resetRadio();
        if (panelName.equals("buystocks") || panelName.equals("sellstocks")
                || panelName.equals("setstocks")) 
            resetStocks();
    }

    public void forward() {
        super.forward();
    }

    public void noAccountMsg() {
        JOptionPane.showMessageDialog(null,
                "Please select an account!"
                , "Request Failed", JOptionPane.ERROR_MESSAGE);
    }

    public void requestFailMsg() {
        JOptionPane.showMessageDialog(null,
                "Invalid input!",
                "Request Failed", JOptionPane.ERROR_MESSAGE);
    }

    public void wrongPWMsg() {
        JOptionPane.showMessageDialog(null,
                "Wrong password!",
                "Request Failed", JOptionPane.ERROR_MESSAGE);
    }

    public void noInputMsg() {
        JOptionPane.showMessageDialog(null,
                "Please input an amount!",
                "Request Failed", JOptionPane.ERROR_MESSAGE);
    }

    public void noPWMsg() {
        JOptionPane.showMessageDialog(null,
                "Please input password!",
                "Request Failed", JOptionPane.ERROR_MESSAGE);
    }

    public void noMoneyMsg() {
        JOptionPane.showMessageDialog(null,
                "No enough money!",
                "Request Failed", JOptionPane.ERROR_MESSAGE);
    }

    public void noStockMsg() {
        JOptionPane.showMessageDialog(null,
                "Please select a stock!",
                "Request Failed", JOptionPane.ERROR_MESSAGE);
    }
}