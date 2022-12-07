package com.example.philipricedealership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import com.example.philipricedealership._models.Product;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;
import com.example.philipricedealership.home.Home;
import com.example.philipricedealership.signup.login;
import com.example.philipricedealership.signup.signup;

import java.util.ArrayList;

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
        dbHelper.dropDbs(new String[]{"product"});
        dbHelper.checkTableExist();

        String vals[][] = {
                {"Buko Pandan", "prod_bukopandan25", "25kg, mabango, malambot, local"},
                {"Buko Pandan", "prod_bukopandan5", "5kg, mabango, malambot, local"},
                {"Buko Pandan", "prod_bukopandan50", "50kg, mabango, malambot, local, "},
                {"Coco Pandan", "prod_cocopandan25", "25kg, whole grain, imported, mabango"},
                {"Dinurado Mindoro", "prod_dinuradomindoro25", "25kg, malambot"},
                {"Hasmine", "prod_hasmine25", "25kg, whole grain, imported, walang amoy"},
                {"Jasmine", "prod_jasmine5", "5kg"},
                {"Jasmine Sweet Rice", "prod_jasminsweetrice25", "25kg, whole grain, imported, walang amoy"},
                {"Mekeni Rice", "prod_mekenirice25", "25kg, medyo maalsa, malambot, local"},
                {"Pandan Gold", "prod_pandangold25", "25kg, malambot, commercial, local"},
                {"Pandan Gold", "prod_pandandgold5", "5kg, whole grain, imported, malambot"},
                {"Princess Hasmine", "prod_princesshasmine50", "50kg"},
                {"Rapido", "prod_rapido50", "50kg, imported, maalsa"},
                {"Sakura", "prod_sakura25", "25kg, local, malambot"},
                {"Sakura", "prod_sakura5", "5kg"},
                {"Tanglad Rice", "prod_tangladrice25", "25kg, malambot"},
                {"Tween Dragon", "prod_tweendragon50", "50kg"},
                {"Victoria", "prod_victoria25", "25kg"}
        };
        double prices[] = {
                950, 210, 1880, 1060, 1100,
                1000, 235, 1000, 900, 860,
                235, 2000, 1740, 870, 265, 930,
                1880, 880
        };

        for (int x = 0; x < vals.length; x++){
            Product prd = new Product(vals[x][0], vals[x][1], vals[x][2], prices[x]);
            prd.saveState(getApplicationContext(), dbHelper, true);
        }

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
                ArrayList<Product> mycart = dummyUser.getCartItems(dbHelper);
                System.out.println("I have "+mycart.size()+" these are : ");
                for(Product p : mycart) System.out.println(p.getName()+" -> "+ p.getPrice() +" -> "+p.getTotalCost());
                startActivity(homeIntent);
                finish();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
}