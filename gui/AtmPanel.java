package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import bank.*;

//general panel common components&methods
@SuppressWarnings("serial")
public abstract class AtmPanel extends JPanel {

    protected CancelButton cancel = new CancelButton();
    protected NextButton next;
    protected NextButton extra;
    protected ActionListener newListener;
    protected AtmPanel forward;
    protected AtmPanel backward;
    protected AtmPanel link;//extra link panel
    protected AtmFrame frame;
    protected Bank bank;
    protected Date date;
    protected Customer customer;
    protected Account account;
    protected String panelName;
    protected Stock stock;

    public AtmPanel(AtmFrame newFrame, Bank newBank, Date newDate) {
        setLayout(null);
        setBackground(Color.decode("#FFFFFF"));
        add(cancel);
        frame = newFrame;
        bank = newBank;
        date = newDate;
        newListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                addEvent(e);
            }
        };
        cancel.addActionListener(newListener);
    }

    public void addEvent(ActionEvent e) {
        if (e.getSource().equals(next)) forward();
        else if (e.getSource().equals(cancel)) backward();
        else if (e.getSource().equals(extra)) link();
    }

    public void setFore(AtmPanel newPanel) {
        forward = newPanel;
    }

    public void setBack(AtmPanel newPanel) {
        backward = newPanel;
    }

    public void setLink(AtmPanel newPanel) {
        link = newPanel;
    }

    public void setCustomer(Customer newCustomer) {
        customer = newCustomer;
    }

    public void setAccount(Account newAccount) {
        account = newAccount;
    }

    public void setDate(Date newDate) {
        date = newDate;
    }

    public void setStock(Stock newStock) {
        stock = newStock;
    }

    public void forward() {
        forward.setAccount(account);
        forward.setCustomer(customer);
        forward.setDate(date);
        forward.setStock(stock);
        frame.addPanel(forward);
    }

    public void backward() {
        if (panelName != null &&
                (panelName.equals("main") || panelName.equals("manager"))) {
            int response = JOptionPane.showConfirmDialog(null,
                    "Sure to log out?", "Log Out", 0);
            if (response == JOptionPane.NO_OPTION) {
                return;
            }
        }
        if (backward == null)
            System.exit(0);
        else {
            backward.setAccount(account);
            backward.setCustomer(customer);
            backward.setDate(date);
            backward.setStock(stock);
            frame.addPanel(backward);
        }
    }

    public void link() {
        link.setAccount(account);
        link.setCustomer(customer);
        link.setDate(date);
        link.setStock(stock);
        frame.addPanel(link);
    }

    abstract public void reset(); //for overriden
}
