/**
 * Customer register.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * This class is used for customer registering.
 */

public class RegisterInterface extends JFrame {

    private Bank bank;

    private Date date;

    private JPasswordField jPasswordFieldPassword;

    private JPasswordField jPasswordFieldPassword2;

    private JTextField jTextFieldUsername;

    public RegisterInterface(Bank bank, Date date) {

        this.bank = bank;

        this.date = date;

        inputUsername();

        inputPassword();

        repeatPassword();

        setButtons();

        setTitle("Customer Register");

        setSize(WelcomeInterface.WIDTH, WelcomeInterface.HEIGHT);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void inputUsername() {
        JLabel jLabelUserName = new JLabel("username");
        jLabelUserName.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabelUserName.setBounds(100, 80, 100, 30);
        getContentPane().add(jLabelUserName);
        jTextFieldUsername = new JTextField(15);
        jTextFieldUsername.setBounds(100, 120, 600, 35);
        jTextFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
        getContentPane().add(jTextFieldUsername);
    }

    private void inputPassword() {
        JLabel jLabelPassword = new JLabel("password");
        jLabelPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabelPassword.setBounds(100, 170, 100, 30);
        getContentPane().add(jLabelPassword);
        jPasswordFieldPassword = new JPasswordField(15);
        jPasswordFieldPassword.setEchoChar('*');
        jPasswordFieldPassword.setBounds(100, 210, 600, 35);
        jPasswordFieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        getContentPane().add(jPasswordFieldPassword);
    }

    private void repeatPassword() {
        JLabel jLabelPassword2 = new JLabel("repeat password");
        jLabelPassword2.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabelPassword2.setBounds(100, 260, 300, 30);
        getContentPane().add(jLabelPassword2);
        jPasswordFieldPassword2 = new JPasswordField(15);
        jPasswordFieldPassword2.setEchoChar('*');
        jPasswordFieldPassword2.setBounds(100, 300, 600, 35);
        jPasswordFieldPassword2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        getContentPane().add(jPasswordFieldPassword2);
    }

    private void setButtons() {
        JButton jButtonBack = new JButton("Back");
        JButton jButtonRegister = new JButton("Register");
        jButtonBack.setBounds(100, 20, 100, 50);
        jButtonRegister.setBounds(300, 390, 200, 80);
        jButtonRegister.setForeground(Color.BLUE);
        getContentPane().add(jButtonBack);
        getContentPane().add(jButtonRegister);

        jButtonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        jButtonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WelcomeInterface(bank, date);
                dispose();
            }
        });
    }

    private void register() {
        String username = String.valueOf(jTextFieldUsername.getText());
        String password = String.valueOf(jPasswordFieldPassword.getPassword());
        String password2 = String.valueOf(jPasswordFieldPassword2.getPassword());
        boolean nameIsUsed = false;
        if (username.length() == 0 || password.length() == 0 || password2.length() == 0) {
            JOptionPane.showMessageDialog(null, "Username or password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            jTextFieldUsername.setText("");
            jPasswordFieldPassword.setText("");
            jPasswordFieldPassword2.setText("");
        } else {
            for (Customer customer : bank.getCustomers()) {
                if (customer.getUsername().equals(username)) {
                    JOptionPane.showMessageDialog(null, "This username has been used!", "Error", JOptionPane.ERROR_MESSAGE);
                    jTextFieldUsername.setText("");
                    jPasswordFieldPassword.setText("");
                    jPasswordFieldPassword2.setText("");
                    nameIsUsed = true;
                }
            }
            if (!nameIsUsed) {
                if (!password.equals(password2)) {
                    JOptionPane.showMessageDialog(null, "Repeat password not the same!", "Error", JOptionPane.ERROR_MESSAGE);
                    jPasswordFieldPassword.setText("");
                    jPasswordFieldPassword2.setText("");
                } else {
                    bank.registerCustomer(new Customer(bank, username, password));
                    Object[] options = {"Go to login"};
                    int response = JOptionPane.showOptionDialog(null, "Register success!", "Register success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                    if (response == 0) {
                        new LoginInterface(bank, date);
                        dispose();
                    }
                }
            }
        }
    }
}
