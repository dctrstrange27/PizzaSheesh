package com.example.philipricedealership.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.philipricedealership.R;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;
import com.example.philipricedealership._utils.Helper;
import com.example.philipricedealership._utils.JavaMailAPI;

public class signup extends AppCompatActivity {

    private EditText email, username, password, confirm_pass;
    private Button button;
    private User newUsr;
    private String key;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        dbHelper = new DatabaseHelper(this);

        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirm_pass = findViewById(R.id.confirm_pass);

        button = findViewById(R.id.button);
        button.setOnClickListener( JohnySinsei -> {
            String _email = email.getText().toString(),
                    _username = username.getText().toString(),
                    _password = password.getText().toString(),
                    _confirm_pass = confirm_pass.getText().toString();
            if (_email.length() == 0 || _username.length() == 0 || _password.length() == 0 || _confirm_pass.length() == 0) {
                Toast.makeText(this, "Please Fill Up All Fields", Toast.LENGTH_LONG).show();
                return;
            }
            if (!_password.equals(_confirm_pass)) {
                Toast.makeText(this, "Confirm Password Doesn't Match", Toast.LENGTH_LONG).show();
                return;
            }
            newUsr = new User(_email, _username, Helper.hashPassword(_password));

            if (newUsr.checkIfAlreadyExist(dbHelper)) {
                Toast.makeText(this, "Email is already taken", Toast.LENGTH_LONG).show();
                return;
            }

            sendCode();
//            verify();
        });
    }

    public void sendCode(){
        key = Helper.randomKey(6);
        JavaMailAPI mail = new JavaMailAPI(this, email.getText().toString(), "Your verification code", key+"");
        mail.execute();
    }

    public void onSuccess() {
        newUsr.setState(1);
        if (newUsr.saveState(this, dbHelper, true)) {
//            Intent homeIntent = new Intent(getApplicationContext(), Home.class);
//            homeIntent.putExtra("currentUser", newUsr);
//            startActivity(homeIntent);
        }
    }
}