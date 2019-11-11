import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import gui.*;

//user and manager interface
@SuppressWarnings("serial")
public class MainPanel extends AtmPanel{
	
	private IconLabel icon = new IconLabel();
	private JPanel bg = new JPanel();

	private AtmButton[] buttons;
	private AtmPanel[] buttonLinks;
	private int buttonNum;
	
	private JLabel title;
	
	//show balance or name
	private JLabel name;
	private JLabel[] labels;
	private JLabel label1, label2;
	private JComboBox<Account> accounts;
	
	//transfer component
    private DefaultComboBoxModel<Account> model;
    private JComboBox<Customer> customers;
    private JComboBox<Account> extraAccounts;
    
	//record component
	private JTextArea record;
	private AtmTextField enter;
	private JScrollPane jsp;
	//stocks component
	private JTable table;
	private DefaultTableModel data;
	private int dealshares;
	
	private JRadioButton radio1, radio2;
	
	private final String[] currencySign = {"$", "¥", "€"};
	private final SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
	public MainPanel(AtmFrame newFrame, Bank newBank, Date newDate) {
		super(newFrame, newBank, newDate);
		setLayout(null);
		setBackground(Color.decode("#FFFFFF"));
		bg.setLayout(null);
		bg.setBackground(Color.decode("#F7F7F7"));
		bg.setBounds(0, 0, 220, 580);
		bg.setBorder(BorderFactory.createEtchedBorder() );
		add(bg);
		bg.add(cancel);
		bg.add(icon);
	}
	public MainPanel(String s, AtmFrame newFrame, Bank newBank, Date newDate) {
		this(newFrame, newBank, newDate);
		panelName = s;
		switch(s) {
		case "main":{
			String[] names = {"Open an Account", "View My Accounts", "Transfer",
					"Request Loans"};
			int[] sizes = {17, 15, 20, 17};
			setButtons(names, sizes, 4);
			buttonNum = 4;
			setTitle("Select Your Transaction");
			showName();
			break;
		}
		case "openopt":{
			setTitle("Select a Type of Account");
			String[] names = {"Checking", "Savings", "Security"};
			int[] sizes = {20, 20, 20};
			showName();
			setButtons(names, sizes, 3);
			buttonNum = 3;
			break;
		}
		case "select":{
			showName();
			textAndBox("Select an Account");
			next = new NextButton();
			add(next);
			next.addActionListener(newListener);
			break;
		}
		case "view":{
			String[] names = {"Withdrawal", "Deposit", "Transactions", "Close Account"};
			int[] sizes = {20, 20, 17, 17};
			setButtons(names, sizes, 4);
			buttonNum = 4;
			buttons[3].setBackground(Color.gray);
			showBalance();
			break;
		}
		case "withdraw":{
			String[] names = {"$20", "$40", "$60", "$80",
					"$100", "Enter Amount"};
			int[] sizes = {20, 20, 20, 20, 20, 20};
			setButtons(names, sizes, 6);
			buttonNum = 6;
			setTitle("Get Cash");
			showBalance();
			break;
		}
		case "deposit":{
			String[] names = {"$20", "$40", "$60", "$80",
					"$100", "Enter Amount"};
			int[] sizes = {20, 20, 20, 20, 20, 20};
			setButtons(names, sizes, 6);
			buttonNum = 6;
			setTitle("Deposit");
			showBalance();
			break;
		}
		case "security":{//view security
			String[] names = {"View Stocks", "Close Account"};
			int[] sizes = {17, 17};
			buttonNum = 2;
			setButtons(names, sizes, 2);
			buttons[1].setBackground(Color.gray);
			showName();
			setTitle("Security Account");
			break;
		}
		case "stocks":{
			setRadio("Holding Shares", "All Stocks");
			Object[][] context = {};
			String[] column = {"Name", "Price", "Change", "Shares"};
			data = new DefaultTableModel(context, column) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};;
			table = new JTable(data);
			jsp = new JScrollPane(table);
			setTable();
			showName();
			next = new NextButton();
			add(next);
			next.addActionListener(newListener);
			break;
		}
		case "viewstocks":{
			setTitle("View Stocks");
			Object[][] context = {};
			String[] column = {"Name", "Price", "Change", "Shares"};
			data = new DefaultTableModel(context, column) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};;
			table = new JTable(data);
			jsp = new JScrollPane(table);
			setTable();
			showBalance();
			next = new NextButton("Details");
			add(next);
			next.addActionListener(newListener);
			break;
		}
		case "buystocks":{
			textAndBox("Select an Account");
			enter = new AtmTextField("");
			enter.setBounds(260, 360, 250, 30);
			enter.setHorizontalAlignment(JTextField.LEFT);
			add(enter);
			JLabel label = new JLabel("Enter Shares");
			label.setFont(new Font("calibri",Font.BOLD, 20));
			label.setBounds(260, 260, 300, 100);
			showStocks();
			add(label);
			next = new NextButton("Buy");
			add(next);
			next.addActionListener(newListener);
			break;
		}
		case "sellstocks":{
			textAndBox("Select an Account");
			enter = new AtmTextField("");
			enter.setBounds(260, 360, 250, 30);
			enter.setHorizontalAlignment(JTextField.LEFT);
			add(enter);
			JLabel label = new JLabel("Enter Shares");
			label.setFont(new Font("calibri",Font.BOLD, 20));
			label.setBounds(260, 260, 300, 100);
			showStocks();
			add(label);
			next = new NextButton("Sell");
			add(next);
			next.addActionListener(newListener);
			break;
		}
		case "setstocks":{
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
		case "transfer":{//adjust later
			textAndBox("Transfer from");
			label2 = new JLabel("To Account");
			label2.setFont(new Font("calibri",Font.BOLD, 20));
			label2.setBounds(260, 270, 250, 100);
			add(label2);
			customers = new JComboBox<>();
			model = new DefaultComboBoxModel<>();
			extraAccounts = new JComboBox<>(model);
			customers.setBounds(260, 340, 200, 100);
			extraAccounts.setBounds(480, 340, 250, 100);
			add(customers);
			add(extraAccounts);
			showName();
			next = new NextButton();
			add(next);
			next.addActionListener(newListener);
			break;
		}
		case "loans":{
			setRadio("Request Loan", "Repay Loan");
			record = new JTextArea("You can request a loan of no more than 10,000"
					+ "\n on each currency.\n\n"
					+ "You cannot request a loan if you haven't repaid it.\n\n"
					+ "We will charge a fee at a 0.1% daily rate.");
			next = new NextButton();
			
			label1 = new JLabel("Choose an Account");
			label1.setFont(new Font("calibri",Font.BOLD, 20));
			label1.setBounds(260, 150, 300, 100);
			add(label1);
			accounts = new JComboBox<>();
			accounts.setBounds(260, 210, 250, 100);
			
			record.setFont(new Font("calibri",Font.CENTER_BASELINE, 17));
			record.setForeground(Color.gray);
			record.setBounds(260, 320, 500, 150);
			record.setFocusable(false);
			add(record);
			showName();
			add(next);
			add(accounts);
			next.addActionListener(newListener);
			break;
		}
		case "transactions":{
			setTitle("Recent Transactions");
			showBalance();
			record = new JTextArea();
			jsp = new JScrollPane(record);
			showTransactions();
			break;
		}
		case "viewloans":{
			setTitle("View Loans");
			showBalance();
			Object[][] context = {};
			String[] column = {"Currency", "Loan", "Interest"};
			data = new DefaultTableModel(context, column) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};;
			table = new JTable(data);
			jsp = new JScrollPane(table);
			setTable();
		}
		case "securitytrans":{
			setTitle("Recent Transactions");
			showName();
			record = new JTextArea();
			jsp = new JScrollPane(record);
			showTransactions();
			break;
		}
		case "manager":{
			String[] names = {"Add 1 Day", "Add 3 Days", "Daily Report"
					, "View Customers", "View Stocks", "Add Stocks"};
			int[] sizes = {17, 17, 15, 15, 17, 17};
			setButtons(names, sizes, 6);
			buttonNum = 6;
			setTitle("Hello! Banker");
			showBalance();
			break;
		}
		case "daily":{
			setTitle("Recent Transactions");
			showBalance();
			record = new JTextArea();
			jsp = new JScrollPane(record);
			showTransactions();
			break;
		}
		case "accounts":{
			//textAndBox()
			setRadio("All Customers", "Loan Customers");

			next = new NextButton();
			label1 = new JLabel("Customer");
			label2 = new JLabel("Account");
			label1.setFont(new Font("calibri",Font.BOLD, 20));
			label2.setFont(new Font("calibri",Font.BOLD, 20));
			label1.setBounds(260, 150, 300, 100);
			label2.setBounds(260, 270, 300, 100);
			add(label1);
			add(label2);
			customers = new JComboBox<>();
			model = new DefaultComboBoxModel<>();
			accounts = new JComboBox<>(model);
			customers.setBounds(260, 210, 250, 100);
			accounts.setBounds(260, 330, 250, 100);
			add(next);
			add(customers);
			add(accounts);
			showBalance();
			next.addActionListener(newListener);
			break;
		}
		}
	}
	//show components&info
	private void setTitle(String s) {
		if (title!=null)
			remove(title);
		title = new JLabel(s);
		title.setBounds(240, 50, 300, 100);
		title.setFont(new Font("calibri",Font.BOLD, 20));
		title.setFocusable(false);
		add(title);
	}
	private void showBalance() {
		name = new JLabel("#");
		labels = new JLabel[3];
		labels[0] = new JLabel("#");
		labels[1] = new JLabel("#");
		labels[2] = new JLabel("#");
		JLabel usd = new JLabel("USD");
		JLabel cny = new JLabel("CNY");
		JLabel eur = new JLabel("EUR");

		name.setBounds(30, 110, 200, 100);
		name.setFont(new Font("calibri",Font.BOLD, 20));
		name.setFocusable(false);
		usd.setBounds(30, 150, 200, 100);
		usd.setFont(new Font("calibri",Font.BOLD, 15));
		usd.setFocusable(false);
		usd.setForeground(Color.gray);
		labels[0].setBounds(30, 180, 200, 100);
		labels[0].setFont(new Font("calibri",Font.BOLD, 20));
		labels[0].setFocusable(false);
		cny.setBounds(30, 215, 200, 100);
		cny.setFont(new Font("calibri",Font.BOLD, 15));
		cny.setFocusable(false);
		cny.setForeground(Color.gray);
		labels[1].setBounds(30, 245, 200, 100);
		labels[1].setFont(new Font("calibri",Font.BOLD, 20));
		labels[1].setFocusable(false);
		eur.setBounds(30, 280, 200, 100);
		eur.setFont(new Font("calibri",Font.BOLD, 15));
		eur.setFocusable(false);
		eur.setForeground(Color.gray);
		labels[2].setBounds(30, 310, 200, 100);
		labels[2].setFont(new Font("calibri",Font.BOLD, 20));
		labels[2].setFocusable(false);

		bg.add(name);
		bg.add(usd);
		bg.add(cny);
		bg.add(eur);
		bg.add(labels[0]);
		bg.add(labels[1]);
		bg.add(labels[2]);
		
		if (customer!=null)
			resetBalance();
	}
	private void showName() {
		JLabel welcome = new JLabel("Hello!");
		name = new JLabel("#");
		welcome.setBounds(30, 110, 200, 100);
		welcome.setFont(new Font("calibri",Font.BOLD, 20));
		welcome.setForeground(Color.gray);
		welcome.setFocusable(false);
		name.setBounds(30, 150, 200, 100);
		name.setFont(new Font("calibri",Font.BOLD, 20));
		name.setForeground(Color.gray);
		name.setFocusable(false);
		bg.add(welcome);
		bg.add(name);
	}
	private void showTransactions() {
		record.setBounds(245, 145, 475, 340);
		record.setBackground(Color.decode("#F7F7F7"));
		record.setFocusable(false);
		record.setEditable(false);
        jsp.setBounds(240, 140, 500, 350);//horizon?
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(jsp);
	}
	private void setButtons(String[] words, int[] sizes, int num) {
		buttons = new AtmButton[num];
		buttonLinks = new AtmPanel[num];
		for (int i=0; i<num; i++) {
			buttons[i] = new AtmButton(words[i], sizes[i]);
			buttons[i].setBounds(370-(int)Math.pow(-1, i)*110,
					150+(int)(i/2)*120, 200, 100);
			add(buttons[i]);
			buttons[i].addActionListener(newListener);
		}
	}
	private void textAndBox(String line) {
		JLabel label = new JLabel(line);
		label.setFont(new Font("calibri",Font.BOLD, 20));
		label.setBounds(260, 130, 300, 100);
		accounts = new JComboBox<>();
		accounts.setBounds(260, 200, 250, 100);
		add(label);
		add(accounts);
	}
	private void setRadio(String text1, String text2) {
		radio1 = new JRadioButton(text1);
		radio2 = new JRadioButton(text2);
		radio1.setBounds(240, 75, 200, 100);
		radio2.setBounds(450, 75, 200, 100);
		radio1.setFont(new Font("calibri",Font.BOLD, 20));
		radio2.setFont(new Font("calibri",Font.BOLD, 20));
		radio1.setSelected(true);
		radio1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radio2.setSelected(false);
                reset();
            }
        	});
		radio2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radio1.setSelected(false);
                reset();
            }
        	});
		add(radio1);
		add(radio2);
	}
	public void setTable() {
		table.setBounds(245, 165, 475, 300);
		table.setBackground(Color.decode("#F7F7F7"));
		table.setFocusable(false);
        jsp.setBounds(240, 160, 500, 310);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(jsp);
	}
	public void showStocks() {
		name = new JLabel("#");
		labels = new JLabel[3];
		labels[0] = new JLabel("#");
		labels[1] = new JLabel("#");
		labels[2] = new JLabel("#");

		name.setBounds(30, 110, 200, 100);
		name.setFont(new Font("calibri",Font.BOLD, 20));
		name.setFocusable(false);
		labels[0].setBounds(30, 180, 200, 100);
		labels[0].setFont(new Font("calibri",Font.BOLD, 20));
		labels[0].setFocusable(false);
		labels[1].setBounds(130, 182, 200, 100);
		labels[1].setFont(new Font("calibri",Font.BOLD, 15));
		labels[1].setFocusable(false);
		labels[2].setBounds(30, 245, 200, 100);
		labels[2].setFont(new Font("calibri",Font.BOLD, 20));
		labels[2].setFocusable(false);

		bg.add(name);
		bg.add(labels[0]);
		bg.add(labels[1]);
		bg.add(labels[2]);
		
		JLabel shares = new JLabel("Shares");
		JLabel price = new JLabel("Price");
		price.setBounds(30, 150, 200, 100);
		price.setFont(new Font("calibri",Font.BOLD, 15));
		price.setForeground(Color.gray);
		price.setFocusable(false);
		shares.setBounds(30, 215, 200, 100);
		shares.setFont(new Font("calibri",Font.BOLD, 15));
		shares.setForeground(Color.gray);
		shares.setFocusable(false);
		bg.add(shares);
		bg.add(price);
		
		if (stock!=null)
			resetStocks();
	}
	
	//reset functions
	public void resetRadio() {
		if (!radio1.isSelected() && !radio2.isSelected())
			radio1.setSelected(true);
	}
	private void resetTransactions() {
		record.setText("");
        ArrayList<Transaction> transactions = account.getTransactions();
        transactions.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getDate().compareTo(o2.getDate());
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
				return o1.getDate().compareTo(o2.getDate());
			}
	 	});
		for (int i = transactions.size() - 1; i >= 0; i--) {
			record.append(transactions.get(i).toString(bank));
			record.append("\n");
	 	}
	}
	private void resetEarned() {
		name.setText("Total Earned");
		name.setForeground(Color.GRAY);
        HashMap<Currency, Double> moneyEarned = bank.calculateMoneyEarned();
        int i = 0;
        for (Currency currency : Currency.values()) {
        		labels[i].setText(currencySign[i]+moneyEarned.get(currency));
        		i++;
        }
	}
	private void resetBalance() {
		resetName();
		int i = 0;
        for (Currency currency : Currency.values()) {
        		labels[i].setText(currencySign[i]+account.getDeposit(currency));
            i++;
        }
        setTitle(account.getType().toString()+" - ●●●●"+account.getNumber().substring(8));
	}
	private void resetName() {
		name.setText(customer.getUsername());
	}
	private void resetSelect() {
		accounts.removeAllItems();
        if (customer.getCheckingAccounts().size() != 0) {
            for (Account account : customer.getCheckingAccounts()) {
                accounts.addItem(account);
            }
        }
        if (customer.getSavingsAccounts().size() != 0) {
            for (Account account : customer.getSavingsAccounts()) {
                accounts.addItem(account);
            }
        }
        if (panelName.equals("select") && customer.getSecurityAccounts()!=null) {
        		accounts.addItem(customer.getSecurityAccounts());
        }
	}
	private void resetTransfer() {
		accounts.removeAllItems();
		customers.removeAllItems();
		model.removeAllElements();
		if (customer.getCheckingAccounts().size() != 0) {
            for (Account account : customer.getCheckingAccounts()) {
                accounts.addItem(account);
            }
            accounts.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    updateToAccounts((Customer) customers.getSelectedItem());
                }
            });
        }
		if (bank.getCustomers().size() != 0) {
            for (Customer customer : bank.getCustomers()) {
                customers.addItem(customer);
            }
            customers.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.DESELECTED)
                        updateToAccounts((Customer) customers.getSelectedItem());
                }
            });
        }
		updateToAccounts((Customer) customers.getSelectedItem());
	}
	private void resetAccounts() {
		customers.removeAllItems();
		if (bank.getCustomers().size() != 0) {
			if (radio1.isSelected()) {
				for (Customer customer : bank.getCustomers()) {
					customers.addItem(customer);
				}
			}
			else {
				for (Customer customer : bank.getCustomers()) {
					if (customer.findLoanAccounts().size() != 0) {
						customers.addItem(customer);
					}
				}
			}
			customers.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.DESELECTED) {
						updateViewAccounts((Customer) customers.getSelectedItem());
						
					}
				}
			});
			updateViewAccounts((Customer) customers.getSelectedItem());
		}
	}
	private void resetStocks() {
		if (!panelName.equals("sellstocks")&&!panelName.equals("buystocks"))
		setTitle(stock.getName()+"  "+format.format(date));
		name.setText(stock.getName());
		labels[0].setText("$"+stock.getUSDPrice());
		labels[1].setText(String.valueOf(CommonMathMethod.bigDecimalMultiply(stock.getChange(), 100.0)) + "%");
		labels[2].setText(String.valueOf(stock.getShares()));
		if (stock.getChange()>0)
			labels[1].setForeground(Color.decode("#32CD32"));
		else if (stock.getChange()<0)
			labels[1].setForeground(Color.decode("#EE5C42"));
		else {
			labels[1].setText("0.00%");
			labels[1].setForeground(Color.gray);
		}
	}
	private void resetTable() {
		if (bank.getStocks() == null)
			return;
		if (panelName.equals("stocks") && radio1.isSelected()) {
			SecurityAccount security = (SecurityAccount) account;
			String[][] newContent = security.stockTable();
			String[] column = {"Name", "Price", "Change", "Holding"};
			data.setDataVector(newContent, column);
		}
		else if (panelName.equals("setstocks")){
			String[][] newContent = bank.holderTable(stock.getName());
			String[] column = {"Holder", "Shares"};
			data.setDataVector(newContent, column);
		}
		else if (panelName.equals("viewloans")) {
			SavingsAccount savings = (SavingsAccount) account;
			String[][] newContent = savings.loanTable();
			String[] column = {"Currency", "Loan", "Interest"};
			data.setDataVector(newContent, column);
		}
		else {
			String[][] newContent = bank.stockTable();
			String[] column = {"Name", "Price", "Change", "Shares"};
			data.setDataVector(newContent, column);
		}
	}
	private void updateViewAccounts(Customer selected) {
		model.removeAllElements();
		if (selected != null) {
			if (radio1.isSelected()) {
				if (selected.getCheckingAccounts().size() != 0) {
					for (Account account : selected.getCheckingAccounts()) {
						model.addElement(account);
					}
				}
				if (selected.getSavingsAccounts().size() != 0) {
					for (Account account : selected.getSavingsAccounts()) {
						model.addElement(account);
					}
				}
			}
			else if (selected.findLoanAccounts().size() != 0) {
				for (Account account : selected.findLoanAccounts()) {
					model.addElement(account);
				}
			}
		}
	}
	private void updateToAccounts(Customer toCustomer) {
        model.removeAllElements();
        if (toCustomer != null) {
            if (toCustomer.getCheckingAccounts().size() != 0) {
                for (Account account : toCustomer.getCheckingAccounts()) {
                    if (account == accounts.getSelectedItem())
                        continue;
                    model.addElement(account);
                }
            }
        }
    }
	private void transactions() {
		JTextField jTextFieldMoney = new JTextField(10);
		JPasswordField jPasswordFieldPassword = new JPasswordField(10);
		JComboBox<Currency> jComboBoxCurrency = new JComboBox<>();
		for (Currency currency : Currency.values())
        jComboBoxCurrency.addItem(currency);

		JPanel jPanel = new JPanel();
		if (!(panelName.equals("loans")&&radio2.isSelected()) 
				&& !panelName.equals("buystocks") 
				&& !panelName.equals("sellstocks")) {
    			jPanel.add(new JLabel("Money: "));
    			jPanel.add(jTextFieldMoney);
		}
		jPanel.add(Box.createHorizontalStrut(15)); // a spacer
		jPanel.add(new JLabel("Password: "));
		jPanel.add(jPasswordFieldPassword);
		jPanel.add(Box.createHorizontalStrut(15)); // a spacer
		jPanel.add(new JLabel("Currency: "));
		jPanel.add(jComboBoxCurrency);
		
		int result = JOptionPane.showConfirmDialog(null, jPanel,
				(panelName!="loans")?panelName:(radio1.isSelected()?"request loans":"repay loans"), 
						JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			Currency currency = (Currency) jComboBoxCurrency.getSelectedItem();
			String password = String.valueOf(jPasswordFieldPassword.getPassword());
			try {
				if ((!(panelName.equals("loans")&&radio2.isSelected()) 
						&& !panelName.equals("buystocks") 
						&& !panelName.equals("sellstocks"))
					&&jTextFieldMoney.getText().length()==0) {
 					noInputMsg();
				}
				else if (password.length()==0) {
					noPWMsg();
				}
				else {
            			switch(panelName) {
            			case "deposit":{
            				if (!account.isPasswordRight(password)) {
            					wrongPWMsg();
            				}
            				else {
            					double money = Double.valueOf(jTextFieldMoney.getText());
                				account.save(money, currency, date);
                				JOptionPane.showMessageDialog(null,
                						"Save Money Finished!", 
                						"Save money success", JOptionPane.INFORMATION_MESSAGE);
            				}
            				break;
            			}
            			case "withdraw":{
            				if (!account.isPasswordRight(password)) {
            					wrongPWMsg();
            				}
            				else {
                   			double money = Double.valueOf(jTextFieldMoney.getText());
            					int withdraw = account.withdraw(money, currency, date);
            					if (withdraw == -1) {
             					JOptionPane.showMessageDialog(null,
              							"Withdrawal must be more than 0!", 
               							"Request Failed", JOptionPane.ERROR_MESSAGE);
                				} else if (withdraw == 0) {
                					noMoneyMsg();
                				} else {
                					JOptionPane.showMessageDialog(null, 
                							"Withdraw Money Finished!", 
                							"Withdraw money success", JOptionPane.INFORMATION_MESSAGE);
                				}
            				}
        					break;
            			}
            			case "transfer":{
            				double money = Double.valueOf(jTextFieldMoney.getText());
            				CheckingAccount accountFrom = (CheckingAccount) accounts.getSelectedItem();
            				CheckingAccount accountTo = (CheckingAccount) extraAccounts.getSelectedItem();
            				if (!accountFrom.isPasswordRight(password)) {
            					wrongPWMsg();
            				}
            				else {
                				int transfer = accountFrom.transferOut(accountTo, money, currency, date);
                				if (transfer == -1) {
                					JOptionPane.showMessageDialog(null, 
                							"Transfer amount must be more than 0!",
                							"Request Failed", JOptionPane.ERROR_MESSAGE);
                				} else if (transfer == 0) {
                					noMoneyMsg();
                				} else {
                					JOptionPane.showMessageDialog(null, 
                							"Transfer Money Finished!", 
                							"Transfer money success", JOptionPane.INFORMATION_MESSAGE);
                				}
            				}
            				break;
            			}
            			case "loans":{
            				SavingsAccount savings = (SavingsAccount) accounts.getSelectedItem();
            				if (!savings.isPasswordRight(password)) {
            					wrongPWMsg();
            				}
            				else {
            					if (radio1.isSelected()){
                					double money = Double.valueOf(jTextFieldMoney.getText());
                					int loan = savings.requestLoan(money, currency, date);
                					if (loan == -1) {
                						JOptionPane.showMessageDialog(null, 
                								"You need to repay your loan first!", 
                								"Request Failed", JOptionPane.ERROR_MESSAGE);
                					} else if (loan == 0) {
                						JOptionPane.showMessageDialog(null, 
                								"Loan must be between 0-10000!", 
                								"Request Failed", JOptionPane.ERROR_MESSAGE);
                					} else {
                						JOptionPane.showMessageDialog(null, 
                								"Loan Finished!", 
                								"Loan success", JOptionPane.INFORMATION_MESSAGE);
                					}
                				}
                				else {
                					int repay = savings.repayLoan(currency, date);
                					if (repay == -1) {
                						JOptionPane.showMessageDialog(null, 
                								"You have no loan to repay!", 
                								"Request Failed", JOptionPane.ERROR_MESSAGE);
                					} else if (repay == 0) {
                						noMoneyMsg();
                					} else {
                						JOptionPane.showMessageDialog(null, 
                								"Repay Finished!",
                								"Repay success", JOptionPane.INFORMATION_MESSAGE);
                					}
                				}
            				}
            				break;
            			}
            			case "buystocks":{
            				SavingsAccount savings = (SavingsAccount) accounts.getSelectedItem();
            				if (!savings.isPasswordRight(password)) {
            					wrongPWMsg();
            				}
            				else {
            					int buy = customer.getSecurityAccounts().buyStock(stock.getName(), dealshares, currency, savings, date);
            					if (buy == -1) {
            						JOptionPane.showMessageDialog(null, 
            								"Cannot buy more than bank's inventory!", 
            								"Request Failed", JOptionPane.ERROR_MESSAGE);
            					} else if (buy == 1) {
            						noMoneyMsg();
            					} else {
            						JOptionPane.showMessageDialog(null, 
            								"Purchase Finished!",
            								"Purchase success", JOptionPane.INFORMATION_MESSAGE);
            					}
            				}
            				break;
            			}
            			case "sellstocks":{
            				SavingsAccount savings = (SavingsAccount) accounts.getSelectedItem();
            				if (!savings.isPasswordRight(password)) {
            					wrongPWMsg();
            				}
            				else {
            					int sell = customer.getSecurityAccounts().sellStock(stock.getName(), dealshares, currency, savings, date);
            					if (sell == -1) {
            						JOptionPane.showMessageDialog(null, 
            								"Cannot sell more than your holding shares!", 
            								"Request Failed", JOptionPane.ERROR_MESSAGE);
            					} else {
            						JOptionPane.showMessageDialog(null, 
            								"Selling Finished!",
            								"Selling success", JOptionPane.INFORMATION_MESSAGE);
            					}
            				}
            				break;
            			}
            			}
				}
            	reset();
			} catch (Exception e) {
				requestFailMsg();
			}
    		}
    }
    private boolean quickVerify() {//verify password
    		JPasswordField jPasswordFieldPassword = new JPasswordField(15);
    		JPanel jPanel = new JPanel();
    		jPanel.add(new JLabel("Password: "));
    		jPanel.add(jPasswordFieldPassword);
    		String dialogTitle;
    		switch(panelName) {
    		case "select":dialogTitle = "View Account";break;
    		case "deposit":dialogTitle = "Quick Deposit";break;
    		case "withdraw":dialogTitle = "Quick Withdraw";break;
    		case "sellstocks":dialogTitle = "Sell Stocks";break;
    		default:dialogTitle = "";
    		}
    		int result = JOptionPane.showConfirmDialog(null, jPanel,
    				dialogTitle, JOptionPane.OK_CANCEL_OPTION);
    		if (result == JOptionPane.OK_OPTION) {
    			String password = String.valueOf(jPasswordFieldPassword.getPassword());
    			if (password.length()==0) {
    				noPWMsg();
    			}
    			else if (account.isPasswordRight(password))
    				return true;
    			else
    				wrongPWMsg();
    		}
		return false;
    }
    public void setStocks() {
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
        JTextField amountField = new JTextField(10);
        JTextField shareField = new JTextField(10);
        JPasswordField bankerPWField = new JPasswordField(10);
        panel1.add(Box.createHorizontalStrut(15));
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
				if (jTabbedPane.getSelectedIndex()==0) {
					if (amountField.getText().length() > 0) {
						double price = Double.parseDouble(amountField.getText());
						if (price > 0){
							bank.setStockPrice(stock.getName(), price);
						}
						else {
							requestFailMsg();
							return;
						}
					}
					if (shareField.getText().length() > 0) {
						int shares = Integer.parseInt(shareField.getText());
						if (shares>0) {
							if (shares != stock.getShares()) {
								bank.setStockShare(stock.getName(), shares);
							}
							else if (amountField.getText().length() == 0){
	    							JOptionPane.showMessageDialog(null, 
	    									"No change made!", "Update Stock", JOptionPane.INFORMATION_MESSAGE);
	    							return;
							}
						}
						else {
							requestFailMsg();
							return;
						}
					}
					if (amountField.getText().length()==0 && shareField.getText().length()==0) {
						JOptionPane.showMessageDialog(null, 
								"No change made!", "Update Shares", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					JOptionPane.showMessageDialog(null, 
							"Stock price and shares updated!", "Update Stock", JOptionPane.INFORMATION_MESSAGE);
					reset();
				}
				else if (jTabbedPane.getSelectedIndex()==1) {
					String password = String.valueOf(bankerPWField.getPassword());
					if (password.length() == 0)
						noPWMsg();
					else if (!password.equals(bank.getBankerPW())) {
						wrongPWMsg();
					}
					else {
						if (bank.deleteStock(stock.getName())) {
							super.backward();
						}
						else {
	    						JOptionPane.showMessageDialog(null, 
	    								"Cannot remove a stock with shares!", "Request Failed", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			/*}catch(Exception e) {
				requestFailMsg();
			}*/
		}
    }
    public void newStocks() {
    		JTextField jTextFieldName = new JTextField(10);
    		JTextField jTextFieldPrice = new JTextField(10);
    		JTextField jTextFieldShares = new JTextField(10);
    		JPanel jPanel = new JPanel();

    		jPanel.add(Box.createHorizontalStrut(15)); // a spacer
    		jPanel.add(new JLabel("Name: "));
    		jPanel.add(jTextFieldName);
    		jPanel.add(Box.createHorizontalStrut(15)); // a spacer
    		jPanel.add(new JLabel("Price: "));
    		jPanel.add(jTextFieldPrice);
    		jPanel.add(Box.createHorizontalStrut(15)); // a spacer
    		jPanel.add(new JLabel("Shares: "));
    		jPanel.add(jTextFieldShares);
    		
    		int result = JOptionPane.showConfirmDialog(null, jPanel,
    				"Add New Stock", JOptionPane.OK_CANCEL_OPTION);
    		if (result == JOptionPane.OK_OPTION) {
    			try {
    				String stockname = jTextFieldName.getText();
    				double price = Double.parseDouble(jTextFieldPrice.getText());
    				int shares = Integer.parseInt(jTextFieldShares.getText());
    				if (stockname.length() == 0 || (price <= 0) || (shares<=0)) {
    					requestFailMsg();
    				}
    				else if (bank.getStocks().get(stockname)!=null) {
    					JOptionPane.showMessageDialog(null, 
	                    		"This stock already exists!", "Request Failed", JOptionPane.ERROR_MESSAGE);
    				}
    				else {
    					bank.addNewStock(stockname, price, shares, 0);
    					JOptionPane.showMessageDialog(null, 
	                    		"Add new stock success!", "Add New Stock", JOptionPane.INFORMATION_MESSAGE);
    				}
    			}
    			catch(Exception e) {
    				requestFailMsg();
    			}
    		}
    }
	//events
    public void addEvent(ActionEvent e) {
		super.addEvent(e);
		for (int i=0; i<buttonNum; i++) {
			if (e.getSource().equals(buttons[i])) linkButton(i);
		}
    }
    public void setLinkButton(AtmPanel panel, int n) {
    		buttonLinks[n] = panel;
    }
    public void linkButton(int n) {
    		if (panelName.equals("main")||panelName.equals("openopt")
    				||panelName.equals("view")||panelName.equals("manager")
    				||panelName.equals("security")) {
    			if (panelName.equals("manager") && (n==0||n==1)) {
    				Calendar calendar = Calendar.getInstance();
				date = bank.getDate();

    				calendar.setTime(date);
    				calendar.add(Calendar.DATE, 1+n*2);
    				date = calendar.getTime();
				//update database date
				SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
				String[] updateValues = {formatter.format(date)};

				String[] args = {"DATE"};

				Database.updateData("BANKER","USERNAME", bank.getBankerName(), args, updateValues);
				date = bank.getDate();
				System.out.println(formatter.format(date));

    				bank.calculateLoanInterest(date);
				bank.addSaveInterest(1+n*2, date);
    				JOptionPane.showMessageDialog(null, 
                    		n==0?"One day added!":"Three days added!", "Update Date", JOptionPane.INFORMATION_MESSAGE);
    			}
    			else if (panelName.equals("manager") && (n==5)){
    				newStocks();
    			}
    			else if ((panelName.equals("view")&&(n==3))
    					||(panelName.equals("security")&&(n==1))) {
        			closeAccount();
        			super.backward();
        		}
    			else if (panelName.equals("openopt")&&(n==2) 
    					&& customer.getSecurityAccounts()!=null) {
    	            JOptionPane.showMessageDialog(null,
    	            		"Your can only have one security account!", 
    	            		"Request Failed", JOptionPane.ERROR_MESSAGE);
    			}
    			else if (panelName.equals("openopt")&&(n==2)
    					&&customer.getSavingsAccounts().size()==0) {
	        		JOptionPane.showMessageDialog(null, 
	        			"You do not have a savings account!", "Request Failed", JOptionPane.ERROR_MESSAGE);
	        }
    			else {
    				buttonLinks[n].setDate(date);
    				buttonLinks[n].setCustomer(customer);
    				buttonLinks[n].setAccount(account);
    				frame.addPanel(buttonLinks[n]);
    			}
    		}
    		else if (panelName.equals("withdraw")) {
    			if (n<5) {
    				if (this.quickVerify()) {
    					int withdraw = account.withdraw(20*(n+1), Currency.USD, date);
    					if (withdraw == 0) {
    						noMoneyMsg();
    					}
    					else {
    						JOptionPane.showMessageDialog(null, 
    								"Withdraw Money Finished!", 
    								"Withdraw money success", JOptionPane.INFORMATION_MESSAGE);
    						resetBalance();
    					}
    				}
    			}
    			else {
    				transactions();
    			}
    		}
    		else if (panelName.equals("deposit")) {
    			if (n<5) {
    				if (this.quickVerify()) {
            			account.save(20*(n+1), Currency.USD, date);
            			JOptionPane.showMessageDialog(null,
            					"Save Money Finished!", 
            					"Save money success", JOptionPane.INFORMATION_MESSAGE);
            			resetBalance();
    				}
        		}
        		else {
        			transactions();
        		}
    		}
    }
    public void reset() {//update data
		if (panelName.equals("main")||panelName.equals("loans")
				||panelName.equals("transfer")||panelName.equals("openopt")
				||panelName.equals("select")||panelName.equals("security")
				||panelName.equals("stocks")||panelName.equals("securitytrans"))
			resetName();
		else if (panelName.equals("deposit")||panelName.equals("withdraw")
				||panelName.equals("transactions")||panelName.equals("view")
				||panelName.equals("viewloans"))
    			resetBalance();
    		else if (panelName.equals("manager")||panelName.equals("daily")
    				||panelName.equals("accounts")||panelName.equals("viewstocks"))
    			resetEarned();
		if (panelName.equals("stocks")||panelName.equals("loans")
				||panelName.equals("accounts"))
			resetRadio();
    		if (panelName.equals("transfer"))
    			resetTransfer();
    		if (panelName.equals("transactions")||panelName.equals("securitytrans")) {
    			resetTransactions();
    		}
    		if (panelName.equals("loans")||panelName.equals("select")) {
    			resetSelect();
    		}
    		if (panelName.equals("daily")) {
    			resetDaily();
    		}
    		if (panelName.equals("accounts")) {
    			resetAccounts();
    		}
    		if (panelName.equals("buystocks")||panelName.equals("sellstocks")
    				|| panelName.equals("setstocks")) {
    			resetStocks();
    		}
    		if (panelName.equals("buystocks")||panelName.equals("sellstocks")) {
    			resetSelect();
    			enter.setText("");
    		}
    		if (panelName.equals("stocks")||panelName.equals("viewstocks") 
    				||panelName.equals("viewloans")||panelName.equals("setstocks")) {
    			resetTable();
    		}
    }
    public void forward() {
    		if (panelName.equals("select")) {
    			Account newAccount = (Account) accounts.getSelectedItem();
    			setAccount(newAccount);
    			if (newAccount != null) {
    				setAccount(newAccount);
    				if (this.quickVerify()) {
    					if (account instanceof SecurityAccount)
    						super.link();
    					else
    						super.forward();
    				}
    			}
    			else {
    				noAccountMsg();
    			}
    		}
    		else if (panelName.equals("transfer")) {
    			CheckingAccount accountFrom = (CheckingAccount) accounts.getSelectedItem();
    			CheckingAccount accountTo = (CheckingAccount) extraAccounts.getSelectedItem();
    			if (accountFrom == null || accountTo == null) {
    				noAccountMsg();
    			}
    			else
    				transactions();
    		}
    		else if (panelName.equals("loans")) {
    			if (!radio1.isSelected() && !radio2.isSelected()) {
    	            JOptionPane.showMessageDialog(null,
    	            		"Please select a loan transaction!", 
    	            		"Request Failed", JOptionPane.ERROR_MESSAGE);
    			}
    			else {
    				if (accounts.getSelectedItem() == null) {
					noAccountMsg();
					return;
				}
				else if (!(accounts.getSelectedItem() instanceof SavingsAccount)) {
					JOptionPane.showMessageDialog(null, 
							"Please select a savings account!", 
							"Request Failed", JOptionPane.ERROR_MESSAGE);
					return;
				}
    				else
    					transactions();
    			}
    		}
    		else if (panelName.equals("accounts")) {
    			if (!radio1.isSelected() && !radio2.isSelected()) {
    	            JOptionPane.showMessageDialog(null,
    	            			"Please select customer type!", 
    	            			"Request Failed", JOptionPane.ERROR_MESSAGE);
    			}
    			else {
    				Account account = (Account) accounts.getSelectedItem();
    				Customer customer = (Customer) customers.getSelectedItem();
        			if (account != null && customer != null) {
        				setAccount(account);
            			setCustomer(customer);
            			if (radio1.isSelected())
            				super.forward();
            			else
            				super.link();
        			}
        			else {
        				noAccountMsg();
        			}
    			}
    		}
    		else if (panelName.equals("buystocks")){
    			if (accounts.getSelectedItem() == null) {
				noAccountMsg();
				return;
			}
			else if (!(accounts.getSelectedItem() instanceof SavingsAccount)) {
				JOptionPane.showMessageDialog(null, 
						"Please select a savings account!", 
						"Request Failed", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				try {
					int shares = Integer.parseInt(enter.getText());
					if (shares<=0) {
						requestFailMsg();
					}
		    			else {
		    				int response = JOptionPane.showConfirmDialog(null, 
		    						"$"+CommonMathMethod.twoDecimal(shares*stock.getUSDPrice())+" in total, yes to purchase!", "Buy Stocks", 0);
		    		        if (response == JOptionPane.YES_OPTION) {
		    		        		dealshares = shares;
		    		        		transactions();
		    		        }
		    			}
				}catch (Exception e) {
					requestFailMsg();
				}
			}
    		}
    		else if (panelName.equals("sellstocks")){
    			if (accounts.getSelectedItem() == null) {
    				noAccountMsg();
    				return;
    			}
    			else if (!(accounts.getSelectedItem() instanceof SavingsAccount)) {
    				JOptionPane.showMessageDialog(null, 
    						"Please select a savings account!", 
    						"Request Failed", JOptionPane.ERROR_MESSAGE);
    				return;
    			}
    			else {
    				try {
    					int shares = Integer.parseInt(enter.getText());
    					if (shares<=0) {
    						requestFailMsg();
    					}
    		    			else {
    		    				int response = JOptionPane.showConfirmDialog(null, 
    		    						"$"+CommonMathMethod.twoDecimal(shares*stock.getUSDPrice())+" in total, yes to sell!", "Sell Stocks", 0);
    		    		        if (response == JOptionPane.YES_OPTION) {
    		    		        		dealshares = shares;
    		    		        		transactions();
    		    		        }
    		    			}
    				}catch (Exception e) {
    					requestFailMsg();
    				}
    			}
    		}
    		else if (panelName.equals("viewstocks")){
    			if (table.getSelectedRow()>-1) {
    				String stockname = table.getValueAt(table.getSelectedRow(), 0).toString();
    				setStock(bank.getStocks().get(stockname));
    				super.forward();
    			}
    			else
    				noStockMsg();
    		}
    		else if (panelName.equals("stocks")) {
    			if (table.getSelectedRow()>-1) {
					String stockname = table.getValueAt(table.getSelectedRow(), 0).toString();
				if (!radio1.isSelected() && !radio2.isSelected()) {
    	            		noStockMsg();
					return;
				}
				else if (radio1.isSelected()) {
    					setStock(customer.getSecurityAccounts().getStocks().get(stockname));
    				}
    				else {
        				setStock(bank.getStocks().get(stockname));
    				}
				if (radio2.isSelected())
    					super.forward();
				else
					super.link();
    			}
    			else
    				noStockMsg();
    		}
    		else if (panelName.equals("setstocks")) {
    			setStocks();
    		}
    		else
    			super.forward();
    }
    private void closeAccount() {
        int response = JOptionPane.showConfirmDialog(null, "You need to pay 5 USD to close this account, do you want to close it？(We will not return the remaining balances.)", "Close account", 0);
        if (response == JOptionPane.YES_OPTION) {
            customer.closeAccount(account, date);
            JOptionPane.showMessageDialog(null, 
            		"Close account successfully!", 
            		"Close Account", JOptionPane.INFORMATION_MESSAGE);
            super.backward();
        }
    }
    private void noAccountMsg() {
		JOptionPane.showMessageDialog(null,
				"Please select an account!"
				,"Request Failed", JOptionPane.ERROR_MESSAGE); 
    }
    private void requestFailMsg() {
    		JOptionPane.showMessageDialog(null,
				"Invalid input!",
				"Request Failed", JOptionPane.ERROR_MESSAGE);
    }
    private void wrongPWMsg() {
    		JOptionPane.showMessageDialog(null, 
				"Wrong password!", 
				"Request Failed", JOptionPane.ERROR_MESSAGE);
    }
    private void noInputMsg() {
    		JOptionPane.showMessageDialog(null,
				"Please input an amount!", 
				"Request Failed", JOptionPane.ERROR_MESSAGE);
    }
    private void noPWMsg() {
    		JOptionPane.showMessageDialog(null,
				"Please input password!", 
				"Request Failed", JOptionPane.ERROR_MESSAGE);
    }
    private void noMoneyMsg() {
    		JOptionPane.showMessageDialog(null, 
				"No enough money!", 
				"Request Failed", JOptionPane.ERROR_MESSAGE);
    }
    private void noStockMsg() {
		JOptionPane.showMessageDialog(null, 
			"Please select a stock!", 
			"Request Failed", JOptionPane.ERROR_MESSAGE);
    }
}