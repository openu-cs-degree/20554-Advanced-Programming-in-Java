import java.util.ArrayList;

public class TreasureBox {
    private int coins;
    private ArrayList<TreasureLine> items;
    private final int lineLength = 50;
    public TreasureBox() {
        this(0);
    }
    public TreasureBox(int coins) {
        this.coins = coins;
        items = new ArrayList<TreasureLine>();
    }
    public void addItem(TreasureItem item, int count) {
        items.add(new TreasureLine(item,count));
    }

    private String invoiceLine(boolean filled) {
        String line = "";
        line += "*";
        for (int i = 0; i < lineLength - 2; i++) line += (filled ? "*" : " ");
        line += "\n";
        return line;
    }
    private int getNumOfDigits(int num) {
        int count = 0;
        while (num > 0) {
            num /= 10;
            count++;
        }
        return count;
    }
    // I KNOW, I know, the "customerName" was unspecified in the Maman's instructions... but it fits so well :)
    public String getItems(String customerName) {
        String s = invoiceLine(true);
        // add title
        s += "* Invoice of Mr/Mrs " + customerName + ":\n";

        // add items
        for (int i = 0; i < items.size(); i++) {
            s += "* " + (i+1) + ". " + items.get(i).toString();
            for (int j = 0; j < lineLength - items.get(i).toString().length() - 7; j++) s += " ";
            s += "\n";
        }

        // add sum
        s += invoiceLine(false);
        s += "* Total cost: " + getTotalPrice() + " gold coins. ";
        for (int j = 0; j < lineLength - 30 - getNumOfDigits(getTotalPrice()); j++) s += " ";
        s += "\n";
        s += invoiceLine(true);

        return s;
    }
    public int getTotalPrice() {
        int price = 0;
        for (TreasureLine line : items) {
            price += line.getSum();
        }
        return price;
    }
    public int pay(int payment) {
        int difference = payment - getTotalPrice();
        // if difference < 0...
        coins += getTotalPrice();
        items.clear();
        return difference;
    }
    public int getCoins() {
        return coins;
    }
}
