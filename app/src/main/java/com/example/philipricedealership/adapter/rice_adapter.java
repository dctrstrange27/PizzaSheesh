package com.example.philipricedealership.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.philipricedealership._models.Product;
import com.example.philipricedealership.R;

import java.util.ArrayList;

public class rice_adapter extends ArrayAdapter<Product> {
    static Product rice;
    public rice_adapter(Context cont, ArrayList<Product> pList){
        super(cont, R.layout.product_list, pList);
    }
    public View getView(int position, @Nullable View c, @NonNull ViewGroup parent) {
        rice = getItem(position);
        if(c == null){
            c = LayoutInflater.from(getContext()).inflate(R.layout.product_list,parent,false);
        }
        ImageView img = c.findViewById(R.id.pic);
        TextView name = c.findViewById(R.id.prodName);
        TextView price = c.findViewById(R.id.prodPrice);

        name.setText(rice.getName());
        price.setText("$"+Integer.toString((int) rice.getPrice())+".004");
        img.setImageResource(rice.getImgResId(this.getContext()));
        return c;
    }


}
