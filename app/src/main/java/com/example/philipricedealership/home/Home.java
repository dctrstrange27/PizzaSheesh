package com.example.philipricedealership.home;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.philipricedealership.R;

public class Home extends AppCompatActivity {
    private ImageView home,products,orders,cart,Me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        home = findViewById(R.id.homeIcon);
        products = findViewById(R.id.productsIcon);
        orders = findViewById(R.id.ordersIcon);
        cart = findViewById(R.id.cartIcons);
        Me = findViewById(R.id.meIcons);
        routes();
    }
    public void routes(){
        route(0, null);
        home.setOnClickListener(johny -> {
            home.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.anim_item));
            route(0, null);
        });
        products.setOnClickListener(johny -> {
            products.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.anim_item));
            route(1, null);
        });
        orders.setOnClickListener(johny -> {
            orders.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.anim_item));
            route(2, null);
        });
        cart.setOnClickListener(johny -> {
            cart.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.anim_item));
            route(3, null);
        });
        Me.setOnClickListener(johny -> {
            Me.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.anim_item));
            route(4, null);
        });

    }
    public void route(int r, Bundle bundleRoute){
        if(r == 0) getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, fragment_home.class, bundleRoute).commit();
        if(r == 1) getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, fragment_products.class, bundleRoute).commit();
        if(r == 2) getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, fragment_orders.class, bundleRoute).commit();
        if(r == 3) getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, fragment_cart.class, bundleRoute).commit();
        if(r == 4) getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, fragment_me.class, bundleRoute).commit();
    }
}