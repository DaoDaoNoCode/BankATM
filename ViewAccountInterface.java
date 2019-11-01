import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * This is the interface where customer can find all their accounts, and they can choose one to view.
 */

public class ViewAccountInterface extends JFrame {

    private Bank bank;

    private Customer customer;

    private Date date;

    private JComboBox<Account> jComboBoxAccounts;

    public ViewAccountInterface(Bank bank, Customer customer, Date date) {
        this.bank = bank;
        this.customer = customer;
        this.date = date;

        showUserAccounts();

        setButtons();

        setTitle("View Account");

        setSize(WelcomeInterface.WIDTH, WelcomeInterface.HEIGHT);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);

    }

    private void showUserAccounts() {
        JLabel jLabelAccounts = new JLabel("Please choose an account:");
        jLabelAccounts.setBounds(220, 60, 600, 80);
        jLabelAccounts.setFont(new Font("Tahoma", Font.BOLD, 28));
        getContentPane().add(jLabelAccounts);
        jComboBoxAccounts = new JComboBox<>();
        if (customer.getCheckingAccounts().size() != 0) {
            for (Account account : customer.getCheckingAccounts()) {
                jComboBoxAccounts.addItem(account);
            }
        }
        if (customer.getSavingsAccounts().size() != 0) {
            for (Account account : customer.getSavingsAccounts()) {
                jComboBoxAccounts.addItem(account);
            }
        }
        jComboBoxAccounts.setBounds(220, 200, 360, 40);
        getContentPane().add(jComboBoxAccounts);
    }

    private void setButtons() {
        JButton jButtonBack = new JButton("Back");
        JButton jButtonView = new JButton("View");
        jButtonBack.setBounds(100, 20, 100, 50);
        jButtonView.setBounds(300, 350, 200, 80);
        jButtonView.setForeground(Color.BLUE);
        getContentPane().add(jButtonBack);
        getContentPane().add(jButtonView);

        jButtonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerInterface(bank, customer, date);
                dispose();
            }
        });

        jButtonView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAccount();
            }
        });
    }

    private void viewAccount() {
        Account account = (Account) jComboBoxAccounts.getSelectedItem();
        if (account != null) {
            new AccountInterface(bank, customer, account, date);
            dispose();
        } else {
            Object[] options = {"Go to open an account"};
            int response = JOptionPane.showOptionDialog(null, "You have no account!", "No account", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (response == 0) {
                new OpenAccountInterface(bank, customer, date);
                dispose();
            }
        }

    }
}
