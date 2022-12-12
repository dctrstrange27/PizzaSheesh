package com.example.philipricedealership.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.philipricedealership.R;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;
import com.example.philipricedealership._utils.Helper;
import com.example.philipricedealership.home.Home;

public class login extends AppCompatActivity {
    private TextView signup;
    private EditText email, pass;
    private Button login;
    private DatabaseHelper dbHelper;
    private User usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        dbHelper = new DatabaseHelper(this);

        email = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.button);

        login.setOnClickListener(JohnySinsei -> {
            try{
                String _email = email.getText().toString().replaceAll("\\s",""), _pass = pass.getText().toString();
                User usr = new User(_email);
                if(!usr.checkIfAlreadyExist(dbHelper)){
                    Toast.makeText(this, "User doesn't exist", Toast.LENGTH_LONG).show();
                    return;
                }
                usr.fetchSelf(dbHelper);
                if(!Helper.comparePassword(_pass, usr.getPassword())){
                    Toast.makeText(this, "Wrong Credentail", Toast.LENGTH_LONG).show();
                    return;
                }
                usr.setState(1);
                usr.saveState(getApplicationContext(), dbHelper, false);
                Toast.makeText(this, "Success. Welcome Back!", Toast.LENGTH_LONG).show();

                Intent homeIntent = new Intent(getApplicationContext(), Home.class);
                homeIntent.putExtra("currentUser", usr);
                startActivity(homeIntent);
            }catch (Exception e){ System.out.println("ERRR " + e); }
        });
    }
}