package thedark.thewallet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Month implements Serializable{

    private final String[] MONTHS = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private double balance;
    private double incomes;
    private double expenses;
    private String date;
    private ArrayList<Entry> entries;

    public Month() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( new Date());

        date = MONTHS[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR);

        entries = new ArrayList<Entry>();

        balance = 0;
        incomes = 0;
        expenses = 0;
    }

    public double getBalance() {
        return balance;
    }

    public double getIncomes() {
        return incomes;
    }

    public double getExpenses() {
        return expenses;
    }

    public void addEntry(Entry entry)
    {
        entries.add( 0, entry);

        if ( entry.getType() == 0 )
        {
            balance = balance + entry.getAmount();
            incomes = incomes + entry.getAmount();
        }
        else
        {
            balance = balance - entry.getAmount();
            expenses = expenses - entry.getAmount();
        }
    }

    public String getDate()
    {
        return date;
    }

    public ArrayList<Entry> getList( int type) {
        ArrayList<Entry> arrayList;
        arrayList = new ArrayList<Entry>();

        for ( Entry entry: entries) {
            if ( entry.getType() == type) {
                arrayList.add( entry);
            }
        }
        return arrayList;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

}
