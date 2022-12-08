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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.philipricedealership.R;
import com.example.philipricedealership._models.Order;
import com.example.philipricedealership._models.Product;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;

import java.util.ArrayList;
import com.example.philipricedealership.home.fragment_orders;
public class orders_adapter extends ArrayAdapter<Order> {

    private User currentUser;
    private DatabaseHelper dbHelper;
    private fragment_orders rootParent;

    public orders_adapter(Context cont, ArrayList<Order> pList, User currentUser, fragment_orders rootParent){
        super(cont, R.layout.order_list, pList);

        this.dbHelper = new DatabaseHelper(getContext());
        this.currentUser = currentUser;
        this.rootParent = rootParent;
    }

    public View getView(int position, @Nullable View c, @NonNull ViewGroup parent) {
        Order order = getItem(position);
        Order currentOrder = getItem(position);
        ArrayList <Product> cartlist = currentOrder.getCartItems(dbHelper);
        if(c == null){
            c = LayoutInflater.from(getContext()).inflate(R.layout.order_list,parent,false);
        }
        TextView ordID = c.findViewById(R.id.order_prodPrice);
        TextView desc = c.findViewById(R.id.order_prodDesc);
        TextView total = c.findViewById(R.id.order_prodQty);
        Button cancelOrder = c.findViewById(R.id.cancelOrder);
        Button viewOrder = c.findViewById(R.id.viewOrders);

        total.setText("Total: "+Integer.toString((int) order.getTotal()));
        desc.setText(order.getDate());
        ordID.setText("order id: #000"+Integer.toString(order.getUid()));

        viewOrder.setOnClickListener(aegin ->{
            Dialog viewOrders = new Dialog(getContext());
            viewOrders.setContentView(R.layout.order_list_diag);
            viewOrders.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            viewOrders.getWindow().getAttributes().windowAnimations = R.style.diagAnim;
            viewOrders.show();

            ListView view_order = viewOrders.findViewById(R.id.orderList);
            TextView orderId = viewOrders.findViewById(R.id.order_id);
            TextView orderDate = viewOrders.findViewById(R.id.order_date);
            TextView ordTotal = viewOrders.findViewById(R.id.total);
            ImageButton close = viewOrders.findViewById(R.id.close);
            orderId.setText("order id: #000"+Integer.toString(order.getUid()));
            orderDate.setText("date: "+order.getDate());
            ordTotal.setText("Total:₱ "+Integer.toString((int) order.getTotal()));
            ordered_adapter orders = new ordered_adapter(getContext(),cartlist, currentUser,null);
            view_order.setAdapter(orders);
            close.setOnClickListener(e -> {
                viewOrders.dismiss();
            });

        });


        cancelOrder.setOnClickListener(JohnySinsei -> {
            Dialog checkout_dialog = new Dialog(getContext());
            checkout_dialog.setContentView(R.layout.dialog_cancel_order);
            checkout_dialog.getWindow().getAttributes().windowAnimations = R.style.diagAnim;
            checkout_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageButton dialogClose = checkout_dialog.findViewById(R.id.closeV);
            dialogClose.setOnClickListener(JohnySinsei2 -> {
                checkout_dialog.dismiss();
            });
            Button ok = checkout_dialog.findViewById(R.id.ok), no = checkout_dialog.findViewById(R.id.no);
            ok.setOnClickListener(JohnySinsei2 -> {
                order.destroySelf(getContext(), dbHelper);
                rootParent.rerender();
                checkout_dialog.dismiss();
            });
            no.setOnClickListener(JohnySinsei2 -> {
                checkout_dialog.dismiss();
            });
            checkout_dialog.show();
        });

        return c;
    }

}
