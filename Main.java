import java.util.Date;

/**
 * This is the main class and the entrance of the program.
 */

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        new WelcomeInterface(bank, new Date());
    }
}
