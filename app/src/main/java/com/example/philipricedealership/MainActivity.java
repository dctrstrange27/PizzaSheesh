package com.example.philipricedealership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;
import com.example.philipricedealership.home.Home;
import com.example.philipricedealership.signup.login;
import com.example.philipricedealership.signup.signup;

public class MainActivity extends AppCompatActivity {
    private Button login_btn,signup_btn;
    private User dummyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        dbHelper.dropDbs(new String[]{"user", "product", "orders"});
        dbHelper.checkTableExist();

        login_btn.setOnClickListener(johny -> {
            Intent toLogin = new Intent(getApplicationContext(), login.class);
            startActivity(toLogin);
        });

        signup_btn.setOnClickListener(johny -> {
            Intent toSignup = new Intent(getApplicationContext(), signup.class);
            startActivity(toSignup);
        });

        Cursor hasLoggedIn = dbHelper.execRawQuery("SELECT * FROM user where state=1", null);

        if(hasLoggedIn != null && hasLoggedIn.getCount() > 0){
            hasLoggedIn.moveToNext();
            dummyUser = new User(hasLoggedIn.getString(3));
            dummyUser.fetchSelf(dbHelper);

            try{
                Intent homeIntent = new Intent(getApplicationContext(), Home.class);
                homeIntent.putExtra("currentUser", dummyUser);
                startActivity(homeIntent);
                finish();
            }catch (Exception e){
                System.out.println(e);
            }
        }

    }
}