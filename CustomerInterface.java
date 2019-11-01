import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * This is the main interface of customer, in which they can choose the action.
 */

public class CustomerInterface extends JFrame {

    private Bank bank;

    private Customer customer;

    private Date date;

    public CustomerInterface(Bank bank, Customer customer, Date date) {
        this.bank = bank;
        this.customer = customer;
        this.date = date;

        showUserInfo();

        setButtons();

        setTitle("Customer Interface");

        setSize(WelcomeInterface.WIDTH, WelcomeInterface.HEIGHT);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void showUserInfo() {
        JLabel jLabelUserName = new JLabel("Hello! " + customer.getUsername());
        jLabelUserName.setBounds(100, 40, 600, 100);
        jLabelUserName.setFont(new Font("Tahoma", Font.BOLD, 30));
        this.getContentPane().add(jLabelUserName);
    }

    private void setButtons() {
        JButton jButtonOpenAccount = new JButton("Open Account");
        JButton jButtonViewAccount = new JButton("View My Accounts");
        JButton jButtonTransfer = new JButton("Make a Transfer");
        JButton jButtonLoan = new JButton("Request a Loan");
        JButton jButtonLogOut = new JButton("Log Out");
        jButtonOpenAccount.setBounds(100, 150, 250, 80);
        jButtonViewAccount.setBounds(450, 150, 250, 80);
        jButtonTransfer.setBounds(100, 280, 250, 80);
        jButtonLoan.setBounds(450, 280, 250, 80);
        jButtonLogOut.setBounds(250, 410, 300, 80);
        jButtonLogOut.setForeground(Color.RED);
        getContentPane().add(jButtonOpenAccount);
        getContentPane().add(jButtonViewAccount);
        getContentPane().add(jButtonTransfer);
        getContentPane().add(jButtonLoan);
        getContentPane().add(jButtonLogOut);

        jButtonOpenAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAccount();
            }
        });

        jButtonViewAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAccounts();
            }
        });

        jButtonTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransferInterface(bank, customer, date);
                dispose();
            }
        });

        jButtonLoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoanInterface(bank, customer, date);
                dispose();
            }
        });

        jButtonLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginInterface(bank, date);
                dispose();
            }
        });
    }

    private void viewAccounts() {
        if (customer.getCheckingAccounts().size() == 0 && customer.getSavingsAccounts().size() == 0) {
            Object[] options = {"Open an account"};
            int response = JOptionPane.showOptionDialog(null, "No account exists!", "No account", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (response == 0) {
                openAccount();
            }
        } else {
            new ViewAccountInterface(bank, customer, date);
            dispose();
        }
    }

    private void openAccount() {
        new OpenAccountInterface(bank, customer, date);
        dispose();
    }
}
