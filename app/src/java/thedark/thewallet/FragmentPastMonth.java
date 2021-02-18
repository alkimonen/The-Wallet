package thedark.thewallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class FragmentPastMonth extends Fragment {

    private User user;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_past_month,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewMonthName = (TextView) view.findViewById(R.id.textViewMonthName);
        TextView textView0 = (TextView) view.findViewById(R.id.textViewCardName);
        TextView textView1 = (TextView) view.findViewById(R.id.textView16);
        TextView textView2 = (TextView) view.findViewById(R.id.textView18);
        TextView textView3 = (TextView) view.findViewById(R.id.textView23);
        TextView textView4 = (TextView) view.findViewById(R.id.textView24);
        TextView textView5 = (TextView) view.findViewById(R.id.textView25);
        TextView textView6 = (TextView) view.findViewById(R.id.textView26);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("position", 0);
        }

        try {
            FileInputStream fis = getActivity().openFileInput( "user.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            user = (User) is.readObject();
            is.close();
            fis.close();
        } catch ( Exception e){ }

        ArrayList<Double> total = new ArrayList<Double>();

        Month month = user.getMonths().get( position);

        for ( int n = 0; n < 7; n++) {
            total.add( 0.0);
            for ( Entry entry: month.getList( n)) {
                total.set( n, total.get( n) + entry.getAmount());
            }
        }

        textViewMonthName.setText( month.getDate());
        textView0.setText( "Incomes: " + total.get(0));
        textView1.setText( "Shopping Expense: " + total.get(1));
        textView2.setText( "Transportation Expense: " + total.get(2));
        textView3.setText( "Food Expense: " + total.get(3));
        textView4.setText( "Clothing Expense: " + total.get(4));
        textView5.setText( "Bill Expense: " + total.get(5));
        textView6.setText( "Other Expense: " + total.get(6));

        view.findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new FragmentPastMonthDetails();

                Bundle bundle = new Bundle();
                bundle.putInt( "position", position);
                newFragment.setArguments( bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace( getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


    }
}

