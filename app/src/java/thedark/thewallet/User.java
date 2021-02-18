package thedark.thewallet;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private double balance;
    private String name;
    private String password;
    private Month currentMonth;
    private ArrayList<Entry> entries;
    private ArrayList<Entry> regulars;
    private ArrayList<Month> months;
    private ArrayList<CreditCard> cards;

    public User( String name, String password, double balance)
    {
        this.name = name;
        this.password = password;
        this.balance = balance;

        currentMonth = new Month();

        entries = new ArrayList<Entry>();
        regulars = new ArrayList<Entry>();
        months = new ArrayList<Month>();
        cards = new ArrayList<CreditCard>();

    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public ArrayList<Entry> getRegulars() {
        return regulars;
    }

    public ArrayList<CreditCard> getCreditCards() { return cards; }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Month> getMonths() {
        return months;
    }

    public Month getCurrentMonth() {
        return currentMonth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addEntry( Entry entry) {
        if ( entry.isRegular() && !regulars.contains( entry)) {
            entry.updateDate();
            regulars.add( entry);
        }
        else entry.updateDate();

        entries.add(0, entry);
        currentMonth.addEntry( entry);

        if ( entry.getType() == 0 )
            balance = balance + entry.getAmount();
        else
            balance = balance - entry.getAmount();
    }

    public void changeMonth() {
        months.add(0, currentMonth);
        currentMonth = new Month();
    }

    public void addCreditCard( String name, int dueDate, int expMonth, int expYear, int limit) {
        cards.add( new CreditCard( name, dueDate, expMonth, expYear, limit));
    }

    public void addCardExpense( int order, Entry entry) {
        cards.get( order).addEntry( entry);
    }

}
