import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

/**
 * This is the interface of transfer between customers.
 */

public class TransferInterface extends JFrame {

    private Bank bank;

    private Customer fromCustomer;

    private Date date;

    private JComboBox<Account> jComboBoxFromAccounts;

    private JComboBox<Customer> jComboBoxCustomer;

    private DefaultComboBoxModel<Account> model;

    private JComboBox<Account> jComboBoxToAccounts;

    private JComboBox<Currency> jComboBoxCurrency;

    private JTextField jTextFieldMoney;

    private JPasswordField jPasswordFieldPassword;

    public TransferInterface(Bank bank, Customer customer, Date date) {
        this.bank = bank;
        this.fromCustomer = customer;
        this.date = date;

        showFromAccounts();

        showToUsers();

        showToAccounts((Customer) jComboBoxCustomer.getSelectedItem());

        inputMoney();

        inputPassword();

        setButtons();

        setTitle("Make a Transfer");

        setSize(WelcomeInterface.WIDTH, WelcomeInterface.HEIGHT);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);

    }

    private void showFromAccounts() {
        JLabel jLabelAccounts = new JLabel("From");
        jLabelAccounts.setBounds(100, 80, 200, 40);
        jLabelAccounts.setFont(new Font("Tamaho", Font.BOLD, 18));
        getContentPane().add(jLabelAccounts);

        jComboBoxFromAccounts = new JComboBox<>();
        jComboBoxFromAccounts.setBounds(100, 110, 300, 40);
        getContentPane().add(jComboBoxFromAccounts);

        if (fromCustomer.getCheckingAccounts().size() != 0) {
            for (Account account : fromCustomer.getCheckingAccounts()) {
                jComboBoxFromAccounts.addItem(account);
            }
            jComboBoxFromAccounts.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    updateToAccounts((Customer) jComboBoxCustomer.getSelectedItem());
                }
            });
        }
    }

    private void showToUsers() {
        JLabel jLabelUsers = new JLabel("To");
        jLabelUsers.setBounds(100, 160, 200, 40);
        jLabelUsers.setFont(new Font("Tamaho", Font.BOLD, 18));
        getContentPane().add(jLabelUsers);

        jComboBoxCustomer = new JComboBox<>();
        jComboBoxCustomer.setBounds(100, 190, 200, 40);
        getContentPane().add(jComboBoxCustomer);

        if (bank.getCustomers().size() != 0) {
            for (Customer customer : bank.getCustomers()) {
                jComboBoxCustomer.addItem(customer);
            }
            jComboBoxCustomer.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.DESELECTED)
                        updateToAccounts((Customer) jComboBoxCustomer.getSelectedItem());
                }
            });
        }
    }

    private void showToAccounts(Customer toCustomer) {
        model = new DefaultComboBoxModel<>();
        updateToAccounts(toCustomer);
        jComboBoxToAccounts = new JComboBox<>(model);
        jComboBoxToAccounts.setBounds(330, 190, 300, 40);
        getContentPane().add(jComboBoxToAccounts);
    }

    private void updateToAccounts(Customer toCustomer) {
        model.removeAllElements();
        if (toCustomer != null) {
            if (toCustomer.getCheckingAccounts().size() != 0) {
                for (Account account : toCustomer.getCheckingAccounts()) {
                    if (account == jComboBoxFromAccounts.getSelectedItem())
                        continue;
                    model.addElement(account);
                }
            }
        }
    }

    private void inputMoney() {
        JLabel jLabelMoney = new JLabel("money");
        jLabelMoney.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabelMoney.setBounds(100, 240, 200, 40);
        getContentPane().add(jLabelMoney);
        jTextFieldMoney = new JTextField(10);
        jTextFieldMoney.setFont(new Font("Tahoma", Font.PLAIN, 16));
        jTextFieldMoney.setBounds(100, 280, 200, 35);
        getContentPane().add(jTextFieldMoney);
        jComboBoxCurrency = new JComboBox<>();
        jComboBoxCurrency.setBounds(330, 280, 100, 40);
        getContentPane().add(jComboBoxCurrency);
        for (Currency currency : Currency.values())
            jComboBoxCurrency.addItem(currency);

    }

    private void inputPassword() {
        JLabel jLabelPassword = new JLabel("password");
        jLabelPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabelPassword.setBounds(100, 330, 200, 40);
        getContentPane().add(jLabelPassword);
        jPasswordFieldPassword = new JPasswordField(15);
        jPasswordFieldPassword.setEchoChar('*');
        jPasswordFieldPassword.setBounds(100, 370, 560, 35);
        jPasswordFieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        getContentPane().add(jPasswordFieldPassword);
    }

    private void setButtons() {
        JButton jButtonBack = new JButton("Back");
        JButton jButtonTransfer = new JButton("Transfer");
        jButtonBack.setBounds(100, 20, 100, 50);
        jButtonTransfer.setBounds(330, 450, 150, 60);
        jButtonTransfer.setForeground(Color.BLUE);
        getContentPane().add(jButtonBack);
        getContentPane().add(jButtonTransfer);

        jButtonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerInterface(bank, fromCustomer, date);
                dispose();
            }
        });

        jButtonTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeTransfer();
            }
        });
    }

    private void makeTransfer() {
        try {
            CheckingAccount accountFrom = (CheckingAccount) jComboBoxFromAccounts.getSelectedItem();
            CheckingAccount accountTo = (CheckingAccount) jComboBoxToAccounts.getSelectedItem();
            double money = Double.valueOf(jTextFieldMoney.getText());
            Currency currency = (Currency) jComboBoxCurrency.getSelectedItem();
            String password = String.valueOf(jPasswordFieldPassword.getPassword());
            if (accountFrom == null || accountTo == null) {
                JOptionPane.showMessageDialog(null, "Account could not be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (!accountFrom.isPasswordRight(password)) {
                    JOptionPane.showMessageDialog(null, "Wrong password!", "Error", JOptionPane.ERROR_MESSAGE);
                    jPasswordFieldPassword.setText("");
                } else {
                    int transfer = accountFrom.transferOut(accountTo, money, currency, date);
                    if (transfer == -1) {
                        JOptionPane.showMessageDialog(null, "Transfer must be more than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (transfer == 0) {
                        JOptionPane.showMessageDialog(null, "No enough money!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Transfer Money Finished!", "Transfer money success", JOptionPane.INFORMATION_MESSAGE);
                        new TransferInterface(bank, fromCustomer, date);
                        dispose();
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
