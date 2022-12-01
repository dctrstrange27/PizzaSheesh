package com.example.philipricedealership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.philipricedealership.signup.login;
import com.example.philipricedealership.signup.signup;

public class MainActivity extends AppCompatActivity {
    Button login_btn,signup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);

        login_btn.setOnClickListener(johny -> {
            Intent toLogin = new Intent(getApplicationContext(), login.class);
            startActivity(toLogin);
        });

        signup_btn.setOnClickListener(johny -> {
            Intent toSignup = new Intent(getApplicationContext(), signup.class);
            startActivity(toSignup);
        });

    }
}