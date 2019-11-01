import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * This class is for customer or banker to login.
 */

public class LoginInterface extends JFrame {

    private Bank bank;

    private Date date;

    private JPasswordField jPasswordFieldPassword;

    private JTextField jTextFieldUsername;

    private JRadioButton jRadioButtonCustomer, jRadioButtonBanker;

    public LoginInterface(Bank bank, Date date) {

        this.bank = bank;

        this.date = date;

        this.setTitle("User Login");

        inputUsername();

        inputPassword();

        setButtons();

        jurisdiction();

        setSize(WelcomeInterface.WIDTH, WelcomeInterface.HEIGHT);

        setLayout(null);

        setLocationRelativeTo(null);

        this.setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void inputUsername() {
        JLabel jLabelUserName = new JLabel("username");
        jLabelUserName.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabelUserName.setBounds(100, 100, 100, 30);
        getContentPane().add(jLabelUserName);
        jTextFieldUsername = new JTextField(15);
        jTextFieldUsername.setBounds(100, 140, 600, 35);
        jTextFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
        getContentPane().add(jTextFieldUsername);
    }

    private void inputPassword() {
        JLabel jLabelPassword = new JLabel("password");
        jLabelPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabelPassword.setBounds(100, 190, 100, 30);
        getContentPane().add(jLabelPassword);
        jPasswordFieldPassword = new JPasswordField(15);
        jPasswordFieldPassword.setEchoChar('*');
        jPasswordFieldPassword.setBounds(100, 230, 600, 35);
        jPasswordFieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        getContentPane().add(jPasswordFieldPassword);
    }

    private void startLogin() {
        if (!jRadioButtonCustomer.isSelected() && !jRadioButtonBanker.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please select user type!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String username = String.valueOf(jTextFieldUsername.getText());
            String password = String.valueOf(jPasswordFieldPassword.getPassword());
            if (username.length() == 0 || password.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username or password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean loginSuccess = false;
                boolean wrongPassword = false;
                if (jRadioButtonCustomer.isSelected()) {
                    for (Customer customer : bank.getCustomers()) {
                        if (customer.getUsername().equals(username)) {
                            if (customer.getPassword().equals(password)) {
                                loginSuccess = true;
                                JOptionPane.showMessageDialog(null, "Login success!");
                                new CustomerInterface(bank, customer, date);
                                dispose();
                            } else {
                                wrongPassword = true;
                                JOptionPane.showMessageDialog(null, "Wrong password!", "Error", JOptionPane.ERROR_MESSAGE);
                                jPasswordFieldPassword.setText("");
                            }
                        }
                    }
                    if (!loginSuccess && !wrongPassword) {
                        Object[] options = {"Register", "Try again"};
                        int response = JOptionPane.showOptionDialog(null, "Customer not exists!", "Customer not exists", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                        if (response == 0) {
                            new RegisterInterface(bank, date);
                            dispose();
                        } else if (response == 1) {
                            jTextFieldUsername.setText("");
                            jPasswordFieldPassword.setText("");
                        }
                    }
                } else {
                    if (username.equals("admin") && password.equals("admin")) {
                        new BankerInterface(bank, date);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                        jTextFieldUsername.setText("");
                        jPasswordFieldPassword.setText("");
                    }
                }
            }
        }
    }

    private void setButtons() {
        JButton jButtonBack = new JButton("Back");
        JButton jButtonLogin = new JButton("Login");
        JButton jButtonReset = new JButton("Reset");
        jButtonBack.setBounds(100, 20, 100, 50);
        jButtonLogin.setBounds(220, 390, 150, 70);
        jButtonReset.setBounds(440, 390, 150, 70);
        jButtonLogin.setForeground(Color.BLUE);
        jButtonReset.setForeground(Color.RED);
        getContentPane().add(jButtonBack);
        getContentPane().add(jButtonLogin);
        getContentPane().add(jButtonReset);

        jButtonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WelcomeInterface(bank, date);
                dispose();
            }
        });

        jButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startLogin();
            }
        });

        jButtonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextFieldUsername.setText("");
                jPasswordFieldPassword.setText("");
            }
        });
    }

    public void jurisdiction() {
        JLabel jLabelJurisdiction = new JLabel("user type");
        jLabelJurisdiction.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabelJurisdiction.setBounds(100, 300, 300, 30);
        getContentPane().add(jLabelJurisdiction);
        jRadioButtonCustomer = new JRadioButton("Customer");
        jRadioButtonCustomer.setFont(new Font("Tahoma", Font.PLAIN, 16));
        jRadioButtonCustomer.setSelected(true);
        jRadioButtonCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jRadioButtonBanker.setSelected(false);
            }
        });
        jRadioButtonCustomer.setBounds(230, 300, 100, 30);
        getContentPane().add(jRadioButtonCustomer);

        jRadioButtonBanker = new JRadioButton("Banker");
        jRadioButtonBanker.setFont(new Font("Tahoma", Font.PLAIN, 16));
        jRadioButtonBanker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jRadioButtonCustomer.setSelected(false);
            }
        });
        jRadioButtonBanker.setBounds(380, 300, 80, 30);
        getContentPane().add(jRadioButtonBanker);
    }
}

