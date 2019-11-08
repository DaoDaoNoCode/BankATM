import java.util.Date;

public class Initializer {
	StartPanel newStart;
	PWPanel newManagerLogin;
	PWPanel newPW;
	PWPanel newID;
	PWPanel newRegister;
	PWPanel newWelcome;
	PWPanel newChecking;
	PWPanel newSavings;
	
	MainPanel newMain;
	MainPanel newOpenopt;
	MainPanel newSelect;
	MainPanel newView;
	MainPanel newWithdraw;
	MainPanel newDeposit;
	MainPanel newTransfer;
	MainPanel newLoans;
	MainPanel newCustomerInquiry;
	MainPanel newBankerInquiry;
	MainPanel newManager;
	MainPanel newDaily;
	MainPanel newAccounts;
	public Initializer(Bank newBank) {
		//new panels
		AtmFrame newFrame = new AtmFrame();
		Date newDate = new Date();
		newStart = new StartPanel(newFrame, newBank, newDate);
		newManagerLogin = new PWPanel("ManagerLogin", newFrame, newBank, newDate);
		newPW = new PWPanel("PW", newFrame, newBank, newDate);
		newID = new PWPanel("ID", newFrame, newBank, newDate);
		newRegister = new PWPanel("register", newFrame, newBank, newDate);
		newWelcome = new PWPanel("welcome", newFrame, newBank, newDate);
		newChecking = new PWPanel("checking", newFrame, newBank, newDate);
		newSavings = new PWPanel("savings", newFrame, newBank, newDate);
		
		newMain = new MainPanel("main", newFrame, newBank, newDate);
		newOpenopt = new MainPanel("openopt", newFrame, newBank, newDate);
		newSelect = new MainPanel("select", newFrame, newBank, newDate);
		newView = new MainPanel("view", newFrame, newBank, newDate);
		newWithdraw = new MainPanel("withdraw", newFrame, newBank, newDate);
		newDeposit = new MainPanel("deposit", newFrame, newBank, newDate);
		newTransfer = new MainPanel("transfer", newFrame, newBank, newDate);
		newLoans = new MainPanel("loans", newFrame, newBank, newDate);
		newCustomerInquiry = new MainPanel("inquiry", newFrame, newBank, newDate);
		newBankerInquiry = new MainPanel("inquiry", newFrame, newBank, newDate);
		newManager = new MainPanel("manager", newFrame, newBank, newDate);
		newDaily = new MainPanel("daily", newFrame, newBank, newDate);
		newAccounts = new MainPanel("accounts", newFrame, newBank, newDate);
		newFrame.setPanel(newWelcome);
	}
	public void setLinks() {
		//set forwards&backwards
		newWelcome.setFore(newStart);
		newStart.setFore(newMain);
		newStart.setForgot(newID);
		newStart.setOpen(newRegister);
		newStart.setBanker(newManagerLogin);
		newStart.setBack(newWelcome);
		newID.setBack(newStart);
		newID.setFore(newPW);
		newPW.setFore(newStart);	
		newPW.setBack(newID);
		newRegister.setBack(newStart);
		newRegister.setFore(newStart);
		
		newManagerLogin.setFore(newManager);
		newManagerLogin.setBack(newStart);
		newManager.setLinkButton(newManager, 0);
		newManager.setLinkButton(newManager, 1);
		newManager.setLinkButton(newDaily, 2);
		newManager.setLinkButton(newAccounts, 3);
		newManager.setBack(newStart);
		newAccounts.setFore(newBankerInquiry);
		newAccounts.setBack(newManager);
		newDaily.setBack(newManager);
		newBankerInquiry.setBack(newAccounts);
	
		newMain.setBack(newStart);
		newMain.setLinkButton(newOpenopt, 0);
		newMain.setLinkButton(newSelect, 1);
		newMain.setLinkButton(newTransfer, 2);
		newMain.setLinkButton(newLoans, 3);
		newOpenopt.setLinkButton(newChecking, 0);
		newOpenopt.setLinkButton(newSavings, 1);
		newOpenopt.setLinkButton(newOpenopt, 2);
		//newOpenopt.setLinkButton(newSecurity, 2);
		newOpenopt.setBack(newMain);
		newChecking.setFore(newMain);
		newChecking.setBack(newOpenopt);
		newSavings.setFore(newMain);
		newSavings.setBack(newOpenopt);
		newSelect.setBack(newMain);
		newSelect.setFore(newView);
		newView.setBack(newMain);
		newView.setLinkButton(newWithdraw, 0);
		newView.setLinkButton(newDeposit, 1);
		newView.setLinkButton(newCustomerInquiry, 2);
		newWithdraw.setBack(newView);
		newDeposit.setBack(newView);
		newCustomerInquiry.setBack(newView);
		newTransfer.setBack(newMain);
		newLoans.setBack(newMain);
	}
}
