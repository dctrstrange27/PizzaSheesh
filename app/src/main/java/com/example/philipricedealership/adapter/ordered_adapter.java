package com.example.philipricedealership.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.philipricedealership.R;
import com.example.philipricedealership._models.Order;
import com.example.philipricedealership._models.Product;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;
import com.example.philipricedealership.home.fragment_orders;

import java.util.ArrayList;

public class ordered_adapter extends ArrayAdapter<Product> {

    private User currentUser;
    private DatabaseHelper dbHelper;
    private fragment_orders rootParent;

    public ordered_adapter(Context cont, ArrayList<Product> pList, User currentUser, fragment_orders rootParent){
        super(cont, R.layout.order_list_diag, pList);

        this.currentUser = currentUser;
        this.rootParent = rootParent;
    }

    public View getView(int position, @Nullable View c, @NonNull ViewGroup parent) {
        Product orderList = getItem(position);
        if(c == null){
            c = LayoutInflater.from(getContext()).inflate(R.layout.order_items,parent,false);
        }
        TextView price = c.findViewById(R.id.o_prodPrice);
        TextView desc = c.findViewById(R.id.o_prodDesc);
        TextView qty = c.findViewById(R.id.o_prodQty);
        TextView name = c.findViewById(R.id.o_prodName);
        ImageView img = c.findViewById(R.id.o_pic);

        name.setText(orderList.getName());
        desc.setText(orderList.getDescription());
        price.setText(String.format("₱ %.2f", orderList.getPrice()));
        img.setImageResource(orderList.getImgResId(this.getContext()));

        return c;
    }

}
