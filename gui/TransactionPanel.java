package gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import bank.*;

//user and manager interface
@SuppressWarnings("serial")
public class TransactionPanel extends MainPanel {

	private JTextArea record;
    private JScrollPane jsp;

    public TransactionPanel(String s, AtmFrame newFrame, Bank newBank, Date newDate) {
        super(s, newFrame, newBank, newDate);
        switch (s) {
            case "transactions": {
                setTitle("Recent Transactions");
                showBalance();
                record = new JTextArea();
                jsp = new JScrollPane(record);
                showTransactions();
                break;
            }
            case "securitytrans": {
                setTitle("Recent Transactions");
                showName();
                record = new JTextArea();
                jsp = new JScrollPane(record);
                showTransactions();
                break;
            }
            case "daily": {
                setTitle("Recent Transactions");
                showBalance();
                record = new JTextArea();
                jsp = new JScrollPane(record);
                showTransactions();
                break;
            }
        }
    }

    //show components&info

    private void showTransactions() {
        record.setBounds(245, 145, 475, 340);
        record.setBackground(Color.decode("#F7F7F7"));
        record.setFocusable(false);
        record.setEditable(false);
        jsp.setBounds(240, 140, 500, 350);//horizon?
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(jsp);
    }

    //reset functions
    private void resetTransactions() {
        record.setText("");
        ArrayList<Transaction> transactions = account.getTransactions();
        transactions.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getID() - o2.getID();
            }
        });
        for (int i = transactions.size() - 1; i >= 0; i--) {
            record.append(transactions.get(i).toString());
            record.append("\n");
        }
    }

    private void resetDaily() {
        record.setText("");
        ArrayList<Transaction> transactions = bank.getBankerTransactions();
        transactions.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getID() - o2.getID();
            }
        });
        for (int i = transactions.size() - 1; i >= 0; i--) {
            record.append(transactions.get(i).toString(bank));
            record.append("\n");
        }
    }

    public void reset() {//update data
    		super.reset();
        if (panelName.equals("transactions") || panelName.equals("securitytrans")) {
            resetTransactions();
        }
        if (panelName.equals("daily")) {
            resetDaily();
        }
    }
}