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
import androidx.core.content.ContextCompat;

import com.example.philipricedealership._models.Product;
import com.example.philipricedealership.R;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;

import java.util.ArrayList;
import com.example.philipricedealership.home.fragment_products;
public class rice_adapter extends ArrayAdapter<Product> {
    static int rerenders = 0;
    private User currentUser;
    private DatabaseHelper dbHelper;
    private fragment_products rootParent;
    public rice_adapter(Context cont, ArrayList<Product> pList, User currentUser, fragment_products rootParent){
        super(cont, R.layout.product_list, pList);
        this.currentUser = currentUser;
        this.dbHelper = new DatabaseHelper(getContext());
        this.rootParent = rootParent;
    }

    public View getView(int position, @Nullable View c, @NonNull ViewGroup parent) {
        System.out.println("RENDERED VIEW "+rerenders);
        rerenders++;
        Product rice = getItem(position);
        if(c == null){
            c = LayoutInflater.from(getContext()).inflate(R.layout.product_list,parent,false);
        }
        ImageView img = c.findViewById(R.id.pic);
        TextView name = c.findViewById(R.id.prodName);
        TextView price = c.findViewById(R.id.prodPrice);
        TextView desc = c.findViewById(R.id.prodDesc);
        Button addToCart = c.findViewById(R.id.cancelOrder);

        name.setText(rice.getName());
        desc.setText(rice.getDescription());
        price.setText("$"+Integer.toString((int) rice.getPrice())+".00");
        img.setImageResource(rice.getImgResId(this.getContext()));






        if(rice.isAdded()){
            addToCart.setClickable(false);
            addToCart.setText("Added");
            addToCart.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.disabled));
        }

        addToCart.setOnClickListener(e -> {
            currentUser.addToCart(rice, getContext(), dbHelper);
            currentUser.fetchSelf(dbHelper);
        });
        return c;
    }

}
