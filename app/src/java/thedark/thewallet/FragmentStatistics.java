package thedark.thewallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import java.io.ObjectInputStream;

public class FragmentStatistics extends Fragment {

    private User user;
    private ListView listView;
    private TextView textView;
    private HomeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_current_month_statistics,null);
    }

    private String categories[] = {"Shopping","Transportation","Food","Clothing","Bill","Other"};
    private int example[] = {13,12,15,15,41,12};

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            FileInputStream fis = getActivity().openFileInput( "user.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            user = (User) is.readObject();
            is.close();
            fis.close();
        } catch ( Exception e){ }

        listView = (ListView) view.findViewById(R.id.listViewCurrentMonth);
        textView = (TextView) view.findViewById(R.id.textViewCurrentMonth);

        textView.setText( user.getCurrentMonth().getDate());

        adapter = new HomeAdapter( user.getCurrentMonth().getEntries(), getActivity());
        listView.setAdapter( adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment newFragment = new FragmentStatisticsEntryDetails();

                Bundle bundle = new Bundle();
                bundle.putInt( "entryPosition", position);
                newFragment.setArguments( bundle);

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


