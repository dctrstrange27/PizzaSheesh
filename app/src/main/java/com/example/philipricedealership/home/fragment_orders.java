package com.example.philipricedealership.home;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.philipricedealership.R;
import com.example.philipricedealership._models.Order;
import com.example.philipricedealership._models.Product;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;
import com.example.philipricedealership.adapter.ordered_adapter;
import com.example.philipricedealership.adapter.orders_adapter;
import com.example.philipricedealership.adapter.rice_adapter;

import java.util.ArrayList;


public class fragment_orders extends Fragment {
    User currentUser;
    DatabaseHelper dbHelper;
    ListView order;
    View v;
    orders_adapter orders;
    ordered_adapter  ordered;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_orders, container, false);
        currentUser = (User) getArguments().getSerializable("currentUser");
        dbHelper = new DatabaseHelper(getContext());
        order = v.findViewById(R.id.orderList);


        rerender();
      return  v;
    }

    public void rerender(){
        ArrayList<Order> ords = Order.getAllOrderFrom(currentUser.getUid(), dbHelper);
        orders = new orders_adapter(v.getContext(),  ords, currentUser, this);
        order.setAdapter(orders);
        order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int i, long arg3) {
                Dialog viewOrders = new Dialog(getContext());
                viewOrders.setContentView(R.layout.order_list_diag);
                viewOrders.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                viewOrders.getWindow().getAttributes().windowAnimations = R.style.diagAnim;
                viewOrders.show();
                System.out.println(currentUser.toString());
            }
        });
    }
}