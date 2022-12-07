package com.example.philipricedealership.home;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageButton;

import android.widget.ListView;


import com.example.philipricedealership.R;
import com.example.philipricedealership._models.Order;
import com.example.philipricedealership._models.Product;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;

import com.example.philipricedealership.adapter.cart_adapter;


public class fragment_cart extends Fragment {
    private Button checkout;
    private DatabaseHelper dbHelper;
    private User currentUser;
    cart_adapter cart;
    DatabaseHelper d;
    ListView item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        dbHelper = new DatabaseHelper(getContext());
        currentUser = (User) getArguments().getSerializable("currentUser");

        checkout = v.findViewById(R.id.checkout);
        checkout.setOnClickListener( JohnySinsei ->{
            currentUser.placeOrder(getContext(), dbHelper);

            Dialog checkout_dialog = new Dialog(getContext());
            checkout_dialog.setContentView(R.layout.dialog_order_placed);
            checkout_dialog.getWindow().getAttributes().windowAnimations = R.style.diagAnim;

            ImageButton dialogClose = checkout_dialog.findViewById(R.id.closeV);
            dialogClose.setOnClickListener(JohnySinsei2 -> {
                checkout_dialog.dismiss();
            });
            Button ok = checkout_dialog.findViewById(R.id.ok);
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

    public void rerender(){
        currentUser.fetchSelf(dbHelper);
        Product.getAllProduct(d);
        cart = new cart_adapter( getContext(), currentUser.getCartItems(d), currentUser, this);
        item.setAdapter(cart);
    }
}