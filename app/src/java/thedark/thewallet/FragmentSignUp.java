package thedark.thewallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class FragmentSignUp extends Fragment {


    private User user;
    private ImageView imageView;
    private EditText userName;
    private EditText amount;
    private EditText passwordText1;
    private EditText passwordText2;
    private Button signInButton;
    private Button loginButton;
    private boolean newUser;
    public static SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_sign_up,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginButton = (Button) view.findViewById(R.id.buttonChangeName);
        signInButton = (Button) view.findViewById(R.id.button);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        userName = (EditText) view.findViewById(R.id.editText);
        amount = (EditText) view.findViewById(R.id.editText2);
        passwordText1 = (EditText) view.findViewById(R.id.passwordText);
        passwordText2 = (EditText) view.findViewById(R.id.passwordText2);
        sharedPreferences = getActivity().getSharedPreferences( "thedark.thewallet", Context.MODE_PRIVATE);

        boolean newUser = sharedPreferences.getBoolean("newUser", true);

        if( newUser)
        {
            loginButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            userName.setVisibility(View.INVISIBLE);
            signInButton.setVisibility(View.INVISIBLE);
            amount.setVisibility(View.INVISIBLE);
            passwordText2.setVisibility(View.INVISIBLE);
        }
    }
}
