package com.example.philipricedealership.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.philipricedealership.R;
import com.example.philipricedealership._models.Order;
import com.example.philipricedealership._models.Product;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;

import java.util.ArrayList;

public class orders_adapter extends ArrayAdapter<Order> {

    private User currentUser;
    private DatabaseHelper dbHelper;
    public orders_adapter(Context cont, ArrayList<Order> pList, User currentUser){
        super(cont, R.layout.order_list, pList);

    }

    public View getView(int position, @Nullable View c, @NonNull ViewGroup parent) {
        Order rice = getItem(position);
        if(c == null){
            c = LayoutInflater.from(getContext()).inflate(R.layout.order_list,parent,false);
        }
        TextView price = c.findViewById(R.id.order_prodPrice);
        TextView desc = c.findViewById(R.id.order_prodDesc);
        TextView qty = c.findViewById(R.id.order_prodQty);

        qty.setText("Total: "+Integer.toString((int) rice.getTotal()));
        desc.setText(rice.getDate());
        price.setText("order id: #000"+Integer.toString(rice.getUid()));
        return c;
    }

}
