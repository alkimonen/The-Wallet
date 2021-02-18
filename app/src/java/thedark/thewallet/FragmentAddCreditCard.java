package thedark.thewallet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FragmentAddCreditCard extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_credit_card,null);
    }

    private User user;
    private EditText nameText;
    private EditText limitText;
    private EditText expMonthText;
    private EditText expYearText;
    private EditText dueDayText;

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

        nameText = (EditText) view.findViewById(R.id.editText4);
        limitText = (EditText) view.findViewById(R.id.editText11);
        expMonthText = (EditText) view.findViewById(R.id.editText6);
        expYearText = (EditText) view.findViewById(R.id.editText9);
        dueDayText = (EditText) view.findViewById(R.id.editText10);

        view.findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !nameText.getText().toString().equals("") && !limitText.getText().toString().equals("")
                        && !expMonthText.getText().toString().equals("") && !expYearText.getText().toString().equals("")
                        && !dueDayText.getText().toString().equals("")) {
                    user.addCreditCard( nameText.getText().toString(), Integer.parseInt( dueDayText.getText().toString()),
                            Integer.parseInt( expMonthText.getText().toString()), Integer.parseInt( expYearText.getText().toString()),
                            Integer.parseInt( limitText.getText().toString()));

                    try {
                        FileOutputStream fos = getActivity().openFileOutput( "user.dat", Context.MODE_PRIVATE);
                        ObjectOutputStream os = new ObjectOutputStream(fos);
                        os.writeObject(user);
                        os.close();
                        fos.close();
                    } catch ( Exception e){ }
                }

                Fragment newFragment = new FragmentCreditCards();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace( getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }


}
