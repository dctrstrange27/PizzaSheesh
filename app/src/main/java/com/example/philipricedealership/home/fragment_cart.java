package com.example.philipricedealership.home;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageButton;

import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


import com.example.philipricedealership.R;
import com.example.philipricedealership._models.Product;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;

import com.example.philipricedealership.adapter.cart_adapter;

import java.util.ArrayList;


public class fragment_cart extends Fragment {
    private Button checkout;
    private DatabaseHelper dbHelper;
    private User currentUser;
    cart_adapter cart;
    DatabaseHelper d;
    ListView item;

    TextView toShow, totalqty, totalcost;
    ScrollView scroll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        dbHelper = new DatabaseHelper(getContext());
        currentUser = (User) getArguments().getSerializable("currentUser");

        toShow = v.findViewById(R.id.toShow);
        checkout = v.findViewById(R.id.checkout);
        totalcost = v.findViewById(R.id.totalcost);
        totalqty = v.findViewById(R.id.totalqty);
        checkout.setOnClickListener( JohnySinsei ->{
            currentUser.placeOrder(getContext(), dbHelper);
            rerender();
            Dialog checkout_dialog = new Dialog(getContext());
            checkout_dialog.setContentView(R.layout.dialog_order_placed);
            checkout_dialog.getWindow().getAttributes().windowAnimations = R.style.diagAnim;
            checkout_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageButton dialogClose = checkout_dialog.findViewById(R.id.closeV);
            dialogClose.setOnClickListener(JohnySinsei2 -> {
                checkout_dialog.dismiss();
            });
            Button ok = checkout_dialog.findViewById(R.id.no);
            ok.setOnClickListener(JohnySinsei2 -> {
                checkout_dialog.dismiss();
            });
            checkout_dialog.show();
        });
        item = v.findViewById(R.id.itemList);
        d = new DatabaseHelper(v.getContext());
        rerender();
        return v;
    }

    public void recount(){
        ArrayList<Product> userItems = currentUser.getCartItems(d);

        double totalCost = 0;
        int totalQty = 0;

        for(Product pr : userItems){
            totalQty += pr.getQty();
            totalCost += pr.getTotalCost();
        }

        totalqty.setText(totalQty + "");
        totalcost.setText("₱ "+String.format("%.2f", totalCost) + "");

        if(totalQty == 0) {
            checkout.setClickable(false);
            checkout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.disabled));
        }
        else {
            checkout.setClickable(true);
            checkout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
        }

        if(totalQty == 0){
            toShow.setVisibility(View.VISIBLE);
        }else{
            item.setVisibility(View.VISIBLE);
        }
    }

    public void rerender(){
        System.out.println("Rerendered");
        currentUser.fetchSelf(dbHelper);
        Product.getAllProduct(d);
        ArrayList<Product> userItems = currentUser.getCartItems(d);
        cart = new cart_adapter( getContext(), userItems, currentUser, this);
        item.setAdapter(cart);


        toShow.setVisibility(View.GONE);
        item.setVisibility(View.GONE);
        recount();
    }
}