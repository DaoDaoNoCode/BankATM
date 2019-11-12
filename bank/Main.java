package bank;
import gui.Initializer;
/**
 * This is the main class and the entrance of the program.
 */

public class Main {
    public static void main(String[] args) {
        if (!Database.hasDatabase("bank")) {
            Database.createDatabase("bank");
        }
        Bank bank = new Bank();
        //new WelcomeInterface(bank, new Date());
        Initializer newGUI = new Initializer(bank);
        newGUI.setLinks();
    }
}
