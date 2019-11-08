import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	private JLabel[] balance;
	private JLabel label1, label2;
	private JComboBox<Account> accounts;
	
	//transfer component
    private DefaultComboBoxModel<Account> model;
    private JComboBox<Customer> customers;
    private JComboBox<Account> extraAccounts;
    
	//table component
	private JTextArea record;
	private JScrollPane jsp;
	//private DefaultTableModel data;
	
	private JRadioButton radio1, radio2;
	
	private final String[] currencySign = {"$", "¥", "€"};
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
			String[] names = {"Checking", "Savings", "Securiry"};
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
			String[] names = {"Withdrawal", "Deposit", "Balance Inquiry", "Close Account"};
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
		case "inquiry":{
			setTitle("Recent Transactions");
			showBalance();
			record = new JTextArea();
			jsp = new JScrollPane(record);
			showTransactions();
			break;
		}
		case "manager":{
			String[] names = {"Add 1 Day", "Add 3 Days", "Transactions", "Daily Report"};
			int[] sizes = {17, 17, 15, 15};
			setButtons(names, sizes, 4);
			buttonNum = 4;
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
	public void setTitle(String s) {
		if (title!=null)
			remove(title);
		title = new JLabel(s);
		title.setBounds(240, 50, 300, 100);
		title.setFont(new Font("calibri",Font.BOLD, 20));
		title.setFocusable(false);
		add(title);
	}
	public void showBalance() {
		name = new JLabel("#");
		balance = new JLabel[3];
		balance[0] = new JLabel("#");
		balance[1] = new JLabel("#");
		balance[2] = new JLabel("#");
		JLabel usd = new JLabel("USD");
		JLabel cny = new JLabel("CNY");
		JLabel eur = new JLabel("EUR");
		
		if (customer!=null)
			resetBalance();
		
		name.setBounds(30, 110, 200, 100);
		name.setFont(new Font("calibri",Font.BOLD, 20));
		name.setForeground(Color.gray);
		name.setFocusable(false);
		usd.setBounds(30, 150, 200, 100);
		usd.setFont(new Font("calibri",Font.BOLD, 15));
		usd.setFocusable(false);
		balance[0].setBounds(30, 180, 200, 100);
		balance[0].setFont(new Font("calibri",Font.BOLD, 20));
		balance[0].setForeground(Color.gray);
		balance[0].setFocusable(false);
		cny.setBounds(30, 215, 200, 100);
		cny.setFont(new Font("calibri",Font.BOLD, 15));
		cny.setFocusable(false);
		balance[1].setBounds(30, 245, 200, 100);
		balance[1].setFont(new Font("calibri",Font.BOLD, 20));
		balance[1].setForeground(Color.gray);
		balance[1].setFocusable(false);
		eur.setBounds(30, 280, 200, 100);
		eur.setFont(new Font("calibri",Font.BOLD, 15));
		eur.setFocusable(false);
		balance[2].setBounds(30, 310, 200, 100);
		balance[2].setFont(new Font("calibri",Font.BOLD, 20));
		balance[2].setForeground(Color.gray);
		balance[2].setFocusable(false);

		bg.add(name);
		bg.add(usd);
		bg.add(cny);
		bg.add(eur);
		bg.add(balance[0]);
		bg.add(balance[1]);
		bg.add(balance[2]);
	}
	public void showName() {
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
	public void showTransactions() {
		record.setBounds(245, 145, 475, 340);
		record.setBackground(Color.decode("#F7F7F7"));
		record.setFocusable(false);
        jsp.setBounds(240, 140, 500, 350);//horizon?
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(jsp);
	}
	public void setButtons(String[] words, int[] sizes, int num) {
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
	public void textAndBox(String line) {
		label1 = new JLabel(line);
		label1.setFont(new Font("calibri",Font.BOLD, 20));
		label1.setBounds(260, 130, 300, 100);
		accounts = new JComboBox<>();
		accounts.setBounds(260, 200, 250, 100);
		add(label1);
		add(accounts);
	}
	public void setRadio(String text1, String text2) {
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
                if (panelName.equals("accounts"))
                		reset();
            }
        	});
		radio2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radio1.setSelected(false);
                if (panelName.equals("accounts"))
                		reset();
            }
        	});
		add(radio1);
		add(radio2);
	}
	//reset functions
	public void resetTransactions() {
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
	public void resetDaily() {
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
	public void resetEarned() {
		name.setText("Total Earned");
        HashMap<Currency, Double> moneyEarned = bank.calculateMoneyEarned();
        int i = 0;
        for (Currency currency : Currency.values()) {
        		balance[i].setText(currencySign[i]+moneyEarned.get(currency));
        		i++;
        }
	}
	public void resetBalance() {
		resetName();
		int i = 0;
        for (Currency currency : Currency.values()) {
            balance[i].setText(currencySign[i]+account.getDeposit(currency));
            i++;
        }
        setTitle(account.getType().toString()+" - ●●●●"+account.getNumber().substring(8));
	}
	public void resetName() {
		name.setText(customer.getUsername());
	}
	public void resetSelect() {
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
	}
	public void resetTransfer() {
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
	public void resetAccounts() {
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
    public void transactions() {
		JTextField jTextFieldMoney = new JTextField(10);
		JPasswordField jPasswordFieldPassword = new JPasswordField(10);
		JComboBox<Currency> jComboBoxCurrency = new JComboBox<>();
		for (Currency currency : Currency.values())
        jComboBoxCurrency.addItem(currency);

		JPanel jPanel = new JPanel();
		if (panelName!="loans" || radio1.isSelected()) {
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
				if ((panelName!="loans" || radio1.isSelected())
						&&jTextFieldMoney.getText().length()==0) {
 					noInputMsg();
				}
				else if (password.length()==0) {
					noPWMsg();
				}
				else if (!account.isPasswordRight(password)) 
    					wrongPWMsg();
				else {
            			switch(panelName) {
            			case "deposit":{
            				double money = Double.valueOf(jTextFieldMoney.getText());
            				account.save(money, currency, date);
            				JOptionPane.showMessageDialog(null,
            						"Save Money Finished!", 
            						"Save money success", JOptionPane.INFORMATION_MESSAGE);
            				break;
            			}
            			case "withdraw":{
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
        					break;
            			}
            			case "transfer":{
            				double money = Double.valueOf(jTextFieldMoney.getText());
            				CheckingAccount accountFrom = (CheckingAccount) accounts.getSelectedItem();
            				CheckingAccount accountTo = (CheckingAccount) extraAccounts.getSelectedItem();
            				int transfer = accountFrom.transferOut(accountTo, money, currency, date);
            				if (transfer == -1) {
            					JOptionPane.showMessageDialog(null, 
            							"Transfer must be more than 0!",
            							"Request Failed", JOptionPane.ERROR_MESSAGE);
            				} else if (transfer == 0) {
            					noMoneyMsg();
            				} else {
            					JOptionPane.showMessageDialog(null, 
            							"Transfer Money Finished!", 
            							"Transfer money success", JOptionPane.INFORMATION_MESSAGE);
            				}
            				break;
            			}
            			case "loans":{
            				SavingsAccount savings = (SavingsAccount) accounts.getSelectedItem();
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
    public boolean quickVerify(boolean isSave) {//verify password
    		JPasswordField jPasswordFieldPassword = new JPasswordField(15);
    		JPanel jPanel = new JPanel();
    		jPanel.add(new JLabel("Password: "));
    		jPanel.add(jPasswordFieldPassword);
    		int result = JOptionPane.showConfirmDialog(null, jPanel,
    				"Quick "+(isSave?"Deposit":"Withdraw"), 
    						JOptionPane.OK_CANCEL_OPTION);
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
    				||panelName.equals("view")||panelName.equals("manager")) {
    			if (panelName.equals("manager") && (n==0||n==1)) {
    				Calendar calendar = Calendar.getInstance();
    				calendar.setTime(date);
    				calendar.add(Calendar.DATE, 1+n*2);
    				date = calendar.getTime();
    				bank.calculateLoanInterest(date);
    				bank.addSaveInterest(1, date);
    				JOptionPane.showMessageDialog(null, 
                    		n==0?"One day added!":"Three days added!", "Update Date", JOptionPane.INFORMATION_MESSAGE);
    			}
    			else if (panelName.equals("view")&&(n==3)) {
        			closeAccount();
        			super.backward();
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
    				if (this.quickVerify(false)) {
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
    				if (this.quickVerify(true)) {
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
				||panelName.equals("select"))
			resetName();
		else if (panelName.equals("deposit")||panelName.equals("withdraw")
				||panelName.equals("inquiry")||panelName.equals("view"))
    			resetBalance();
    		else if (panelName.equals("manager")||panelName.equals("daily")
    				||panelName.equals("accounts"))
    			resetEarned();
    		if (panelName.equals("transfer"))
    			resetTransfer();
    		if (panelName.equals("inquiry")) {
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
    }
    public void forward() {
    		if (panelName.equals("select")) {
    			Account account = (Account) accounts.getSelectedItem();
    			setAccount(account);
    			if (account != null) {
    				setAccount(account);
    				super.forward();
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
        				super.forward();
        			}
        			else {
        				noAccountMsg();
        			}
    			}
    		}
    		else
    			super.forward();
    }
    public void closeAccount() {
        int response = JOptionPane.showConfirmDialog(null, "You need to pay 5 USD to close this account, do you want to close it？(We will not return the remaining balances.)", "Close account", 0);
        if (response == JOptionPane.YES_OPTION) {
            customer.closeAccount(account, date);
            JOptionPane.showMessageDialog(null, 
            		"Close account successfully!", 
            		"Close Account", JOptionPane.INFORMATION_MESSAGE);
            super.backward();
        }
    }
    public void noAccountMsg() {
		JOptionPane.showMessageDialog(null,
				"Please select an account."
				,"Request Failed", JOptionPane.ERROR_MESSAGE); 
    }
    public void requestFailMsg() {
    		JOptionPane.showMessageDialog(null,
				"Invalid request.",
				"Request Failed", JOptionPane.ERROR_MESSAGE);
    }
    public void wrongPWMsg() {
    		JOptionPane.showMessageDialog(null, 
				"Wrong password!", 
				"Request Failed", JOptionPane.ERROR_MESSAGE);
    }
    public void noInputMsg() {
    		JOptionPane.showMessageDialog(null,
				"Please input an amount!", 
				"Request Failed", JOptionPane.ERROR_MESSAGE);
    }
    public void noPWMsg() {
    		JOptionPane.showMessageDialog(null,
				"Please input password!", 
				"Request Failed", JOptionPane.ERROR_MESSAGE);
    }
    public void noMoneyMsg() {
    		JOptionPane.showMessageDialog(null, 
				"No enough money!", 
				"Request Failed", JOptionPane.ERROR_MESSAGE);
    }
}