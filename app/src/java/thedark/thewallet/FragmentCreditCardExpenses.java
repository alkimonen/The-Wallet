package thedark.thewallet;

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

public class FragmentCreditCardExpenses extends Fragment {

    private ListView listView;
    private User user;
    private int position;
    private HomeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_credit_card_expenses,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        listView = (ListView) view.findViewById(R.id.listViewCardExpenses);

        adapter = new HomeAdapter( user.getCreditCards().get(position).getEntries(), getActivity());
        listView.setAdapter( adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int entryPosition, long id) {

                Fragment newFragment = new FragmentCreditCardEntryDetails();

                Bundle bundle = new Bundle();
                bundle.putInt( "position", position);
                bundle.putInt( "entryPosition", entryPosition);
                newFragment.setArguments( bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace( getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
