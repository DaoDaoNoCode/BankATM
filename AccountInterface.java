import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * This class is the interface of an account.
 */

public class AccountInterface extends JFrame {

    private Bank bank;

    private Customer customer;

    private Date date;

    private Account account;

    public AccountInterface(Bank bank, Customer customer, Account account, Date date) {
        this.bank = bank;
        this.customer = customer;
        this.account = account;
        this.date = date;

        showAccountInfo();

        showDeposits();

        setButtons();

        showTransactions();

        setTitle("Account Interface");

        setSize(WelcomeInterface.WIDTH, WelcomeInterface.HEIGHT);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void showAccountInfo() {
        JLabel jLabelAccountNumber = new JLabel("Account Number: " + account.getNumber());
        JLabel jLabelAccountType = new JLabel("Account Type: " + account.getType());
        jLabelAccountNumber.setBounds(100, 80, 500, 60);
        jLabelAccountNumber.setFont(new Font("Tamaho", Font.BOLD, 20));
        jLabelAccountType.setBounds(480, 80, 300, 60);
        jLabelAccountType.setFont(new Font("Tamaho", Font.BOLD, 20));
        getContentPane().add(jLabelAccountNumber);
        getContentPane().add(jLabelAccountType);
        JLabel jLabelHint1 = new JLabel("Accounts will be charged a fee of withdrawal every time.");
        JLabel jLabelHint2 = new JLabel("(0.05% for withdrawal less than 1000, 0.1% for withdrawal equals to or more than 1000)");
        JLabel jLabelHint3 = new JLabel("Deposits that no less than 1000 will get a 0.02% daily save interest.");
        jLabelHint1.setBounds(230, 120, 500, 60);
        jLabelHint2.setBounds(150, 150, 600, 60);
        jLabelHint3.setBounds(200, 180, 600, 60);
        jLabelHint1.setForeground(Color.RED);
        jLabelHint2.setForeground(Color.RED);
        jLabelHint3.setForeground(Color.RED);
        getContentPane().add(jLabelHint1);
        getContentPane().add(jLabelHint2);
        getContentPane().add(jLabelHint3);
    }

    private void showDeposits() {
        int x = 160;
        for (Currency currency : Currency.values()) {
            JLabel jLabelCurrency = new JLabel("Currency: " + currency);
            JLabel jLabelBalance = new JLabel("Balance: " + account.getDeposit(currency));
            jLabelCurrency.setFont(new Font("Tamaho", Font.BOLD, 16));
            jLabelBalance.setFont(new Font("Tamaho", Font.BOLD, 16));
            jLabelBalance.setForeground(Color.BLUE);
            jLabelCurrency.setForeground(Color.BLUE);
            jLabelCurrency.setBounds(x, 220, 500, 60);
            jLabelBalance.setBounds(x, 250, 500, 60);
            x += 200;
            getContentPane().add(jLabelCurrency);
            getContentPane().add(jLabelBalance);
        }
    }

    private void showTransactions() {
        JTextArea jTextAreaTransactions = new JTextArea();
        ArrayList<Transaction> transactions = account.getTransactions();
        transactions.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        for (int i = transactions.size() - 1; i >= 0; i--) {
            jTextAreaTransactions.append(transactions.get(i).toString());
            jTextAreaTransactions.append("\n");
        }
        jTextAreaTransactions.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(jTextAreaTransactions);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setBounds(160, 310, 520, 130);
        getContentPane().add(jScrollPane);
    }

    private void setButtons() {
        JButton jButtonSave = new JButton("Save");
        JButton jButtonWithdraw = new JButton("Withdraw");
        JButton jButtonClose = new JButton("Close Account");
        JButton jButtonBack = new JButton("Back");
        jButtonBack.setBounds(100, 20, 100, 50);
        jButtonSave.setBounds(160, 470, 150, 40);
        jButtonWithdraw.setBounds(345, 470, 150, 40);
        jButtonClose.setBounds(530, 470, 150, 40);
        jButtonClose.setForeground(Color.RED);
        getContentPane().add(jButtonSave);
        getContentPane().add(jButtonWithdraw);
        getContentPane().add(jButtonClose);
        getContentPane().add(jButtonBack);

        jButtonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOrWithdraw(true);
            }
        });

        jButtonWithdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOrWithdraw(false);
            }
        });

        jButtonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAccount();
            }
        });

        jButtonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewAccountInterface(bank, customer, date);
                dispose();
            }
        });
    }

    /**
     * A panel to save or withdraw money.
     * @param isSave true if the action is saving money
     */
    private void saveOrWithdraw(boolean isSave) {
        JTextField jTextFieldMoney = new JTextField(15);
        JPasswordField jPasswordFieldPassword = new JPasswordField(15);

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

        int result = JOptionPane.showConfirmDialog(null, jPanel, (isSave ? "Save" : "Withdraw") + " money", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Currency currency = (Currency) jComboBoxCurrency.getSelectedItem();
            String password = String.valueOf(jPasswordFieldPassword.getPassword());
            try {
                double money = Double.valueOf(jTextFieldMoney.getText());
                if (!account.isPasswordRight(password)) {
                    JOptionPane.showMessageDialog(null, "Wrong password!", "Error", JOptionPane.ERROR_MESSAGE);
                    jPasswordFieldPassword.setText("");
                } else {
                    if (isSave) {
                        account.save(money, currency, date);
                        JOptionPane.showMessageDialog(null, "Save Money Finished!", "Save money success", JOptionPane.INFORMATION_MESSAGE);
                        new AccountInterface(bank, customer, account, date);
                        dispose();
                    } else {
                        int withdraw = account.withdraw(money, currency, date);
                        if (withdraw == -1) {
                            JOptionPane.showMessageDialog(null, "Withdrawal must be more than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (withdraw == 0) {
                            JOptionPane.showMessageDialog(null, "No enough money!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Withdraw Money Finished!", "Withdraw money success", JOptionPane.INFORMATION_MESSAGE);
                            new AccountInterface(bank, customer, account, date);
                            dispose();
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void closeAccount() {
        int response = JOptionPane.showConfirmDialog(null, "You need to pay 5 USD to close this account, do you want to close it？(We will not return the remaining balances.)", "Close account", 0);
        if (response == JOptionPane.YES_OPTION) {
            customer.closeAccount(account, date);
            JOptionPane.showMessageDialog(null, "Close account successfully!", "Close account success", JOptionPane.INFORMATION_MESSAGE);
            new CustomerInterface(bank, customer, date);
            dispose();
        }
    }
}
