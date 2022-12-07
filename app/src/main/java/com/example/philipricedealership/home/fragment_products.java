package com.example.philipricedealership.home;

import android.graphics.Movie;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.philipricedealership.R;

import com.example.philipricedealership._models.Product;
import com.example.philipricedealership._utils.DatabaseHelper;
import com.example.philipricedealership.adapter.rice_adapter;

public class fragment_products extends Fragment {
    ListView riceList;
    static rice_adapter rice;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_products, container, false);
        riceList = v.findViewById(R.id.riceList);

        DatabaseHelper d = new DatabaseHelper(v.getContext());
        Product.getAllProduct(d);
        rice = new rice_adapter(v.getContext(), Product.getAllProduct(d));
        riceList.setAdapter(rice);
        System.out.println("Products: "+Product.getAllProduct(d));

        return  v;
    }
}