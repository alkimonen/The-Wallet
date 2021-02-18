package thedark.thewallet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SignUpActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView textView = (TextView) findViewById(R.id.textViewUsername);
        loginButton = (Button) findViewById(R.id.buttonChangeName);
        signInButton = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);
        userName = (EditText) findViewById(R.id.editText);
        amount = (EditText) findViewById(R.id.editText2);
        passwordText1 = (EditText) findViewById(R.id.passwordText);
        passwordText2 = (EditText) findViewById(R.id.passwordText2);
        sharedPreferences = this.getSharedPreferences( "thedark.thewallet", Context.MODE_PRIVATE);

        boolean newUser = sharedPreferences.getBoolean("newUser", true);

        if( newUser)
        {
            textView.setVisibility( View.INVISIBLE);
            loginButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            userName.setVisibility(View.INVISIBLE);
            signInButton.setVisibility(View.INVISIBLE);
            amount.setVisibility(View.INVISIBLE);
            passwordText2.setVisibility(View.INVISIBLE);

            try {
                FileInputStream fis = getApplicationContext().openFileInput( "user.dat");
                ObjectInputStream is = new ObjectInputStream(fis);
                user = (User) is.readObject();
                is.close();
                fis.close();
            } catch ( Exception e){ }
            textView.setText( user.getName());
        }
    }

    public void createUser(View view) throws IOException, ClassNotFoundException {
        if ( !userName.getText().toString().trim().equals("") && !amount.getText().toString().trim().equals("")
                && !passwordText1.getText().toString().trim().equals("") && !passwordText2.getText().toString().trim().equals("")
        && passwordText1.getText().toString().trim().equals(passwordText2.getText().toString().trim())) {
            user = new User( userName.getText().toString() , passwordText1.getText().toString() , Double.parseDouble(amount.getText().toString()) );
            System.out.println( "Kullanıcı oluşturuldu!");
            sharedPreferences.edit().putBoolean( "newUser", false).apply();

            FileOutputStream fos = getApplicationContext().openFileOutput( "user.dat", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(user);
            os.close();
            fos.close();

            Intent intent = new Intent( getApplicationContext(), MainActivity.class);
            startActivity( intent);
        }
        else if ( !passwordText1.getText().toString().trim().equals(passwordText2.getText().toString().trim())) {
            Toast.makeText( getApplicationContext(), "Passwords are different!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText( getApplicationContext(), "Please fill all blanks!", Toast.LENGTH_LONG).show();
        }
    }

    public void login(View view) throws IOException, ClassNotFoundException {

        FileInputStream fis = getApplicationContext().openFileInput( "user.dat");
        ObjectInputStream is = new ObjectInputStream(fis);
        user = (User) is.readObject();
        is.close();
        fis.close();

        if ( user.getPassword().equals(passwordText1.getText().toString().trim())) {

            Intent intent = new Intent( getApplicationContext(), MainActivity.class);
            startActivity( intent);
        }
        else
        {
            Toast.makeText( getApplicationContext(), "Username or password are different!", Toast.LENGTH_LONG).show();
        }
    }


}