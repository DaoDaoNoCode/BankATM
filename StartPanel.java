import gui.NextButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

@SuppressWarnings("serial")
public class StartPanel extends PWPanel {
    private JButton forgot = new JButton("Forgot Password?");
    private JButton open = new JButton("New Customer?");
    private JButton manager = new JButton("Manager Login");

    private AtmPanel forgotPanel;
    private AtmPanel openPanel;
    private AtmPanel bankerPanel;

    public StartPanel(AtmFrame newFrame, Bank newBank, Date newDate) {
        super(newFrame, newBank, newDate);
        welcome = new JLabel("Welcome to OOD Bank");
        next = new NextButton("Login");

        welcome.setBounds(175, 50, 800, 200);
        welcome.setFont(new Font("calibri", Font.BOLD, 40));
        welcome.setForeground(Color.decode("#4169E1"));

        idField.setBounds(260, 225, 280, 50);
        pwField.setBounds(260, 295, 280, 50);

        next.setBounds(262, 370, 120, 60);

        forgot.setBounds(390, 375, 175, 25);
        forgot.setBorderPainted(false);
        forgot.setForeground(Color.decode("#1C86EE"));
        open.setBounds(400, 400, 160, 25);
        open.setBorderPainted(false);
        open.setForeground(Color.decode("#1C86EE"));
        manager.setBounds(655, 525, 145, 50);
        manager.setBorderPainted(false);
        manager.setForeground(Color.gray);

        add(welcome);
        add(idField);
        add(pwField);
        add(next);
        add(forgot);
        add(open);
        add(manager);

        next.addActionListener(newListener);
        forgot.addActionListener(newListener);
        open.addActionListener(newListener);
        manager.addActionListener(newListener);
    }

    public void addEvent(ActionEvent e) {
        super.addEvent(e);
        if (e.getSource().equals(forgot)) forgot();
        else if (e.getSource().equals(open)) open();
        else if (e.getSource().equals(manager)) banker();
    }

    public void forgot() {
        frame.addPanel(forgotPanel);
    }

    public void open() {
        frame.addPanel(openPanel);
    }

    public void banker() {
        frame.addPanel(bankerPanel);
    }

    public void setForgot(AtmPanel panel) {
        forgotPanel = panel;
    }

    public void setOpen(AtmPanel panel) {
        openPanel = panel;
    }

    public void setBanker(AtmPanel panel) {
        bankerPanel = panel;
    }

    public void forward() {
        String username = idField.getText();
        String password = pwField.getPW();
        if (username.length() == 0 || pwField.blank()) {
            JOptionPane.showMessageDialog(null,
                    "Username or password cannot be empty!",
                    "Request Failed", JOptionPane.ERROR_MESSAGE);
        } else {
            for (Customer customer : bank.getCustomers()) {
                if (customer.getUsername().equals(username)) {
                    if (customer.getPassword().equals(password)) {
                        JOptionPane.showMessageDialog(null, "Login success!");
                        forward.setCustomer(customer);
                        frame.addPanel(forward);
                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(null,
                    "Incorrect username or password!",
                    "Request Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void reset() {
        idField.reset();
        pwField.reset();
    }
}
