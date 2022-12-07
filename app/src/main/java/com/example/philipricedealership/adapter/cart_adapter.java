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

import com.example.philipricedealership._models.Product;
import com.example.philipricedealership.R;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;

import java.util.ArrayList;

public class cart_adapter extends ArrayAdapter<Product> {

    public cart_adapter(Context cont, ArrayList<Product> pList, User currentUser){
        super(cont, R.layout.cart_list, pList);
    }
    public View getView(int position, @Nullable View c, @NonNull ViewGroup parent) {
        Product rice = getItem(position);
        if(c == null){
            c = LayoutInflater.from(getContext()).inflate(R.layout.cart_list,parent,false);
        }
        ImageView img = c.findViewById(R.id.item_pic);
        TextView name = c.findViewById(R.id.item_name);
        TextView price = c.findViewById(R.id.item_price);
        Button delete = c.findViewById(R.id.delete);

        delete.setClickable(true);
        name.setText(rice.getName());
        price.setText("$"+Integer.toString((int) rice.getPrice())+".00");
        img.setImageResource(rice.getImgResId(this.getContext()));

        return c;
    }
}
