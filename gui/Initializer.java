package gui;
import java.util.Date;

import bank.Bank;

public class Initializer {
    StartPanel newStart;
    PWPanel newManagerLogin;
    PWPanel newPW;
    PWPanel newID;
    PWPanel newRegister;
    PWPanel newWelcome;
    PWPanel newChecking;
    PWPanel newSavings;
    PWPanel newSecurity;

    ButtonPanel newMain;
    ButtonPanel newOpenopt;
    SelectPanel newSelect;
    ButtonPanel newView;
    ButtonPanel newViewSecurity;
    ButtonPanel newWithdraw;
    ButtonPanel newDeposit;
    SelectPanel newTransfer;
    SelectPanel newLoans;
    TransactionPanel newCustomerTrans;
    TransactionPanel newBankerTrans;
    TablePanel newViewLoans;
    //MainPanel newSecurityTrans;
    ButtonPanel newManager;
    TransactionPanel newDaily;
    SelectPanel newAccounts;

    TablePanel newStocks;
    SelectPanel newSellStocks;
    SelectPanel newBuyStocks;
    TablePanel newViewStocks;
    TablePanel newSetStocks;

    public Initializer(Bank newBank) {
        //new panels
        AtmFrame newFrame = new AtmFrame();
        //Date newDate = new Date();
        Date newDate = newBank.getDate();
        newStart = new StartPanel(newFrame, newBank, newDate);
        newManagerLogin = new PWPanel("ManagerLogin", newFrame, newBank, newDate);
        newPW = new PWPanel("PW", newFrame, newBank, newDate);
        newID = new PWPanel("ID", newFrame, newBank, newDate);
        newRegister = new PWPanel("register", newFrame, newBank, newDate);
        newWelcome = new PWPanel("welcome", newFrame, newBank, newDate);
        newChecking = new PWPanel("checking", newFrame, newBank, newDate);
        newSavings = new PWPanel("savings", newFrame, newBank, newDate);
        newSecurity = new PWPanel("security", newFrame, newBank, newDate);

        newMain = new ButtonPanel("main", newFrame, newBank, newDate);
        newOpenopt = new ButtonPanel("openopt", newFrame, newBank, newDate);
        newSelect = new SelectPanel("select", newFrame, newBank, newDate);
        newView = new ButtonPanel("view", newFrame, newBank, newDate);
        newViewSecurity = new ButtonPanel("security", newFrame, newBank, newDate);
        newWithdraw = new ButtonPanel("withdraw", newFrame, newBank, newDate);
        newDeposit = new ButtonPanel("deposit", newFrame, newBank, newDate);
        newTransfer = new SelectPanel("transfer", newFrame, newBank, newDate);
        newLoans = new SelectPanel("loans", newFrame, newBank, newDate);
        newCustomerTrans = new TransactionPanel("transactions", newFrame, newBank, newDate);
        newBankerTrans = new TransactionPanel("transactions", newFrame, newBank, newDate);
        newViewLoans = new TablePanel("viewloans", newFrame, newBank, newDate);
        //newSecurityTrans = new MainPanel("securitytrans", newFrame, newBank, newDate);
        newManager = new ButtonPanel("manager", newFrame, newBank, newDate);
        newDaily = new TransactionPanel("daily", newFrame, newBank, newDate);
        newAccounts = new SelectPanel("accounts", newFrame, newBank, newDate);

        newStocks = new TablePanel("stocks", newFrame, newBank, newDate);
        newSellStocks = new SelectPanel("sellstocks", newFrame, newBank, newDate);
        newBuyStocks = new SelectPanel("buystocks", newFrame, newBank, newDate);
        newViewStocks = new TablePanel("viewstocks", newFrame, newBank, newDate);
        newSetStocks = new TablePanel("setstocks", newFrame, newBank, newDate);
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
        newManager.setLinkButton(newViewStocks, 4);
        newManager.setLinkButton(newManager, 5);
        newManager.setBack(newStart);
        newAccounts.setFore(newBankerTrans);
        newAccounts.setLink(newViewLoans);
        newAccounts.setBack(newManager);
        newDaily.setBack(newManager);
        newBankerTrans.setBack(newAccounts);
        newViewLoans.setBack(newAccounts);
        newViewStocks.setBack(newManager);
        newViewStocks.setFore(newSetStocks);
        newSetStocks.setBack(newViewStocks);

        newMain.setBack(newStart);
        newMain.setLinkButton(newOpenopt, 0);
        newMain.setLinkButton(newSelect, 1);
        newMain.setLinkButton(newTransfer, 2);
        newMain.setLinkButton(newLoans, 3);
        newOpenopt.setLinkButton(newChecking, 0);
        newOpenopt.setLinkButton(newSavings, 1);
        newOpenopt.setLinkButton(newSecurity, 2);
        //newOpenopt.setLinkButton(newSecurity, 2);
        newOpenopt.setBack(newMain);
        newChecking.setFore(newMain);
        newChecking.setBack(newOpenopt);
        newSavings.setFore(newMain);
        newSavings.setBack(newOpenopt);
        newSecurity.setFore(newMain);
        newSecurity.setBack(newOpenopt);
        newSelect.setBack(newMain);
        newSelect.setFore(newView);
        newSelect.setLink(newViewSecurity);
        newViewSecurity.setBack(newMain);
        newViewSecurity.setLinkButton(newStocks, 0);
        //newViewSecurity.setLinkButton(newSecurityTrans, 1);
        //newSecurityTrans.setBack(newViewSecurity);
        newStocks.setBack(newViewSecurity);
        newStocks.setFore(newBuyStocks);
        newStocks.setLink(newSellStocks);
        newBuyStocks.setBack(newStocks);
        newSellStocks.setBack(newStocks);

        newView.setBack(newMain);
        newView.setLinkButton(newWithdraw, 0);
        newView.setLinkButton(newDeposit, 1);
        newView.setLinkButton(newCustomerTrans, 2);
        newWithdraw.setBack(newView);
        newDeposit.setBack(newView);
        newCustomerTrans.setBack(newView);
        newTransfer.setBack(newMain);
        newLoans.setBack(newMain);
    }
}
