package thedark.thewallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CreditCardAdapter extends ArrayAdapter<CreditCard> {

    private ArrayList<CreditCard> cards;
    Context context;

    public CreditCardAdapter(ArrayList<CreditCard> data, Context context) {
        super(context, R.layout.home_list, data);
        cards = data;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        if ( item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.credit_cards_list,parent, false);
        }

        CreditCard currentCreditCard = cards.get( position);

        TextView nameView = (TextView) item.findViewById(R.id.textView19);
        TextView limitView = (TextView) item.findViewById(R.id.textView20);
        TextView expDateView = (TextView) item.findViewById(R.id.textView21);
        TextView balanceView = (TextView) item.findViewById(R.id.textView22);

        nameView.setText( currentCreditCard.getName());
        limitView.setText( "Limit: " + currentCreditCard.getLimit());
        expDateView.setText( "Exp. Date: " + currentCreditCard.getExpDay());
        balanceView.setText( currentCreditCard.getExpenses() + "");

        return item;
    }
}
