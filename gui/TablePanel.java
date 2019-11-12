package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import bank.*;

//user and manager interface
@SuppressWarnings("serial")
public class TablePanel extends MainPanel {

    private JScrollPane jsp;
    //stocks component
    private JTable table;
    private DefaultTableModel data;

    public TablePanel(String s, AtmFrame newFrame, Bank newBank, Date newDate) {
        super(s, newFrame, newBank, newDate);
        switch (s) {
            case "stocks": {
                setRadio("Holding Shares", "All Stocks");
                Object[][] context = {};
                String[] column = {"Name", "Price", "Change", "Shares"};
                data = new DefaultTableModel(context, column) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                table = new JTable(data);
                jsp = new JScrollPane(table);
                setTable();
                showName();
                next = new NextButton();
                add(next);
                next.addActionListener(newListener);
                break;
            }
            case "viewstocks": {
                setTitle("View Stocks");
                Object[][] context = {};
                String[] column = {"Name", "Price", "Change", "Shares"};
                data = new DefaultTableModel(context, column) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                table = new JTable(data);
                jsp = new JScrollPane(table);
                setTable();
                showBalance();
                next = new NextButton("Details");
                add(next);
                next.addActionListener(newListener);
                break;
            }
            case "setstocks": {
                showStocks();
                Object[][] context = {};
                Object[] column = {"Holder", "Shares"};
                data = new DefaultTableModel(context, column) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                table = new JTable(data);
                jsp = new JScrollPane(table);
                setTable();
                next = new NextButton("Set");
                add(next);
                next.addActionListener(newListener);
                break;
            }
            case "viewloans": {
                setTitle("View Loans");
                showBalance();
                Object[][] context = {};
                String[] column = {"Currency", "Loan", "Interest"};
                data = new DefaultTableModel(context, column) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                table = new JTable(data);
                jsp = new JScrollPane(table);
                setTable();
                break;
            }
        }
    }

    private void setTable() {
        table.setBounds(245, 165, 475, 300);
        table.setBackground(Color.decode("#F7F7F7"));
        table.setFocusable(false);
        jsp.setBounds(240, 160, 500, 310);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(jsp);
    }

    //reset functions

    private void resetTable() {
        if (bank.getStocks() == null)
            return;
        if (panelName.equals("stocks") && radio1.isSelected()) {
            SecurityAccount security = (SecurityAccount) account;
            String[][] newContent = security.stockTable();
            String[] column = {"Name", "Price", "Change", "Holding"};
            data.setDataVector(newContent, column);
        } else if (panelName.equals("setstocks")) {
            String[][] newContent = bank.holderTable(stock.getName());
            String[] column = {"Holder", "Shares"};
            data.setDataVector(newContent, column);
        } else if (panelName.equals("viewloans")) {
            SavingsAccount savings = (SavingsAccount) account;
            String[][] newContent = savings.loanTable();
            String[] column = {"Currency", "Loan", "Interest"};
            data.setDataVector(newContent, column);
        } else {
            String[][] newContent = bank.stockTable();
            String[] column = {"Name", "Price", "Change", "Shares"};
            data.setDataVector(newContent, column);
        }
    }

    private void setStocks() {
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JTextField amountField = new JTextField(10);
        JTextField shareField = new JTextField(10);
        JPasswordField bankerPWField = new JPasswordField(10);
        panel1.add(new JLabel("New Price: "));
        panel1.add(amountField);
        panel1.add(Box.createHorizontalStrut(15));
        panel1.add(new JLabel("New Shares: "));
        panel1.add(shareField);
        panel2.add(Box.createHorizontalStrut(15));
        panel2.add(new JLabel("Password: "));
        panel2.add(bankerPWField);

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab("Update Stock", panel1);
        jTabbedPane.addTab("Remove Stock", panel2);

        int result = JOptionPane.showConfirmDialog(null, jTabbedPane,
                "Set Stock", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            //try {
            if (jTabbedPane.getSelectedIndex() == 0) {
                if (amountField.getText().length() > 0) {
                    double price = Double.parseDouble(amountField.getText());
                    if (price > 0) {
                        bank.setStockPrice(stock.getName(), price);
                    } else {
                        requestFailMsg();
                        return;
                    }
                }
                if (shareField.getText().length() > 0) {
                    int shares = Integer.parseInt(shareField.getText());
                    if (shares > 0) {
                        if (shares != stock.getShares()) {
                            bank.setStockShare(stock.getName(), shares);
                        } else if (amountField.getText().length() == 0) {
                            JOptionPane.showMessageDialog(null,
                                    "No change made!", "Update Stock", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    } else {
                        requestFailMsg();
                        return;
                    }
                }
                if (amountField.getText().length() == 0 && shareField.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "No change made!", "Update Shares", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(null,
                        "Stock price and shares updated!", "Update Stock", JOptionPane.INFORMATION_MESSAGE);
                reset();
            } else if (jTabbedPane.getSelectedIndex() == 1) {
                String password = String.valueOf(bankerPWField.getPassword());
                if (password.length() == 0)
                    noPWMsg();
                else if (!password.equals(bank.getBankerPW())) {
                    wrongPWMsg();
                } else {
                    if (bank.deleteStock(stock.getName())) {
                        JOptionPane.showMessageDialog(null,
                                "Stock removed!", "Remove Stock", JOptionPane.INFORMATION_MESSAGE);
                        super.backward();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Cannot remove a stock that has holders!", "Request Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
			/*}catch(Exception e) {
				requestFailMsg();
			}*/
        }
    }

    //events

    public void reset() {//update data
        super.reset();
        resetTable();
    }

    public void forward() {
    		if (panelName.equals("viewstocks")) {
            if (table.getSelectedRow() > -1) {
                String stockname = table.getValueAt(table.getSelectedRow(), 0).toString();
                setStock(bank.getStocks().get(stockname));
                super.forward();
            } else
                noStockMsg();
        } else if (panelName.equals("stocks")) {
            if (table.getSelectedRow() > -1) {
                String stockname = table.getValueAt(table.getSelectedRow(), 0).toString();
                if (!radio1.isSelected() && !radio2.isSelected()) {
                    noStockMsg();
                    return;
                } else if (radio1.isSelected()) {
                    setStock(customer.getSecurityAccounts().getStocks().get(stockname));
                } else {
                    setStock(bank.getStocks().get(stockname));
                }
                if (radio2.isSelected())
                    super.forward();
                else
                    super.link();
            } else
                noStockMsg();
        } else if (panelName.equals("setstocks")) {
            setStocks();
        } else
            super.forward();
    }
}