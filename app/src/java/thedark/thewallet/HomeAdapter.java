package thedark.thewallet;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeAdapter extends ArrayAdapter<Entry> {

    private ArrayList<Entry> entries;
    Context context;

    public HomeAdapter(ArrayList<Entry> data, Context context) {
        super(context, R.layout.home_list, data);
        entries = data;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        if ( item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.home_list,parent, false);
        }

        Entry currentEntry = entries.get( position);

        TextView typeView = (TextView) item.findViewById(R.id.textViewType);
        TextView detailsView = (TextView) item.findViewById(R.id.textView15);
        TextView amountView = (TextView) item.findViewById(R.id.textView13);
        TextView dateView = (TextView) item.findViewById(R.id.textViewDate);

        typeView.setText( currentEntry.getTypeName());
        detailsView.setText( currentEntry.getDetails());
        amountView.setText( currentEntry.getAmount() + "");
        dateView.setText( currentEntry.getDate());

        if ( currentEntry.getType() == 0 )
            amountView.setTextColor( Color.GREEN);
        else if ( currentEntry.getType() > 0 && currentEntry.getType() < 7 )
            amountView.setTextColor( Color.RED);

        return item;
    }
}
