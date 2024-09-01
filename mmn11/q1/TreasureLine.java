public class TreasureLine {
    private TreasureItem item;
    private int count;
    public TreasureLine(TreasureItem item, int count) {
        this.item = item;
        this.count = count;
    }
    public int getSum() {
        return item.getPrice() * count;
    }
    public String toString() {
        return count + " " + item.getName() + "\t* " + item.getPrice() + " coins per item\t= " + getSum() + "coins. ";
    }
}
