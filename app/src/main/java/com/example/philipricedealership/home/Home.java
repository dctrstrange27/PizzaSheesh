package com.example.philipricedealership.home;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.philipricedealership.R;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;

public class Home extends AppCompatActivity {
    private ImageView home,products,orders,cart,Me;
    private User currentUser;
    private DatabaseHelper dbHelper;
    private TextView myname,fragLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        fragLabel = findViewById(R.id.fragmentLabel);
        home = findViewById(R.id.homeIcon);
        products = findViewById(R.id.productsIcon);
        orders = findViewById(R.id.ordersIcon);
        cart = findViewById(R.id.cartIcons);
        Me = findViewById(R.id.meIcons);
        currentUser = (User) getIntent().getSerializableExtra("currentUser");
//        myname.setText(currentUser.getUsername());
        routes();
    }
    public void routes(){
        route(0, null);
        fragLabel.setText("Home");
        home.setOnClickListener(johny -> {
            home.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.anim_item));
            fragLabel.setText("Home");
            route(0, null);
        });
        products.setOnClickListener(johny -> {
            products.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.anim_item));
            fragLabel.setText("Products");
            route(1, null);
        });
        orders.setOnClickListener(johny -> {
            orders.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.anim_item));
            fragLabel.setText("Orders");
            route(2, null);
        });
        cart.setOnClickListener(johny -> {
            cart.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.anim_item));
            fragLabel.setText("My Cart");
            route(3, null);
        });
        Me.setOnClickListener(johny -> {
            Me.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.anim_item));
            fragLabel.setText("Me");
            route(4, null);
        });

    }
    public void route(int r, Bundle bundolf){
        try {
            if(bundolf.containsKey("currentUser")) bundolf.remove("currentUser");
            currentUser.fetchSelf(dbHelper);
            bundolf.putSerializable("currentUser", currentUser);
        }catch (Exception e){
            System.out.println("Fire ERR " + e);
            bundolf = new Bundle();
            bundolf.putSerializable("currentUser", currentUser);
        }
        if(r == 0) getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, fragment_home.class, bundolf).commit();
        if(r == 1) getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, fragment_products.class, bundolf).commit();
        if(r == 2) getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, fragment_orders.class, bundolf).commit();
        if(r == 3) getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, fragment_cart.class, bundolf).commit();
        if(r == 4) getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, fragment_me.class, bundolf).commit();
    }
}