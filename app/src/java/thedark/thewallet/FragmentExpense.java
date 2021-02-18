package thedark.thewallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Switch;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FragmentExpense extends Fragment {

    private User user;
    private EditText amount, details;
    private Switch swich;
    private Button addButton;
    private Button popupButton;
    private Button addCardButton;
    private boolean creditCardSelected;
    private int type;
    private int position;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_expense,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        amount = (EditText) view.findViewById(R.id.editTextAmountIncome);

        type = 6;
        amount = (EditText) view.findViewById(R.id.editTextAmountExpense);
        details = (EditText) view.findViewById(R.id.editTextDetailsExpense);
        swich = (Switch) view.findViewById(R.id.switch1);
        addButton = (Button) view.findViewById(R.id.buttonExpense);
        popupButton = (Button) view.findViewById(R.id.button8);
        addCardButton = (Button) view.findViewById(R.id.button10);

        sharedPreferences = getActivity().getSharedPreferences( "thedark.thewallet", Context.MODE_PRIVATE);
        creditCardSelected = sharedPreferences.getBoolean("creditCardSelected", false);
        if ( creditCardSelected) {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                position = bundle.getInt( "position", 0);
                type = bundle.getInt("type", 6);
                swich.setChecked( bundle.getBoolean( "regular", false));
                amount.setText( bundle.getString( "amount", ""));
                details.setText( bundle.getString( "details", ""));
            }
            addCardButton.setText( "CREDIT CARD SELECTED");
        }

        try {
            FileInputStream fis = getActivity().openFileInput( "user.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            user = (User) is.readObject();
            is.close();
            fis.close();
        } catch ( Exception e){ }

        view.findViewById(R.id.buttonExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !amount.getText().toString().equals("") && !details.getText().toString().equals("")) {

                    Boolean isRegular = swich.isChecked();

                    Entry expense = new Entry( type, Double.parseDouble( amount.getText().toString()),
                            details.getText().toString(), isRegular);

                    user.addEntry( expense);

                    if ( creditCardSelected) {
                        user.getCreditCards().get( position).addEntry( expense);
                    }
                }

                try {   //writing
                    FileOutputStream fos = getActivity().openFileOutput( "user.dat", Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(user);
                    os.close();
                    fos.close();
                } catch ( Exception e){ }

                sharedPreferences.edit().putBoolean( "creditCardSelected", false).apply();

                Fragment newFragment = new FragmentHome();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace( getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        view.findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu( getActivity(), popupButton);
                popup.getMenuInflater().inflate( R.menu.popup_menu, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick( MenuItem item) {
                        if ( item.getItemId() == R.id.one) {
                            type = 1;
                        }
                        else if ( item.getItemId() == R.id.two) {
                            type = 2;
                        }
                        else if ( item.getItemId() == R.id.three) {
                            type = 3;
                        }
                        else if ( item.getItemId() == R.id.four) {
                            type = 4;
                        }
                        else if ( item.getItemId() == R.id.five) {
                            type = 5;
                        }
                        else {
                            type = 6;
                        }
                        popupButton.setText( item.getTitle());
                        return true;
                    }
                });
            }

        });

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new FragmentCreditCardSelection();

                Bundle bundle = new Bundle();
                bundle.putInt( "type", type);
                bundle.putBoolean( "regular", swich.isChecked());
                bundle.putString( "details", details.getText().toString());
                bundle.putString( "amount", amount.getText().toString());
                newFragment.setArguments( bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace( getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });
    }
}
