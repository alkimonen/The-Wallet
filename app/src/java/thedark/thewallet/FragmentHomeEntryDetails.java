package thedark.thewallet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FragmentHomeEntryDetails extends Fragment {

    private User user;
    private TextView textViewType, textViewAmount;
    private EditText editTextDetails;
    private Switch swich;
    private int position;
    private Entry entry;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_entry_details,null);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        try {
            FileInputStream fis = getActivity().openFileInput( "user.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            user = (User) is.readObject();
            is.close();
            fis.close();
        } catch ( Exception e){ }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("entryPosition", 0);
        }

        textViewType = (TextView) view.findViewById(R.id.textView27);
        editTextDetails = (EditText) view.findViewById(R.id.editText12);
        textViewAmount = (TextView) view.findViewById(R.id.textView29);
        swich = (Switch) view.findViewById(R.id.switch3);

        entry = user.getEntries().get( position);

        textViewType.setText( entry.getTypeName());
        editTextDetails.setText( entry.getDetails());
        textViewAmount.setText( entry.getAmount() + "");
        swich.setChecked( entry.isRegular());

        view.findViewById(R.id.button12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                entry.setRegular( swich.isChecked());
                entry.setDetails( editTextDetails.getText().toString());

                try {
                    FileOutputStream fos = getActivity().openFileOutput( "user.dat", Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(user);
                    os.close();
                    fos.close();
                } catch ( Exception e){ }

                Toast.makeText(getActivity(), "Aplied", Toast.LENGTH_LONG).show();

                Fragment newFragment = new FragmentHome();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace( getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        view.findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Delete");
                alert.setMessage("Are you sure?");
                alert.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        user.getEntries().remove( entry);
                        if ( user.getCurrentMonth().getEntries().contains( entry)) {
                            user.getCurrentMonth().getEntries().remove( entry);
                        }
                        try {
                            FileOutputStream fos = getActivity().openFileOutput( "user.dat", Context.MODE_PRIVATE);
                            ObjectOutputStream os = new ObjectOutputStream(fos);
                            os.writeObject(user);
                            os.close();
                            fos.close();
                        } catch ( Exception e){ }

                        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_LONG).show();

                        Fragment newFragment = new FragmentHome();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace( getId(), newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                alert.setNegativeButton( "No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
                    }
                });
                alert.show();

            }
        });
    }
}
