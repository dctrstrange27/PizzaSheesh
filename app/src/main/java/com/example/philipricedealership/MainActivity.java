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
        dbHelper.dropDbs(new String[] {"product"});
        dbHelper.checkTableExist();

        String vals[][] = {
                {"Pizza Sheezy Pepperoni", "pizza_sheezy_pepperoni", "Our cheesy flavor loaded favorite pepperoni pushed to the extreme. Available in 12\" and 15\" size"},
                {"Pizza Sheezy Mushroom", "pizza_sheezy_mushroom", "Leveled up with cheesy pizza with roasted mushrooms and tasty tomatoes available in 12\" and 15\" size"},
                {"Pizza Sheezy Octopus", "pizza_sheezy_mini_octo", "No pork pizza! An appetizing combination of delicious mini octopus, cheese and creamy shrimps available in 12\" and 15\" size"},
                {"Pizza Sheezy Veggies", "pizza_sheezy_veggies", "Cheesy pizza with veggies overload and more available in 12\" and 15\" size"},
                {"Sheezy Lasagna", "pizza_sheezy_lasagna", "Our best pasta made with loaded layers of beef and generous amount of cheese on top available in small, medium and large pan"}
        };
        double prices[] = {
                350, 450, 480, 300, 450
        };

        for (int x = 0; x < vals.length; x++){
            Product prd = new Product(vals[x][0], vals[x][1], vals[x][2], prices[x]);
            prd.saveState(getApplicationContext(), dbHelper, true);
            System.out.println("Product : "+prd.getName()+" isValid Image -> "+(prd.getImgResId(getApplicationContext()) == 0 ? "Invalid" : "Valid"));
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



        //testingg
    }
}