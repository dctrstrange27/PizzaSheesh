package com.example.philipricedealership.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.philipricedealership._models.Product;
import com.example.philipricedealership.R;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;
import com.example.philipricedealership.home.Home;

import java.util.ArrayList;

public class rice_adapter extends ArrayAdapter<Product> {
    private User currentUser;
    private DatabaseHelper dbHelper;
    public rice_adapter(Context cont, ArrayList<Product> pList, User currentUser){
        super(cont, R.layout.product_list, pList);
        this.currentUser = currentUser;
        this.dbHelper = new DatabaseHelper(getContext());
    }

    public View getView(int position, @Nullable View c, @NonNull ViewGroup parent) {
        Product rice = getItem(position);
        if(c == null){
            c = LayoutInflater.from(getContext()).inflate(R.layout.product_list,parent,false);
        }
        ImageView img = c.findViewById(R.id.pic);
        TextView name = c.findViewById(R.id.prodName);
        TextView price = c.findViewById(R.id.prodPrice);
        Button addToCart = c.findViewById(R.id.addToCart);

        name.setText(rice.getName());
        price.setText("$"+Integer.toString((int) rice.getPrice())+".00");
        img.setImageResource(rice.getImgResId(this.getContext()));
        setBtnState(addToCart, rice);
        addToCart.setOnClickListener(e -> {
            currentUser.addToCart(rice, getContext(), dbHelper);
            currentUser.fetchSelf(dbHelper);
            setBtnState(addToCart, rice);
        });
        return c;
    }

    private void setBtnState(Button target, Product p) {
        if(currentUser.isPresentInCart(p.getUid(), dbHelper)){
            target.setClickable(false);
            target.setText("Added");
            target.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.disabled));
        }
    }
}
