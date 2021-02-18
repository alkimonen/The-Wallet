package thedark.thewallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class FragmentCreditCardSelection extends Fragment {

    private User user;
    private CreditCardAdapter adapter;
    private ListView listView;
    private int type;
    private boolean regular;
    private String amount;
    private String details;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_credit_card_selection,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) view.findViewById(R.id.listViewCardSelection);

        try {
            FileInputStream fis = getActivity().openFileInput( "user.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            user = (User) is.readObject();
            is.close();
            fis.close();
        } catch ( Exception e){ }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            type = bundle.getInt("type", 6);
            regular = bundle.getBoolean( "regular", false);
            amount = bundle.getString( "amount", "");
            details = bundle.getString( "details", "");
        }

        adapter = new CreditCardAdapter( user.getCreditCards(), getActivity());
        listView.setAdapter( adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                sharedPreferences = getActivity().getSharedPreferences( "thedark.thewallet", Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean( "creditCardSelected", true).apply();

                android.support.v4.app.Fragment newFragment = new FragmentExpense();

                Bundle bundle = new Bundle();
                bundle.putInt( "position", position);
                bundle.putInt( "type", type);
                bundle.putBoolean( "regular", regular);
                bundle.putString( "details", details);
                bundle.putString( "amount", amount);
                newFragment.setArguments( bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace( getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
