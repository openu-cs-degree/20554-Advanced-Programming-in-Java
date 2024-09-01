import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Scanner;

public class Main implements KeyListener {
    static Scanner scan;
    static Random rand;
    static TreasureBox treasureBox;
    static boolean isSpacePressed = false; // doesn't work, I don't like Java's IO :(
    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec(new String[]{"clear"});
        } catch (Exception ignored) {}
    }
    private static void pressEnterKey() {
        System.out.println("(press the ENTER key to continue...)");
        try {
            System.in.read();
            scan.nextLine();
        } catch(Exception ignored) {}
        clearConsole();
    }
    private static void realTimeType(String s) {
        realTimeType(true, s);
    }
    private static void realTimeType(boolean printLine, String s) {
        for (char c : s.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(isSpacePressed ? 2 : ((rand.nextInt(50) > 48 ? 150 : rand.nextInt(25)) + 10)); }
            catch (Exception ignored) {}
        }
        if (printLine) {
            System.out.println();
            try {
                Thread.sleep(rand.nextInt(50) + 450);
            } catch (Exception ignored) {
            }
        }
    }
    private static String generateFunnyName() {
        final String[] funnyNames = {"Blackbeard", "Bill Bones", "Wainwright 'Bird Eye' Shelley", "John Blackeye", "Poopdick", "Sir McPirate", "Captain McKraken", "John", "Daisy O'Jelly", "Ol' Chipper"};
        return funnyNames[rand.nextInt(funnyNames.length)];
    }
    private static char getContinueChar() {
        String s = scan.nextLine();
        if (s.isEmpty()) {
            return 'Y';
        } else {
            return s.charAt(0);
        }
    }

    public static void main(String[] args) {
        // declare variables
        scan = new Scanner(System.in);
        rand = new Random();
        int payment;
        char continueChar = 'Y'; // Y/n prompt
        String productName;
        int productAmount;
        int productPrice;
        String customerName;

        // prologue (initialization)
        clearConsole();
        realTimeType("Ahoy there, and welcome to Haifa's greatest pirate's bay!");
        realTimeType("In here, thy might find all kinds of piratish accessories, argh!!");
        realTimeType("Oh, wait... are you the new cashier we hired? Oh great, " +
                "the last one lost both of his eyes because SOMEONE couldn't fight the urge to use his new NUNCHAKUS " +
                "all over the place, ahm ahm [staring furiously at McBeardy]... uh, argh...");
        realTimeType("\nAnyway, where were I? Arrrgh yes, here's your new favorite friend, the cash register!");
        realTimeType(false, "C'mon, open it! Tell me Mr. stinky-sea-rat, what is the initial amount of gold coins that you see there?\n[type initial amount...] ");
        treasureBox = new TreasureBox(Integer.parseInt(scan.nextLine()));
        realTimeType("Great, and I see that your first customer is already arriving, I'll leave you both alone and return " +
                "to my life of looting and pillaging. Have fun there Mr. sea-rat, arrrrgh!!!");
        pressEnterKey();

        // main loop
        while(continueChar != 'n') {
            // introduction
            customerName = generateFunnyName();
            realTimeType("Ahoy, sea-rat! my name is " + customerName + ", nice to meet you!");
            realTimeType("There are some fine goods in this shop of your!");

            // purchase items
            while (continueChar != 'n') {
                // item details
                realTimeType(false, "I would like to purchase some... arrgh, how do you call it... [type product name...] ");
                productName = scan.nextLine();
                realTimeType("Yes! I would like to purchase some " + productName + ".");
                productPrice = rand.nextInt(50) + 1;
                realTimeType("Yes I know that is would cost me " + productPrice + " gold coin(s), no need to remind me!");
                realTimeType(false, "I still want to purchase it argh!! A few of those actually! to be precise: [type amount] ");
                productAmount = Integer.parseInt(scan.nextLine());
                treasureBox.addItem(new TreasureItem(productName, productPrice), productAmount);
                realTimeType("Exactly, " + productAmount + " " + productName + ", that is my order! Urgh...");

                // prompt for next item
                realTimeType(false, "What say you, do I want to purchase another item? Well, uh... [Y/n] ");
                continueChar = getContinueChar();

                // proceed to next item
                if (continueChar != 'n') {
                    realTimeType("Of course I want another item, what a question aarrrgggghhhh!!!!");
                    pressEnterKey();
                }
            }

            // view invoice
            realTimeType("Nuh, that's all for today, I'm out of money anyway!!");
            realTimeType("Talking about money, lemme see this invoice of mine...");
            realTimeType(treasureBox.getItems(customerName));
            pressEnterKey();

            // pay
            realTimeType("HOLY NEPTUNE LORD OF THE SEVEN SEAS!!!! " + treasureBox.getTotalPrice() + " bloody coins!!!!");
            realTimeType("Alright here's your money, you dirty sea-rat...");
            realTimeType(false, "Well actually it has been a pretty good pillaging day, imma be generous and give ya [enter payment...] ");
            payment = Integer.parseInt(scan.nextLine());
            while (payment < treasureBox.getTotalPrice()) {
                clearConsole();
                realTimeType("[narrator:] Look, I know that you're a pirate, but stealing is no good!");
                realTimeType(false, "[narrator:] Please enter a valid payment that's bigger than " + treasureBox.getTotalPrice() + ": ");
                payment = Integer.parseInt(scan.nextLine());
            }
            clearConsole();
            realTimeType("Yes, I shall give ya " + payment + " gold coins, you earned it you ol' dirty sea-rat!!");
            realTimeType("Oh, I'm actually get my " + treasureBox.pay(payment) + " coins of change back? No tips today?");
            realTimeType("'ight, this are the rules of the Maman, I see... well then, have a nice day Mr. sea-rat!");
            realTimeType(false, "By the way, I see another customer on his way in... are you still open? [Y/n] ");
            continueChar = getContinueChar();

            // customer leaving
            realTimeType("Okay, I shall tell him this on my way out. Bye bye, sea-rat!");
            pressEnterKey();
        }

        // epilogue
        realTimeType("Ahoy there, Mr. sea-rat! It's me and McBeardy here again!!");
        realTimeType("Have you had a fine day at the shop? Agyagyagyagya!!!");
        realTimeType("Let's open the treasure box and sea how much have you earned today...");
        realTimeType("Wow!! " + treasureBox.getCoins() + " coins in one day!!! We sure wanna keep you employed!");
        realTimeType("You even worth the insurance money that we don't pay for you!! What a treasure (wink wink)");
        pressEnterKey();
        realTimeType("'ight, now go to sleep we want to see you tomorrow first thing in the morning!");
        realTimeType("Now get out of my sight sea-rat, AARRRRRRRGGHHHHH!!!!!!!");
        pressEnterKey();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isSpacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isSpacePressed = false;
        }
    }
}
