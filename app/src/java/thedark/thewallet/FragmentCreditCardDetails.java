package thedark.thewallet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FragmentCreditCardDetails extends Fragment {

    private User user;
    private int position;
    private TextView textViewName, textViewLimit, textViewDue, textViewExp,textViewExpense;
    private EditText editTextNewLimit;
    private CreditCard card;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_credit_card_details,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewName = (TextView) view.findViewById(R.id.textViewCardName);
        textViewLimit = (TextView) view.findViewById(R.id.textViewLimit);
        textViewDue = (TextView) view.findViewById(R.id.textViewDueDay);
        textViewExp = (TextView) view.findViewById(R.id.textViewExpDate);
        textViewExpense = (TextView) view.findViewById(R.id.textViewCardExpenses);
        editTextNewLimit = (EditText) view.findViewById(R.id.editText5);

        try {
            FileInputStream fis = getActivity().openFileInput( "user.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            user = (User) is.readObject();
            is.close();
            fis.close();
        } catch ( Exception e){ }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("position", 0);
        }

        card = user.getCreditCards().get( position);

        textViewName.setText( "Name: " + card.getName());
        textViewLimit.setText( "Limit: " + card.getLimit());
        textViewDue.setText( "Due day: " + card.getDueDay());
        textViewExp.setText( "Exp date: " + card.getExpDay());
        textViewExpense.setText( card.getExpenses() + "");
        textViewExpense.setTextColor( Color.RED);

        view.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new FragmentCreditCardExpenses();

                Bundle bundle = new Bundle();
                bundle.putInt( "position", position);
                newFragment.setArguments( bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace( getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        view.findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !editTextNewLimit.getText().toString().equals("")) {
                    card.setLimit(Integer.parseInt(editTextNewLimit.getText().toString()));
                    textViewLimit.setText( "Limit: " + card.getLimit());
                    Toast.makeText(getActivity(), "Limit Changed", Toast.LENGTH_LONG).show();

                    try {
                        FileOutputStream fos = getActivity().openFileOutput( "user.dat", Context.MODE_PRIVATE);
                        ObjectOutputStream os = new ObjectOutputStream(fos);
                        os.writeObject(user);
                        os.close();
                        fos.close();
                    } catch ( Exception e){ }
                }
            }
        });

        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Delete");
                alert.setMessage("Are you sure?");
                alert.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_LONG).show();

                        user.getCreditCards().remove( position);

                        try {
                            FileOutputStream fos = getActivity().openFileOutput( "user.dat", Context.MODE_PRIVATE);
                            ObjectOutputStream os = new ObjectOutputStream(fos);
                            os.writeObject(user);
                            os.close();
                            fos.close();
                        } catch ( Exception e){ }

                        Fragment newFragment = new FragmentCreditCards();
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
