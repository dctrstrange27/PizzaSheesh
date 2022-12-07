package com.example.philipricedealership.home;

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
import com.example.philipricedealership.adapter.orders_adapter;
import com.example.philipricedealership.adapter.rice_adapter;

import java.util.ArrayList;


public class fragment_orders extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_orders, container, false);
        User currentUser = (User) getArguments().getSerializable("currentUser");
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        ArrayList<Order> ords = Order.getAllOrderFrom(currentUser.getUid(), dbHelper);
        for(Order o : ords) System.out.println("My Orders -> "+o.toString());
        orders_adapter orders;
        ListView order;
        order = v.findViewById(R.id.orderList);
        orders = new orders_adapter(v.getContext(),  ords, currentUser);
        order.setAdapter(orders);

      return  v;
    }
}