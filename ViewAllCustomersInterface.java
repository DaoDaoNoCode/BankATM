import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * This class is for banker to check all customers and those loan accounts.
 */

public class ViewAllCustomersInterface extends JFrame {

    private Bank bank;

    private Date date;

    private JComboBox<Customer> jComboBoxCustomer, jComboBoxLoanCustomer;

    private DefaultComboBoxModel<Account> model, model0;

    private JComboBox<Account> jComboBoxAccounts, jComboBoxLoanAccounts;

    private JLabel[] jLabelBalance, jLabelLoan, jLabelLoanInterest;

    private JTextArea jTextAreaTransactions;

    private JTabbedPane jTabbedPane;

    private JPanel panel1, panel2;

    public ViewAllCustomersInterface(Bank bank, Date date) {
        this.bank = bank;
        this.date = date;
        this.jLabelBalance = new JLabel[Currency.values().length];
        this.jLabelLoan = new JLabel[Currency.values().length];
        this.jLabelLoanInterest = new JLabel[Currency.values().length];
        jTextAreaTransactions = new JTextArea();
        jTabbedPane = new JTabbedPane();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel1.setLayout(null);
        panel2.setLayout(null);

        jTabbedPane.addTab("all customers", panel1);

        jTabbedPane.addTab("loan customers", panel2);

        jTabbedPane.setBounds(0, 0, WelcomeInterface.WIDTH, WelcomeInterface.HEIGHT);

        showCustomers();

        showAccounts((Customer) jComboBoxCustomer.getSelectedItem());

        showLoanAccounts((Customer) jComboBoxLoanCustomer.getSelectedItem());

        showInfo();

        showTransactions();

        showLoans();

        setButtons();

        setTitle("View All Customers");

        getContentPane().add(jTabbedPane);

        setSize(WelcomeInterface.WIDTH, WelcomeInterface.HEIGHT);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);

    }

    private void showCustomers() {
        JLabel jLabelUsers = new JLabel("Customer: ");
        jLabelUsers.setBounds(100, 80, 300, 40);
        jLabelUsers.setFont(new Font("Tahoma", Font.BOLD, 18));
        panel1.add(jLabelUsers);

        jComboBoxCustomer = new JComboBox<>();
        jComboBoxCustomer.setBounds(210, 80, 250, 40);
        panel1.add(jComboBoxCustomer);

        if (bank.getCustomers().size() != 0) {
            for (Customer customer : bank.getCustomers()) {
                jComboBoxCustomer.addItem(customer);
            }
            jComboBoxCustomer.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.DESELECTED)
                        updateAccounts((Customer) jComboBoxCustomer.getSelectedItem());
                }
            });
        }

        JLabel jLabelLoanCustomers = new JLabel("Customer: ");
        jLabelLoanCustomers.setBounds(100, 80, 300, 40);
        jLabelLoanCustomers.setFont(new Font("Tahoma", Font.BOLD, 18));
        panel2.add(jLabelLoanCustomers);

        jComboBoxLoanCustomer = new JComboBox<>();
        jComboBoxLoanCustomer.setBounds(210, 80, 250, 40);
        panel2.add(jComboBoxLoanCustomer);

        if (bank.getCustomers().size() != 0) {
            for (Customer customer : bank.getCustomers()) {
                if (customer.findLoanAccounts().size() != 0) {
                    jComboBoxLoanCustomer.addItem(customer);
                }
            }
            jComboBoxLoanCustomer.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.DESELECTED)
                        updateAccounts((Customer) jComboBoxLoanCustomer.getSelectedItem());
                }
            });
        }
    }

    private void showAccounts(Customer customer) {
        JLabel jLabelAccounts = new JLabel("Account: ");
        jLabelAccounts.setBounds(100, 140, 300, 40);
        jLabelAccounts.setFont(new Font("Tahoma", Font.BOLD, 18));
        panel1.add(jLabelAccounts);
        model = new DefaultComboBoxModel<>();
        updateAccounts(customer);
        jComboBoxAccounts = new JComboBox<>(model);
        jComboBoxAccounts.setBounds(210, 140, 250, 40);
        panel1.add(jComboBoxAccounts);
    }

    private void showLoanAccounts(Customer customer) {
        JLabel jLabelAccounts = new JLabel("Account: ");
        jLabelAccounts.setBounds(100, 140, 300, 40);
        jLabelAccounts.setFont(new Font("Tahoma", Font.BOLD, 18));
        panel2.add(jLabelAccounts);
        model0 = new DefaultComboBoxModel<>();
        updateLoanAccounts(customer);
        jComboBoxLoanAccounts = new JComboBox<>(model0);
        jComboBoxLoanAccounts.setBounds(210, 140, 250, 40);
        panel2.add(jComboBoxLoanAccounts);
    }

    private void updateAccounts(Customer customer) {
        model.removeAllElements();
        if (customer != null) {
            if (customer.getCheckingAccounts().size() != 0) {
                for (Account account : customer.getCheckingAccounts()) {
                    model.addElement(account);
                }
            }
            if (customer.getSavingsAccounts().size() != 0) {
                for (Account account : customer.getSavingsAccounts()) {
                    model.addElement(account);
                }
            }
        }
    }

    private void updateLoanAccounts(Customer customer) {
        model0.removeAllElements();
        if (customer != null) {
            if (customer.findLoanAccounts().size() != 0) {
                for (Account account : customer.findLoanAccounts()) {
                    model0.addElement(account);
                }
            }
        }
    }

    private void setButtons() {
        JButton jButtonBack1 = new JButton("Back");
        JButton jButtonBack2 = new JButton("Back");
        jButtonBack1.setBounds(100, 20, 100, 50);
        jButtonBack2.setBounds(100, 20, 100, 50);
        panel1.add(jButtonBack1);
        panel2.add(jButtonBack2);

        jButtonBack1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BankerInterface(bank, date);
                dispose();
            }
        });
        jButtonBack2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BankerInterface(bank, date);
                dispose();
            }
        });
    }

    private void showInfo() {
        Account account = (Account) jComboBoxAccounts.getSelectedItem();
        int x = 100;
        if (account != null) {
            for (int i = 0; i < Currency.values().length; i++) {
                JLabel[] jLabelCurrency;
                jLabelCurrency = new JLabel[Currency.values().length];
                jLabelCurrency[i] = new JLabel("Currency: " + Currency.values()[i]);
                jLabelBalance[i] = new JLabel("Balance: " + account.getDeposit(Currency.values()[i]));
                jLabelCurrency[i].setFont(new Font("Tamaho", Font.BOLD, 16));
                jLabelBalance[i].setFont(new Font("Tamaho", Font.BOLD, 16));
                jLabelBalance[i].setForeground(Color.BLUE);
                jLabelCurrency[i].setForeground(Color.BLUE);
                jLabelCurrency[i].setBounds(x, 200, 500, 60);
                jLabelBalance[i].setBounds(x, 240, 500, 60);
                x += 220;
                panel1.add(jLabelCurrency[i]);
                panel1.add(jLabelBalance[i]);
            }
            jComboBoxAccounts.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    renewCustomer((Account) jComboBoxAccounts.getSelectedItem());
                }
            });
        } else {
            JLabel jLabelNoAccount = new JLabel("No account!");
            jLabelNoAccount.setBounds(300, 300, 500, 60);
            jLabelNoAccount.setFont(new Font("Tahoma", Font.BOLD, 30));
            jLabelNoAccount.setForeground(Color.RED);
            panel1.add(jLabelNoAccount);
        }
    }

    private void renewCustomer(Account account) {
        for (int i = 0; i < Currency.values().length; i++) {
            jLabelBalance[i].setText("Balance: " + account.getDeposit(Currency.values()[i]));
        }
        ArrayList<Transaction> transactions = account.getTransactions();
        transactions.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        jTextAreaTransactions.setText("");
        for (int i = transactions.size() - 1; i >= 0; i--) {
            jTextAreaTransactions.append(transactions.get(i).toString());
            jTextAreaTransactions.append("\n");
        }
    }

    private void showTransactions() {
        Account account = (Account) jComboBoxAccounts.getSelectedItem();
        if (account != null) {
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
            jScrollPane.setBounds(100, 330, 560, 130);
            panel1.add(jScrollPane);
        }
    }

    private void showLoans() {
        SavingsAccount account = (SavingsAccount) jComboBoxLoanAccounts.getSelectedItem();
        if (account != null) {
            int x = 100;
            for (int i = 0; i < Currency.values().length; i++) {
                JLabel[] jLabelCurrency;
                jLabelCurrency = new JLabel[Currency.values().length];
                jLabelCurrency[i] = new JLabel("Currency: " + Currency.values()[i]);
                jLabelLoan[i] = new JLabel("Loan: " + account.getLoan(Currency.values()[i]));
                jLabelLoanInterest[i] = new JLabel("Interest: " + account.getLoanInterest(Currency.values()[i]));
                jLabelCurrency[i].setFont(new Font("Tamaho", Font.BOLD, 16));
                jLabelLoan[i].setFont(new Font("Tamaho", Font.BOLD, 16));
                jLabelLoanInterest[i].setFont(new Font("Tamaho", Font.BOLD, 16));
                jLabelCurrency[i].setForeground(Color.BLUE);
                jLabelLoan[i].setForeground(Color.BLUE);
                jLabelLoanInterest[i].setForeground(Color.BLUE);
                jLabelCurrency[i].setBounds(x, 200, 500, 60);
                jLabelLoan[i].setBounds(x, 240, 500, 60);
                jLabelLoanInterest[i].setBounds(x, 280, 500, 60);
                panel2.add(jLabelCurrency[i]);
                panel2.add(jLabelLoan[i]);
                panel2.add(jLabelLoanInterest[i]);
                x += 220;
            }
            jComboBoxAccounts.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    renewLoan((SavingsAccount) jComboBoxAccounts.getSelectedItem());
                }
            });
        } else {
            JLabel jLabelNoLoan = new JLabel("No loan!");
            jLabelNoLoan.setBounds(320, 300, 500, 60);
            jLabelNoLoan.setFont(new Font("Tahoma", Font.BOLD, 30));
            jLabelNoLoan.setForeground(Color.RED);
            panel2.add(jLabelNoLoan);
        }
    }

    private void renewLoan(SavingsAccount account) {
        for (int i = 0; i < Currency.values().length; i++) {
            jLabelLoan[i].setText("Loan: " + account.getLoan(Currency.values()[i]));
            jLabelLoanInterest[i].setText("Interest: " + account.getLoanInterest(Currency.values()[i]));
        }
    }
}
