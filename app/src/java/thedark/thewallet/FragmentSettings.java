package thedark.thewallet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FragmentSettings extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_setings,null);
    }

    private User user;
    private EditText name, password1, password2;

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

        name = (EditText) view.findViewById( R.id.editText3);
        password1 = (EditText) view.findViewById(R.id.editText7);
        password2 = (EditText) view.findViewById(R.id.editText8);
        name.setHint( user.getName());

        view.findViewById(R.id.buttonChangeName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( name.getText().toString().equals("")) {
                    Toast.makeText( getActivity(), "Please enter a valid name!", Toast.LENGTH_LONG).show();
                }
                else {
                    user.setName( name.getText().toString());
                    Toast.makeText( getActivity(), "The name is changed!", Toast.LENGTH_LONG).show();

                    user.changeMonth();
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

        view.findViewById(R.id.buttonChangePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !password1.getText().toString().equals("") && password1.getText().toString().equals( password2.getText().toString())) {
                    user.setPassword( password1.getText().toString());
                    Toast.makeText( getActivity(), "The password is changed!", Toast.LENGTH_LONG).show();
                    try {
                        FileOutputStream fos = getActivity().openFileOutput( "user.dat", Context.MODE_PRIVATE);
                        ObjectOutputStream os = new ObjectOutputStream(fos);
                        os.writeObject(user);
                        os.close();
                        fos.close();
                    } catch ( Exception e){ }
                }
                else
                    Toast.makeText( getActivity(), "Please enter a valid password!", Toast.LENGTH_LONG).show();
            }
        });

        view.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Delete");
                alert.setMessage("Are you sure?");
                alert.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_LONG).show();
                        user = null;
                        try {
                            FileOutputStream fos = getActivity().openFileOutput( "user.dat", Context.MODE_PRIVATE);
                            ObjectOutputStream os = new ObjectOutputStream(fos);
                            os.writeObject(user);
                            os.close();
                            fos.close();
                        } catch ( Exception e){ }

                        SignUpActivity.sharedPreferences.edit().putBoolean( "newUser", true).apply();

                        Intent intent = new Intent( getActivity(), SignUpActivity.class);
                        startActivity( intent);
                    }
                });
                alert.setNegativeButton( "No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_LONG).show();
                    }
                });
                alert.show();
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
