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
import android.widget.Switch;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FragmentIncome extends Fragment {

    private User user;
    private EditText amount, details;
    private Switch swich;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_income,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        amount = (EditText) view.findViewById(R.id.editTextAmountIncome);
        details = (EditText) view.findViewById(R.id.editTextDetailsIncome);
        swich = (Switch) view.findViewById(R.id.switch2);

        try {   //reading
            FileInputStream fis = getActivity().openFileInput( "user.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            user = (User) is.readObject();
            is.close();
            fis.close();
        } catch ( Exception e){ }

        view.findViewById(R.id.buttonIncome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( !amount.getText().toString().equals("") && !details.getText().toString().equals("")) {

                    Boolean isRegular = swich.isChecked();

                    Entry expense = new Entry( 0, Double.parseDouble(amount.getText().toString()), details.getText().toString(), isRegular);

                    user.addEntry( expense);
                }

                try {   //writing
                    FileOutputStream fos = getActivity().openFileOutput( "user.dat", Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(user);
                    os.close();
                    fos.close();
                } catch ( Exception e){ }

                Fragment newFragment = new FragmentHome();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace( getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
