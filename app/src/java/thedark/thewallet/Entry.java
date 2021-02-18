package thedark.thewallet;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Entry implements Serializable{
    private final String[] TYPES = { "Income", "Shopping Expense", "Transportation Expense", "Food Expense",
    "Clothing Expense", "Bill Expense", "Other Expense" };
    private int type;
    private String details;  // from user
    private double amount;
    private boolean regular;
    private int[] date;  // [ day, month, year]

    public Entry ( int type, double amount, String details, boolean regular) {
        updateDate();

        this.type = type;
        this.amount = amount;
        this.details = details;
        this.regular = regular;
    }

    public int getType() {
        return type;
    }

    public String getTypeName() {
        return TYPES[type];
    }

    public String getDetails() {
        return details;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isRegular() {
        return regular;
    }

    public int getDay() {
        return date[0];
    }

    public int getMonth() {
        return date[1];
    }

    public int getYear() {
        return date[2];
    }

    public String getDate() {
        return getDay() + "." + getMonth() + "." + getYear();
    }

    public void setDetails( String newDetails) {
        details = newDetails;
    }

    public void setRegular( boolean regular) {
        this.regular = regular;
    }

    public void updateDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        date = new int[]{calendar.get(Calendar.DAY_OF_MONTH), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.YEAR)};

    }
}
