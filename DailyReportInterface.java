import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

/**
 * This class is for banker to see the profits.
 */

public class DailyReportInterface extends JFrame {

    private Bank bank;

    private Date date;

    public DailyReportInterface(Bank bank, Date date) {
        this.bank = bank;
        this.date = date;

        showTotalEarned();

        setButtons();

        showTransactions();

        setTitle("Earned Money");

        setSize(WelcomeInterface.WIDTH, WelcomeInterface.HEIGHT);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void showTotalEarned() {
        JLabel jLabelTotalEarned = new JLabel("Total Earned");
        jLabelTotalEarned.setBounds(330, 80, 300, 40);
        jLabelTotalEarned.setFont(new Font("Tamaho", Font.BOLD, 22));
        getContentPane().add(jLabelTotalEarned);
        HashMap<Currency, Double> moneyEarned = bank.calculateMoneyEarned();
        int x = 160;
        for (Currency currency : Currency.values()) {
            JLabel jLabelCurrency = new JLabel("Currency: " + currency);
            JLabel jLabelMoneyEarned = new JLabel("Balance: " + moneyEarned.get(currency));
            jLabelCurrency.setFont(new Font("Tamaho", Font.BOLD, 16));
            jLabelMoneyEarned.setFont(new Font("Tamaho", Font.BOLD, 16));
            jLabelMoneyEarned.setForeground(Color.BLUE);
            jLabelCurrency.setForeground(Color.BLUE);
            jLabelCurrency.setBounds(x, 130, 500, 60);
            jLabelMoneyEarned.setBounds(x, 160, 500, 60);
            x += 200;
            getContentPane().add(jLabelCurrency);
            getContentPane().add(jLabelMoneyEarned);
        }
    }

    private void showTransactions() {
        JTextArea jTextAreaTransactions = new JTextArea();
        ArrayList<Transaction> transactions = bank.getBankerTransactions();
        transactions.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        for (int i = transactions.size() - 1; i >= 0; i--) {
            jTextAreaTransactions.append(transactions.get(i).toString(bank));
            jTextAreaTransactions.append("\n");
        }
        jTextAreaTransactions.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(jTextAreaTransactions);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setBounds(160, 230, 520, 230);
        getContentPane().add(jScrollPane);
    }

    private void setButtons() {
        JButton jButtonBack = new JButton("Back");
        jButtonBack.setBounds(100, 20, 100, 50);
        getContentPane().add(jButtonBack);

        jButtonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BankerInterface(bank, date);
                dispose();
            }
        });
    }
}
