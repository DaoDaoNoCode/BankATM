import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * This class is for user to request or repay a loan.
 */

public class LoanInterface extends JFrame {

    private Bank bank;

    private Date date;

    private Customer customer;

    private JComboBox<SavingsAccount> jComboBoxAccounts;

    private JLabel[] jLabelLoan, jLabelLoanInterest;

    public LoanInterface(Bank bank, Customer customer, Date date) {
        this.bank = bank;
        this.customer = customer;
        this.date = date;
        this.jLabelLoan = new JLabel[Currency.values().length];
        this.jLabelLoanInterest = new JLabel[Currency.values().length];

        showLoanInfo();

        showLoanAccounts();

        showLoan();

        setButtons();

        setTitle("Request a Loan");

        setSize(WelcomeInterface.WIDTH, WelcomeInterface.HEIGHT);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);

    }

    private void showLoanInfo() {
        JLabel jLabelLoanInfo1 = new JLabel("You can request a loan of no more than 10,000 on each currency.");
        JLabel jLabelLoanInfo2 = new JLabel("You cannot request a loan if you haven't repaid it.");
        JLabel jLabelLoanInfo3 = new JLabel("We will charge a fee at a  0.1% daily rate.");
        jLabelLoanInfo1.setBounds(200, 60, 500, 60);
        jLabelLoanInfo2.setBounds(240, 90, 500, 60);
        jLabelLoanInfo3.setBounds(280, 120, 500, 60);
        jLabelLoanInfo1.setForeground(Color.RED);
        jLabelLoanInfo2.setForeground(Color.RED);
        jLabelLoanInfo3.setForeground(Color.RED);
        getContentPane().add(jLabelLoanInfo1);
        getContentPane().add(jLabelLoanInfo2);
        getContentPane().add(jLabelLoanInfo3);
    }

    private void showLoanAccounts() {
        JLabel jLabelAccounts = new JLabel("Choose the account you want to apply a loan: ");
        jLabelAccounts.setBounds(200, 180, 500, 40);
        jLabelAccounts.setFont(new Font("Tahoma", Font.BOLD, 18));
        getContentPane().add(jLabelAccounts);

        jComboBoxAccounts = new JComboBox<>();
        jComboBoxAccounts.setBounds(250, 220, 300, 40);
        getContentPane().add(jComboBoxAccounts);

        if (customer.getSavingsAccounts().size() != 0) {
            for (Account account : customer.getSavingsAccounts()) {
                jComboBoxAccounts.addItem((SavingsAccount) account);
            }
        }
    }

    private void showLoan() {
        SavingsAccount account = (SavingsAccount) jComboBoxAccounts.getSelectedItem();
        if (account != null) {
            int x = 160;
            for (int i = 0; i < Currency.values().length; i++) {
                JLabel[] jLabelCurrency = new JLabel[Currency.values().length];
                jLabelCurrency[i] = new JLabel("Currency: " + Currency.values()[i]);
                jLabelLoan[i] = new JLabel("Loan: " + account.getLoan(Currency.values()[i]));
                jLabelLoanInterest[i] = new JLabel("Interest: " + account.getLoanInterest(Currency.values()[i]));
                jLabelCurrency[i].setFont(new Font("Tamaho", Font.BOLD, 16));
                jLabelLoan[i].setFont(new Font("Tamaho", Font.BOLD, 16));
                jLabelLoanInterest[i].setFont(new Font("Tamaho", Font.BOLD, 16));
                jLabelCurrency[i].setForeground(Color.BLUE);
                jLabelLoan[i].setForeground(Color.BLUE);
                jLabelLoanInterest[i].setForeground(Color.BLUE);
                jLabelCurrency[i].setBounds(x, 280, 500, 60);
                jLabelLoan[i].setBounds(x, 310, 500, 60);
                jLabelLoanInterest[i].setBounds(x, 340, 500, 60);
                getContentPane().add(jLabelCurrency[i]);
                getContentPane().add(jLabelLoan[i]);
                getContentPane().add(jLabelLoanInterest[i]);
                x += 200;
            }
            jComboBoxAccounts.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    renewLoan((SavingsAccount) jComboBoxAccounts.getSelectedItem());
                }
            });
        } else {
            JLabel jLabelNoSavingsAccount = new JLabel("You have no savings account!");
            jLabelNoSavingsAccount.setBounds(210, 300, 500, 60);
            jLabelNoSavingsAccount.setFont(new Font("Tahoma", Font.BOLD, 26));
            jLabelNoSavingsAccount.setForeground(Color.RED);
            getContentPane().add(jLabelNoSavingsAccount);
        }
    }

    private void renewLoan(SavingsAccount account) {
        for (int i = 0; i < Currency.values().length; i++) {
            jLabelLoan[i].setText("Loan: " + account.getLoan(Currency.values()[i]));
            jLabelLoanInterest[i].setText("Interest: " + account.getLoanInterest(Currency.values()[i]));
        }
    }

    private void setButtons() {
        JButton jButtonBack = new JButton("Back");
        JButton jButtonLoan = new JButton("Request Loan");
        JButton jButtonRepay = new JButton("Repay Loan");
        jButtonBack.setBounds(100, 20, 100, 50);
        jButtonLoan.setBounds(230, 420, 150, 60);
        jButtonRepay.setBounds(430, 420, 150, 60);
        getContentPane().add(jButtonLoan);
        getContentPane().add(jButtonRepay);
        getContentPane().add(jButtonBack);

        jButtonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerInterface(bank, customer, date);
                dispose();
            }
        });

        jButtonLoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeLoan();
            }
        });

        jButtonRepay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repayLoan();
            }
        });
    }

    private void makeLoan() {
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
        int result = JOptionPane.showConfirmDialog(null, jPanel, "Request Loan", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Currency currency = (Currency) jComboBoxCurrency.getSelectedItem();
                String password = String.valueOf(jPasswordFieldPassword.getPassword());
                SavingsAccount account = (SavingsAccount) jComboBoxAccounts.getSelectedItem();
                double money = Double.valueOf(jTextFieldMoney.getText());
                if (account == null) {
                    JOptionPane.showMessageDialog(null, "You have no savings account!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!account.isPasswordRight(password)) {
                        JOptionPane.showMessageDialog(null, "Wrong password!", "Error", JOptionPane.ERROR_MESSAGE);
                        jPasswordFieldPassword.setText("");
                    } else {
                        int loan = account.requestLoan(money, currency, date);
                        if (loan == -1) {
                            JOptionPane.showMessageDialog(null, "You need to repay your loan first!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (loan == 0) {
                            JOptionPane.showMessageDialog(null, "Loan must be between 0-10000!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Loan Finished!", "Loan success", JOptionPane.INFORMATION_MESSAGE);
                            new LoanInterface(bank, customer, date);
                            dispose();
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void repayLoan() {
        JPasswordField jPasswordFieldPassword = new JPasswordField(15);

        JComboBox<Currency> jComboBoxCurrency = new JComboBox<>();
        for (Currency currency : Currency.values())
            jComboBoxCurrency.addItem(currency);

        JPanel jPanel = new JPanel();
        jPanel.add(Box.createHorizontalStrut(15));
        jPanel.add(new JLabel("Password: "));
        jPanel.add(jPasswordFieldPassword);
        jPanel.add(Box.createHorizontalStrut(15));
        jPanel.add(new JLabel("Currency: "));
        jPanel.add(jComboBoxCurrency);
        int result = JOptionPane.showConfirmDialog(null, jPanel, "Repay Loan", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Currency currency = (Currency) jComboBoxCurrency.getSelectedItem();
                String password = String.valueOf(jPasswordFieldPassword.getPassword());
                SavingsAccount account = (SavingsAccount) jComboBoxAccounts.getSelectedItem();
                if (account == null) {
                    JOptionPane.showMessageDialog(null, "You have no savings account!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!account.isPasswordRight(password)) {
                        JOptionPane.showMessageDialog(null, "Wrong password!", "Error", JOptionPane.ERROR_MESSAGE);
                        jPasswordFieldPassword.setText("");
                    } else {
                        int repay = account.repayLoan(currency, date);
                        if (repay == -1) {
                            JOptionPane.showMessageDialog(null, "You have no loan to repay!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (repay == 0) {
                            JOptionPane.showMessageDialog(null, "Money in your saving account is not enough!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Repay Finished!", "Repay success", JOptionPane.INFORMATION_MESSAGE);
                            new LoanInterface(bank, customer, date);
                            dispose();
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
