import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the welcome interface while opening the program.
 */

public class WelcomeInterface extends JFrame {

    static public int WIDTH = 800;

    static public int HEIGHT = 600;

    private Bank bank;

    private Date date;

    public WelcomeInterface(Bank bank, Date date) {

        this.bank = bank;

        this.date = date;

        setWelcomeLabel();

        setDateLabel();

        setButtons();

        setTitle("My fancy bank");

        setSize(WIDTH, HEIGHT);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void setWelcomeLabel() {
        JLabel jLabelWelcome = new JLabel("Welcome to Juntao's bank!");
        jLabelWelcome.setFont(new Font("Tahoma", Font.BOLD, 30));
        jLabelWelcome.setForeground(Color.RED);
        jLabelWelcome.setBounds(200, 100, 600, 100);
        getContentPane().add(jLabelWelcome);
    }

    private void setDateLabel() {
        JLabel jLabelDate = new JLabel("Today is ");
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        JLabel jLabelTime = new JLabel(format.format(date));
        jLabelDate.setFont(new Font("Tahoma", Font.BOLD, 30));
        jLabelTime.setFont(new Font("Tahoma", Font.BOLD, 30));
        jLabelTime.setForeground(Color.BLUE);
        jLabelDate.setBounds(240, 200, 400, 100);
        jLabelTime.setBounds(390, 200, 400, 100);
        getContentPane().add(jLabelDate);
        getContentPane().add(jLabelTime);
    }

    private void setButtons() {
        JButton jButtonLogin = new JButton("User Login");
        JButton jButtonRegister = new JButton("Customer Register");
        jButtonLogin.setBounds(240, 350, 150, 50);
        jButtonRegister.setBounds(420, 350, 150, 50);
        getContentPane().add(jButtonLogin);
        getContentPane().add(jButtonRegister);

        jButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginInterface(bank, date);
                dispose();
            }
        });

        jButtonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterInterface(bank, date);
                dispose();
            }
        });
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
