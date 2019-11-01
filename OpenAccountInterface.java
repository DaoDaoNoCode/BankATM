import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * This class is for customer to open a checking or saving account.
 */

public class OpenAccountInterface extends JFrame {

    private Bank bank;

    private Customer customer;

    private Date date;

    private JRadioButton jRadioButtonChecking, jRadioButtonSavings;

    private JPasswordField jPasswordFieldPassword;

    private JPasswordField jPasswordFieldPassword2;

    public OpenAccountInterface(Bank bank, Customer customer, Date date) {
        this.bank = bank;
        this.customer = customer;
        this.date = date;

        inputPassword();
        repeatPassword();
        setButtons();
        jurisdiction();

        setTitle("Open Account");

        setSize(WelcomeInterface.WIDTH, WelcomeInterface.HEIGHT);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);

    }

    private void jurisdiction() {
        JLabel jLabelJurisdiction = new JLabel("account type");
        jLabelJurisdiction.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabelJurisdiction.setBounds(100, 300, 300, 30);
        getContentPane().add(jLabelJurisdiction);
        jRadioButtonChecking = new JRadioButton("Checking");
        jRadioButtonChecking.setFont(new Font("Tahoma", Font.PLAIN, 16));
        jRadioButtonChecking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jRadioButtonSavings.setSelected(false);
            }
        });
        jRadioButtonChecking.setBounds(250, 300, 100, 30);
        getContentPane().add(jRadioButtonChecking);

        jRadioButtonSavings = new JRadioButton("Savings");
        jRadioButtonSavings.setFont(new Font("Tahoma", Font.PLAIN, 16));
        jRadioButtonSavings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jRadioButtonChecking.setSelected(false);
            }
        });
        jRadioButtonSavings.setBounds(380, 300, 100, 30);
        getContentPane().add(jRadioButtonSavings);
    }

    private void setButtons() {
        JButton jButtonOpenAccount = new JButton("Open Account");
        JButton jButtonBack = new JButton("Back");
        jButtonBack.setBounds(100, 20, 100, 50);
        jButtonOpenAccount.setBounds(300, 390, 200, 80);
        jButtonOpenAccount.setForeground(Color.BLUE);
        getContentPane().add(jButtonBack);
        getContentPane().add(jButtonOpenAccount);

        jButtonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerInterface(bank, customer, date);
            }
        });

        jButtonOpenAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });
    }

    private void inputPassword() {
        JLabel jLabelPassword = new JLabel("password");
        jLabelPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabelPassword.setBounds(100, 100, 100, 30);
        getContentPane().add(jLabelPassword);
        jPasswordFieldPassword = new JPasswordField(15);
        jPasswordFieldPassword.setEchoChar('*');
        jPasswordFieldPassword.setBounds(100, 140, 600, 35);
        jPasswordFieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        getContentPane().add(jPasswordFieldPassword);
    }

    private void repeatPassword() {
        JLabel jLabelPassword2 = new JLabel("repeat password");
        jLabelPassword2.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabelPassword2.setBounds(100, 190, 300, 30);
        getContentPane().add(jLabelPassword2);
        jPasswordFieldPassword2 = new JPasswordField(15);
        jPasswordFieldPassword2.setEchoChar('*');
        jPasswordFieldPassword2.setBounds(100, 230, 600, 35);
        jPasswordFieldPassword2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        getContentPane().add(jPasswordFieldPassword2);
    }

    private void createAccount() {
        String password = String.valueOf(jPasswordFieldPassword.getPassword());
        String password2 = String.valueOf(jPasswordFieldPassword2.getPassword());
        if (!jRadioButtonChecking.isSelected() && !jRadioButtonSavings.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please select account type!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (password.length() == 0 || password2.length() == 0) {
                JOptionPane.showMessageDialog(null, "Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                jPasswordFieldPassword.setText("");
                jPasswordFieldPassword2.setText("");
            } else {
                if (!password.equals(password2)) {
                    JOptionPane.showMessageDialog(null, "Repeat password not the same!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (jRadioButtonChecking.isSelected()) {
                        int response = JOptionPane.showConfirmDialog(null, "You need to pay 5 USD to open an account, do you want to open it？", "Open account", 0);
                        if (response == JOptionPane.YES_OPTION) {
                            Account account = customer.openAccount(AccountType.CHECKING, password, date);
                            JOptionPane.showMessageDialog(null, "Open checking account!\nNumber is: " + account.getNumber(), "Open account success", JOptionPane.INFORMATION_MESSAGE);
                            new CustomerInterface(bank, customer, date);
                            dispose();
                        }
                    } else {
                        int response = JOptionPane.showConfirmDialog(null, "You need to pay 5 USD to open an account, do you want to open it？", "Open account", 0);
                        if (response == JOptionPane.YES_OPTION) {
                            Account account = customer.openAccount(AccountType.SAVINGS, password, date);
                            JOptionPane.showMessageDialog(null, "Open savings account!\nNumber is: " + account.getNumber(), "Open account success", JOptionPane.INFORMATION_MESSAGE);
                            new CustomerInterface(bank, customer, date);
                            dispose();
                        }
                    }
                }
            }
        }
    }
}
