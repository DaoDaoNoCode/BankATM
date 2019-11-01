import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is the banker interface.
 */

public class BankerInterface extends JFrame {

    private Bank bank;

    private Calendar calendar;

    public BankerInterface(Bank bank, Date date) {
        this.bank = bank;
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(date);

        showBakerInfo();

        setButtons();

        setTitle("Banker Interface");

        setSize(WelcomeInterface.WIDTH, WelcomeInterface.HEIGHT);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void showBakerInfo() {
        JLabel jLabelBanker = new JLabel("Hello! Banker");
        jLabelBanker.setBounds(290, 40, 600, 100);
        jLabelBanker.setFont(new Font("Tahoma", Font.BOLD, 30));
        this.getContentPane().add(jLabelBanker);
    }

    public void setButtons() {
        JButton jButtonAddOneDay = new JButton("Add One Day");
        JButton jButtonAddThreeDay = new JButton("Add Three Days");
        JButton jButtonShowMoneyEarned = new JButton("Show Money Earned");
        JButton jButtonViewAllCustomers = new JButton("View All Customer");
        JButton jButtonLogOut = new JButton("Log Out");
        jButtonAddOneDay.setBounds(100, 150, 250, 80);
        jButtonAddThreeDay.setBounds(450, 150, 250, 80);
        jButtonShowMoneyEarned.setBounds(100, 280, 250, 80);
        jButtonViewAllCustomers.setBounds(450, 280, 250, 80);
        jButtonLogOut.setBounds(250, 410, 300, 80);
        jButtonLogOut.setForeground(Color.RED);
        getContentPane().add(jButtonLogOut);
        getContentPane().add(jButtonAddOneDay);
        getContentPane().add(jButtonAddThreeDay);
        getContentPane().add(jButtonShowMoneyEarned);
        getContentPane().add(jButtonViewAllCustomers);

        jButtonLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginInterface(bank, calendar.getTime());
            }
        });
        jButtonAddOneDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.DATE, 1);
                bank.calculateLoanInterest(calendar.getTime());
                bank.addSaveInterest(1, calendar.getTime());
            }
        });
        jButtonAddThreeDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.DATE, 3);
                bank.calculateLoanInterest(calendar.getTime());
                bank.addSaveInterest(3, calendar.getTime());
            }
        });
        jButtonShowMoneyEarned.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DailyReportInterface(bank, calendar.getTime());
                dispose();
            }
        });
        jButtonViewAllCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewAllCustomersInterface(bank, calendar.getTime());
                dispose();
            }
        });
    }

}
