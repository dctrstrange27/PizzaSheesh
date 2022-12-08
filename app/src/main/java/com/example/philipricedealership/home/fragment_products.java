package com.example.philipricedealership.home;

import android.graphics.Movie;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.philipricedealership.R;

import com.example.philipricedealership._models.Order;
import com.example.philipricedealership._models.Product;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;
import com.example.philipricedealership.adapter.rice_adapter;

import java.util.ArrayList;

public class fragment_products extends Fragment {
    ListView riceList;
    rice_adapter rice;
    DatabaseHelper d;
    View v;
    User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_products, container, false);
        riceList = v.findViewById(R.id.riceList);
        currentUser = (User) getArguments().getSerializable("currentUser");
        d = new DatabaseHelper(v.getContext());

        render();
        return  v;
    }

    public void render(){
        ArrayList<Product> prods = Product.getAllProduct(d);
        for(Product prd : prods) if(currentUser.isPresentInCart(prd.getUid())) prd.setAdded(true);
        rice = new rice_adapter(v.getContext(), prods, currentUser, this);
        riceList.setAdapter(rice);
    }
}