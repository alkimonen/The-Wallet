package thedark.thewallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MonthNameAdapter extends ArrayAdapter<String> {

    private ArrayList<String> names;
    private Context context;

    public MonthNameAdapter( Context context, ArrayList<String> data) {
        super(context, R.layout.month_name_list, data);
        names = data;
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        if ( item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.month_name_list,parent, false);
        }

        String name = names.get( position);

        TextView textView = (TextView) item.findViewById(R.id.textView);

        textView.setText( name);

        return item;
    }
}
