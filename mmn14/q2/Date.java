import java.io.Serializable;
import java.util.Objects;

/**
 * Date logic class
 */
public class Date implements Serializable {
//    private static final long serialVersionUID = 1L;
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(Date other) {
        this.day = other.day;
        this.month = other.month;
        this.year = other.year;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Date date))
            return false;
        return day == date.day && month == date.month && year == date.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    /**
     * Static utility function that gets a year and a month,
     * and returns how many days are in that month.
     */
    public static int daysInMonth(int year, int month) {
        return switch (month) {
            case 2 -> (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
                    ? 29
                    : 28;
            case 4, 6, 9, 11 -> 30;
            default -> 31;
        };
    }
}
