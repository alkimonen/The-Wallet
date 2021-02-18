package thedark.thewallet;

import java.io.Serializable;
import java.util.ArrayList;

public class CreditCard implements Serializable{

    private String name;
    private int dueDay;
    private String expDay;
    private double limit;
    private double expenses;
    private ArrayList<Entry> entries;

    public CreditCard(String name, int dueDay, int expMonth, int expYear, int limit)
    {
        this.name = name;
        this.dueDay = dueDay;
        expDay = expMonth + "/" + expYear;
        this.limit = limit;
        expenses = 0;
        entries = new ArrayList<Entry>();
    }

    public void deleteData() {
        expenses = 0;
        entries = new ArrayList<Entry>();
    }

    public void addEntry( Entry entry) {
        expenses = expenses + entry.getAmount();
        entries.add( entry);
    }

    public String getName() {
        return name;
    }

    public double getExpenses() {
        return expenses;
    }

    public int getDueDay() {
        return dueDay;
    }

    public String getExpDay() {
        return expDay;
    }

    public double getLimit() {
        return limit;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
