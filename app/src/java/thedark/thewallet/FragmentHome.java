package thedark.thewallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;

public class FragmentHome extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home_menu,null);
    }

    private User user;
    private HomeAdapter adapter;
    private SharedPreferences sharedPreferences;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
            FileInputStream fis = getActivity().openFileInput( "user.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            user = (User) is.readObject();
            is.close();
            fis.close();
        } catch ( Exception e){ }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime( new Date());

        sharedPreferences = getActivity().getSharedPreferences( "thedark.thewallet", Context.MODE_PRIVATE);

        if ( calendar.get(Calendar.DAY_OF_MONTH) != 1 ) {
            sharedPreferences.edit().putBoolean( "newMonth", true).apply();
        }
        else if ( calendar.get(Calendar.DAY_OF_MONTH) == 1 &&
                sharedPreferences.getBoolean("newMonth", false)) {
            user.changeMonth();
            sharedPreferences.edit().putBoolean( "newMonth", false).apply();

            try {
                FileOutputStream fos = getActivity().openFileOutput( "user.dat", Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(user);
                os.close();
                fos.close();
            } catch ( Exception e){ }
        }

        for ( CreditCard card: user.getCreditCards()) {
            if ( calendar.get(Calendar.DAY_OF_MONTH) != card.getDueDay()) {
                sharedPreferences.edit().putBoolean( card.getName(), true).apply();
            } else if ( calendar.get(Calendar.DAY_OF_MONTH) == card.getDueDay() &&
                    sharedPreferences.getBoolean( card.getName(), false)) {
                card.deleteData();
                sharedPreferences.edit().putBoolean( card.getName(), false).apply();

                try {
                    FileOutputStream fos = getActivity().openFileOutput( "user.dat", Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(user);
                    os.close();
                    fos.close();
                } catch ( Exception e){ }
            }
        }

        for ( int index = 0; index < user.getRegulars().size(); index++) {
            if ( calendar.get(Calendar.DAY_OF_MONTH) != user.getRegulars().get( index).getDay())   {
                sharedPreferences.edit().putBoolean( index + "", true).apply();
            } else if ( calendar.get(Calendar.DAY_OF_MONTH) == user.getRegulars().get( index).getDay()
                    && sharedPreferences.getBoolean( index + "", false)) {
                user.addEntry( user.getRegulars().get( index));
                sharedPreferences.edit().putBoolean( index + "", false).apply();
            }
        }

        ListView listView = (ListView) view.findViewById(R.id.listView);
        TextView textView = (TextView) view.findViewById(R.id.textView3);
        textView.setText( user.getBalance() + "");

        adapter = new HomeAdapter( user.getEntries(), getActivity());
        listView.setAdapter( adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment newFragment = new FragmentHomeEntryDetails();

                Bundle bundle = new Bundle();
                bundle.putInt( "entryPosition", position);
                newFragment.setArguments( bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace( getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        view.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment newFragment = new FragmentIncome();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace( getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        view.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new FragmentExpense();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace( getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();
        this.getView().setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Fragment newFragment = new FragmentHome();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace( getId(), newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }
                return false;
            }
        } );

    }


}
